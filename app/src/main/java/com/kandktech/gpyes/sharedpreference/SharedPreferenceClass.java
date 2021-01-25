package com.kandktech.gpyes.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass {

    Context context;
    SharedPreferences sp;

    public SharedPreferenceClass(Context context) {
        this.context = context;
        try{
            sp = context.getSharedPreferences("UserAttendance",Context.MODE_PRIVATE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void setTodayDate(String todayDate){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("todayDate",todayDate);
        editor.apply();
    }

    public String getTodayDate(){
        return sp.getString("todayDate","");
    }

   public void setLat(String lat){
       SharedPreferences.Editor editor = sp.edit();
       editor.putString("lat",lat);
       editor.apply();
   }

   public String getLat(){
        return sp.getString("lat","");
   }

    public void setLon(String lat){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("lon",lat);
        editor.apply();
    }

    public String getLon(){
        return sp.getString("lon","");
    }


}
