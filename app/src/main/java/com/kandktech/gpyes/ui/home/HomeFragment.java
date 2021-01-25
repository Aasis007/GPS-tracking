package com.kandktech.gpyes.ui.home;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Api;
import com.google.gson.JsonObject;
import com.kandktech.gpyes.Model.ResponseModel;
import com.kandktech.gpyes.R;
import com.kandktech.gpyes.LocationService;
import com.kandktech.gpyes.Retrofit.ApiInterface;
import com.kandktech.gpyes.Retrofit.RetrofitClient;
import com.kandktech.gpyes.Utls.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment  {
    View view;
    ImageView startbtn;
    public static Boolean flag = false;

    public static Double latitude = 0.0;
    public static Double longitude = 0.0;


    public HomeFragment() {
        //required empty constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);



        startbtn = view.findViewById(R.id.start_btn);


        startbtn.setImageResource(HomeFragment.flag ? R.drawable.stopbtn : R.drawable.startbutton);

        //Location Off
        startbtn.setOnClickListener(view -> {

            if (HomeFragment.flag) {
                startbtn.setImageResource(R.drawable.startbutton);

                Toast.makeText(getContext(), "You are Offline", Toast.LENGTH_SHORT).show();
                stopLocationService();
                HomeFragment.flag= false;
            }
            else {
                //Location On
                startbtn.setImageResource(R.drawable.stopbtn);


                Toast.makeText(getContext(), "You are Active", Toast.LENGTH_SHORT).show();

                startLocationService();



                HomeFragment.flag=true;
            }



        });



        return view;

    }


    private boolean isLocationserviceRunning() {
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {

                if (LocationService.class.getName().equals(service.service.getClassName())) {

                    if (service.foreground) {
                        return true;
                    }

                }
            }
            return false;
        }
        return false;

    }


    private void startLocationService() {




        if (!isLocationserviceRunning()) {

            Intent intent = new Intent(getContext(), LocationService.class);
            intent.setAction("startLocationService");
            getActivity().startService(intent);

            Toast.makeText(getContext(), "Location Service Started", Toast.LENGTH_SHORT).show();
        }

    }


    private void stopLocationService() {
        sendAttendanceGps(2);
        if (isLocationserviceRunning()) {
            Intent intent = new Intent(getContext(), LocationService.class);
            intent.setAction("stopLocationService");
            getActivity().startService(intent);
            Toast.makeText(getContext(), "Location Services Stopped", Toast.LENGTH_SHORT).show();
        }
    }


    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }

    public void sendAttendanceGps(int setStatus){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        SimpleDateFormat restime = new SimpleDateFormat(("HH:mm:ss"));
        String currenttime = restime.format(new Date());
        System.out.println("time : "+currenttime);
        SimpleDateFormat resDate = new SimpleDateFormat(("yyyy-MM-dd"));
        String currentdate =resDate.format(new Date());
        System.out.println("Date:" +currentdate);

        SessionManagement sessionManagement = new SessionManagement(getActivity());
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
//                    Toast.makeText(LocationService.this, ""+res, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

}