package com.example.lisa.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Addy on 19.03.2016.
 */
public final class Appdaten {
        public static final String FILENAME = "meine_sharedPrefs";
        public static final String Beschreibung ="beschreibung";
    //Methode zum Lesen
        public static String getBeschreibung(Context ctx){
            SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
            return prefs.getString(Beschreibung, null);
        }
    //Methode zum Schreiben
    public static void setBeschreibung(Context ctx, String beschreibung){
        SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Beschreibung, beschreibung);
        editor.commit();
    }
}
