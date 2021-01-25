package com.kandktech.gpyes;


import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kandktech.gpyes.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        try {
            FirebaseMessaging.getInstance().subscribeToTopic("gpsupdates")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = getString(R.string.msg_subscribed);
                            if (!task.isSuccessful()) {
                                msg = getString(R.string.msg_subscribe_failed);
                            }
                            Log.d("message : ", msg);
//                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        try {
            String isStart = getIntent().getExtras().getString("isStarts");
            if (isStart.equals("1")){
                HomeFragment.flag = true;
            }else {
                HomeFragment.flag = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.




        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }




}