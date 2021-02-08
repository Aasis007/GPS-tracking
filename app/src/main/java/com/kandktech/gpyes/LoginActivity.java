package com.kandktech.gpyes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kandktech.gpyes.Model.ResponseModel;
import com.kandktech.gpyes.Model.Userdata;
import com.kandktech.gpyes.Retrofit.ApiInterface;
import com.kandktech.gpyes.Retrofit.RetrofitClient;
import com.kandktech.gpyes.Utls.SessionManagement;
import com.google.gson.JsonObject;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private final Retrofit retrofit =RetrofitClient.getRetrofit();
    private final ApiInterface apiInterface =retrofit.create(ApiInterface.class);
    Button sininbtn;
    EditText username,userpassword;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //checking first run of the app
       SharedPreferences preferences = getApplicationContext().getSharedPreferences("Activitypref",0);
       SharedPreferences.Editor editor = preferences.edit();
       editor.putString("myconstant","this is a test string").apply();
       setContentView(R.layout.login_activity);

       username = (EditText) findViewById(R.id.useremail);
       userpassword = (EditText) findViewById(R.id.userpassword);


       sininbtn= (Button)findViewById(R.id.sign_in);
       sininbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               Toast.makeText(LoginActivity.this, "test", Toast.LENGTH_SHORT).show();
               userLogin();

           }
       });

//        if (Build.VERSION.SDK_INT >= 23)
//        {
//            int hasPermission = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA);
//            if (hasPermission != PackageManager.PERMISSION_GRANTED)
//            {
//                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
//                {
//                    getErrorDialog("You need to allow Camera permission." +
//                            "\nIf you disable this permission, You will not able to add attachment.", LoginActivity.this, true).show();
//                }
//                else
//                {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS_CAMERA);
//
//                }
//                return;
//            }
//        }


        if (Build.VERSION.SDK_INT > 28){
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
            String rationale = "Please provide Location permission to get the gps location in this app for tracking.";
            Permissions.Options options = new Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning");

            Permissions.check(LoginActivity.this, permissions, rationale, options, new PermissionHandler() {
                @Override
                public void onGranted() {
                    System.out.println("Grandted");
                }

                @Override
                public void onDenied(Context context, ArrayList<String> deniedPermissions) {

                    Toast.makeText(context, "This app requires the location permission to function properly", Toast.LENGTH_SHORT).show();
                    finishAndRemoveTask();

                }
            });
        }else {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            String rationale = "Please provide Location permission to get the gps location in this app for tracking.";
            Permissions.Options options = new Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning");

            Permissions.check(LoginActivity.this, permissions, rationale, options, new PermissionHandler() {
                @Override
                public void onGranted() {
                    System.out.println("Grandted");
                }

                @Override
                public void onDenied(Context context, ArrayList<String> deniedPermissions) {

                    Toast.makeText(context, "This app requires the location permission to function properly", Toast.LENGTH_SHORT).show();
                    finishAndRemoveTask();

                }
            });
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        //check user logged in
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int UID = sessionManagement.getSession();
        if (UID != -1) {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {

        }
    }

    private void userLogin() {
            final String uname =username.getText().toString().trim();
            final String upassword = userpassword.getText().toString().trim();

                if (uname.isEmpty()) {
                    username.setError("Email is required");
                    username.requestFocus();
                    return;
                }

                if (upassword.isEmpty()) {
                    userpassword.setError("Password is required");
                    return;
                }

            login(uname,upassword);


    }

    private void login(String uname, String upassword) {
        JsonObject login = new JsonObject();
        login.addProperty("email",uname);
        login.addProperty("password",upassword);

        apiInterface.userlogin(login).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                assert response.body() != null;
                if (response.body().getAdmin_data().size() != 0) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        String resp = response.body().toString();
                        int uid = response.body().getAdmin_data().get(0).id;
                        String uname = response.body().getAdmin_data().get(0).name;
                        String uemail = response.body().getAdmin_data().get(0).email;
                        int cid = response.body().getAdmin_data().get(0).comp_id;
                        String ustatus = response.body().getAdmin_data().get(0).user_status;

                        //save userdata
                        Userdata userdata = new Userdata(uid,uname,uemail,cid,ustatus);
                        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                        sessionManagement.savesession(userdata);

                        Toast.makeText(LoginActivity.this, "Login Sucessfull ", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email",response.body().getAdmin_data().get(0).email);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }

//    public AlertDialog.Builder getErrorDialog(String message, Context context, final boolean isFromCamera) {
//        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        alertDialog.setTitle(getString(R.string.app_name)).setMessage(message);
//        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                dialog.dismiss();
//                if (Build.VERSION.SDK_INT >= 23) {
//                    if(isFromCamera){
//                        requestPermissions(new String[]{Manifest.permission.CAMERA},
//                                REQUEST_CODE_ASK_PERMISSIONS_CAMERA);
//                    }else {
//                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                REQUEST_CODE_ASK_PERMISSIONS_EXTERNAL_STORAGE);
//                    }
//                }
//            }
//        });
//        return alertDialog;
//    }
}