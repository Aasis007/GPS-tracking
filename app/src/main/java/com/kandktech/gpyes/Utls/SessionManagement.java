package com.kandktech.gpyes.Utls;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.kandktech.gpyes.Model.Userdata;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREFS_NAME = "session";
    String SESSION_KEY = "session_user";
    String COMP_KEY = "comid";


    public SessionManagement(Context context) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void savesession(Userdata userdata) {
        //save session
        int uid = userdata.getId();
        String uname = userdata.getName();
        String uemail = userdata.getEmail();
        int  ucid = userdata.getComp_id();
        String ustatus = userdata.getUser_status();

        editor.putInt(SESSION_KEY,uid).commit();
        editor.putInt(COMP_KEY,ucid).apply();
        Log.d("cid","comid"+ucid);



    }

    public int getSession() {
        return sharedPreferences.getInt(SESSION_KEY, -1);

    }

    public int getCompid() {
        return sharedPreferences.getInt(COMP_KEY,0);
    }

}
