package com.example.lisa.myapplication;

import android.text.format.DateFormat;

import java.sql.Time;
import java.util.Date;

/**
 * Created by User on 21.03.2016.
 */
public class ToDo {
    private String titel, info, date, time;
    private int wichtig, id;


    public ToDo(int id, String titel, String info, String date, String time, int wichtig){
        this.titel = titel;
        this.info = info;
        this.wichtig = wichtig;
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public String getTitel() {
        return titel;
    }

    public String getInfo() {
        return info;
    }

    public int getWichtig() {
        return wichtig;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
