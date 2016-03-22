package com.example.lisa.myapplication;

import android.text.format.DateFormat;

import java.sql.Time;
import java.util.Date;

/**
 * Created by User on 21.03.2016.
 */
public class ToDo {
    private String titel, info;
    private int wichtig, id;
    private DateFormat date;
    private Time time;

    public ToDo(int id, String titel, String info, DateFormat date, Time time, int wichtig){
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

    public DateFormat getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
