package com.evopackage.evo;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedprefs{
    SharedPreferences s;
    public Sharedprefs(Context context) {
        s = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    public void setState(Boolean state){
        SharedPreferences.Editor e = s.edit();
        e.putBoolean("NightMode", state);
        e.commit();

    }

    public Boolean LoadState() {
    Boolean state = s.getBoolean("NightMode", false);
    return state;
    }
}
