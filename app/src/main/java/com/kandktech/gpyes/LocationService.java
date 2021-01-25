package com.kandktech.gpyes;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.kandktech.gpyes.Model.ResponseModel;
import com.kandktech.gpyes.Retrofit.ApiInterface;
import com.kandktech.gpyes.Retrofit.RetrofitClient;
import com.kandktech.gpyes.Utls.SessionManagement;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.kandktech.gpyes.sharedpreference.SharedPreferenceClass;
import com.kandktech.gpyes.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationService extends Service {

    private Retrofit retrofit = RetrofitClient.getRetrofit();
    private ApiInterface apiInterface =retrofit.create(ApiInterface.class);
    SharedPreferenceClass sharedPreferenceClass;


    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();
                HomeFragment.latitude = latitude;
                HomeFragment.longitude = longitude;

                Log.d("location", latitude + "," + longitude);
                Toast.makeText(LocationService.this, "location:"+latitude, Toast.LENGTH_SHORT).show();


                sendGps(latitude,longitude);

            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    private void startLocationService() {

        String channelID = "Location_notification_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(getApplicationContext(),MainActivity.class);
        resultIntent.putExtra("isStarts","1");
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT


        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(

                getApplicationContext(),
                channelID
        );


        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Location Service");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelID) == null) {

                NotificationChannel notificationChannel = new NotificationChannel(

                        channelID,
                        "Location Service",
                        NotificationManager.IMPORTANCE_HIGH
                );

                notificationChannel.setDescription("This channel is used by Location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(30000);

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        LocationServices.getFusedLocationProviderClient(this)

                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                startForeground(Constants.LOCATION_SERVICE_ID, builder.build());

    }

    private void stopLocationService() {

        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Constants.ACTION_START_LOCATION_SERVICE)) {

                    startLocationService();
                }
                else if (action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)) {

                    stopLocationService();
                }

            }
        }


        return START_STICKY;
    }

    public void sendGps(Double latitude, Double longitude){

        sharedPreferenceClass = new SharedPreferenceClass(getApplicationContext());

        if (!sharedPreferenceClass.getLat().equals("") && !sharedPreferenceClass.getLon().equals("")){
            Location location1 = new Location("Current Location");
            location1.setLatitude(latitude);
            location1.setLongitude(longitude);

            Location location2 = new Location("Previous Location");
            location2.setLatitude(Double.parseDouble(sharedPreferenceClass.getLat()));
            location2.setLongitude(Double.parseDouble(sharedPreferenceClass.getLon()));

            double distanceInMeters = (location1.distanceTo(location2));

            if (distanceInMeters > 40){
                //current time and Date
                SimpleDateFormat restime = new SimpleDateFormat(("HH:mm:ss"));
                String currenttime = restime.format(new Date());
                System.out.println("time : "+currenttime);
                SimpleDateFormat resDate = new SimpleDateFormat(("yyyy-MM-dd"));
                String currentdate =resDate.format(new Date());
                System.out.println("Date:" +currentdate);




                SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
                int UID = sessionManagement.getSession();


                //sending data to server
                JsonObject senddata = new JsonObject();
                senddata.addProperty("user_id",UID);
                senddata.addProperty("lat",latitude);
                senddata.addProperty("lon",longitude);
                senddata.addProperty("time",currenttime);
                senddata.addProperty("date",currentdate);


                apiInterface.sendgpsdata(senddata).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        assert response.body() != null;
                        if (response.isSuccessful()) {

                            assert response.body() != null;
                            String res = response.body().toString();
                            String status = response.body().getStatus();
                            Log.d("status","status"+status);

                            if (latitude != 0.0){

                                sendAttendanceGps(1,currentdate);
                            }
//                            Toast.makeText(LocationService.this, ""+res, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });
            }


        }else{
            //current time and Date
            SimpleDateFormat restime = new SimpleDateFormat(("HH:mm:ss"));
            String currenttime = restime.format(new Date());
            System.out.println("time : "+currenttime);
            SimpleDateFormat resDate = new SimpleDateFormat(("yyyy-MM-dd"));
            String currentdate =resDate.format(new Date());
            System.out.println("Date:" +currentdate);




            SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
            int UID = sessionManagement.getSession();


            //sending data to server
            JsonObject senddata = new JsonObject();
            senddata.addProperty("user_id",UID);
            senddata.addProperty("lat",latitude);
            senddata.addProperty("lon",longitude);
            senddata.addProperty("time",currenttime);
            senddata.addProperty("date",currentdate);


            apiInterface.sendgpsdata(senddata).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    assert response.body() != null;
                    if (response.isSuccessful()) {

                        assert response.body() != null;
                        String res = response.body().toString();
                        String status = response.body().getStatus();
                        Log.d("status","status"+status);

                        if (latitude != 0.0){

                            sendAttendanceGps(1,currentdate);
                        }
//                            Toast.makeText(LocationService.this, ""+res, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }


    }

    public void sendAttendanceGps(int setStatus,String sdate){

        sharedPreferenceClass = new SharedPreferenceClass(getApplicationContext());

        if (!sharedPreferenceClass.getTodayDate().equals(sdate)){
            ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
            SimpleDateFormat restime = new SimpleDateFormat(("HH:mm:ss"));
            String currenttime = restime.format(new Date());
            System.out.println("time : "+currenttime);
            SimpleDateFormat resDate = new SimpleDateFormat(("yyyy-MM-dd"));
            String currentdate =resDate.format(new Date());
            System.out.println("Date:" +currentdate);

            SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
            int UID = sessionManagement.getSession();


            //sending data to server
            JsonObject senddata = new JsonObject();
            senddata.addProperty("user_id",UID);
            senddata.addProperty("lat",HomeFragment.latitude);
            senddata.addProperty("lon",HomeFragment.longitude);
            senddata.addProperty("time",currenttime);
            senddata.addProperty("date",currentdate);
            senddata.addProperty("setStatus",setStatus);


            apiInterface.attendanceGps(senddata).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    assert response.body() != null;
                    if (response.isSuccessful()) {

                        assert response.body() != null;
                        String res = response.body().toString();
                        String status = response.body().getStatus();
                        Log.d("status","status"+status);
                        if (status.equals("1")){
                            sharedPreferenceClass.setTodayDate(currentdate);
                        }

//                    Toast.makeText(LocationService.this, ""+res, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }

    }
}
