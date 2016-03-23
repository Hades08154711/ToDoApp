package com.example.lisa.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Addy on 19.03.2016.
 */
public final class Appdaten {
        public static final String FILENAME = "meine_sharedPrefs";
        public static final String TASK ="task";

    //Methode zum Lesen
        public static String getUser(Context ctx){
            SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
            return prefs.getString("Username", null);
        }

    public static ToDo getTodo(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        ToDo todo = new ToDo(prefs.getInt("Id", 0),prefs.getString("Titel", null),prefs.getString("Info", null),prefs.getString("Date", null),prefs.getString("Time", null),prefs.getInt("Wichtig", 0),prefs.getInt("Erledigt", 0));
        return todo;
    }
    //Methode zum Schreiben
    public static void addTodo(Context ctx, ToDo todo){
        SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Id", todo.getId());
        editor.putString("Titel",  todo.getTitel());
        editor.putString("Info", todo.getInfo());
        editor.putString("Date", todo.getDate());
        editor.putString("Time", todo.getTime());
        editor.putInt("Wichtig", todo.getWichtig());
        editor.putInt("Erledigt", todo.getErledigt());
        editor.commit();
    }

    public static void addUser(Context ctx, String name){
        SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Username", name);
        editor.commit();
    }

    public static void deleteTodo(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("Id");
        editor.remove("Titel");
        editor.remove("Info");
        editor.remove("Date");
        editor.remove("Time");
        editor.remove("Wichtig");
        editor.remove("Erledigt");
        editor.commit();
    }

    public static void deleteUser(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("Username");
        editor.commit();
    }

    public static void clearSharedPrefs(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}
