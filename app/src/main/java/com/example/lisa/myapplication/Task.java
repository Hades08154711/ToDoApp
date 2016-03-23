package com.example.lisa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task extends AppCompatActivity implements View.OnClickListener{



    private CheckBox cbWichtig;
    private EditText etTitel, etBeschreibung;
    private DatePicker dp;
    private  TimePicker tp;
    private DataSource ds;
    private Button bBestaetigen, bCancel;
    private Appdaten ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ds = new DataSource(this);
        ds.open();
        setContentView(R.layout.activity_task);

        ad = new Appdaten();
        ToDo adTodo = ad.getTodo(this);

        etTitel = (EditText) findViewById(R.id.Titel);
        etBeschreibung = (EditText) findViewById(R.id.Beschreibung);
        dp = (DatePicker) findViewById(R.id.datePicker);
        tp = (TimePicker) findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        cbWichtig = (CheckBox) findViewById(R.id.cbWichtig);

        bBestaetigen = (Button) findViewById(R.id.bBestaetigen);
        bCancel = (Button) findViewById(R.id.bCancel);
        bBestaetigen.setOnClickListener(this);
        bCancel.setOnClickListener(this);

        if (adTodo.getTitel() != null){
            etTitel.setText(adTodo.getTitel());
            etBeschreibung.setText(adTodo.getTitel());
//            dp = (DatePicker) findViewById(R.id.datePicker);
//            tp = (TimePicker) findViewById(R.id.timePicker);
            if (adTodo.getWichtig() == 1) cbWichtig.setChecked(true);

            int year = Integer.valueOf(adTodo.getDate().split("-")[0]);
            int month = Integer.valueOf(adTodo.getDate().split("-")[1]);
            int day = Integer.valueOf(adTodo.getDate().split("-")[2]);
            dp.updateDate(year,month,day);

            int hour = Integer.valueOf(adTodo.getTime().split(":")[0]);
            int minute = Integer.valueOf(adTodo.getTime().split(":")[1]);
            tp.setCurrentHour(hour);
            tp.setCurrentMinute(minute);
        }
}
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    private void updateToDo(){
        ad = new Appdaten();
        ToDo adTodo = ad.getTodo(this);

        int wichtig = (cbWichtig.isChecked())? 1 : 0;

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        //TODO Warum - 1900
        String dateFormat = dateformat.format(new Date(dp.getYear()-1900, dp.getMonth(), dp.getDayOfMonth()));
        ds.updateTodo(adTodo.getId(), etTitel.getText().toString(), etBeschreibung.getText().toString(), dateFormat, tp.getCurrentHour().toString() + ":" + tp.getCurrentMinute().toString(), wichtig, adTodo.getErledigt());

    }
    private void addNewToDo(){

        int wichtig = (cbWichtig.isChecked())? 1 : 0;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        //TODO Warum - 1900
        String dateFormat = dateformat.format(new Date(dp.getYear()-1900, dp.getMonth(), dp.getDayOfMonth()));
        ds.addTodo(etTitel.getText().toString(), etBeschreibung.getText().toString(), dateFormat, tp.getCurrentHour().toString() + ":" + tp.getCurrentMinute().toString(),wichtig);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bBestaetigen:
                ToDo adTodo = ad.getTodo(this);
                if(adTodo.getTitel() == null){
                    addNewToDo();
                }else{
                    updateToDo();
                }
                startActivity(new Intent(this, TodoActivity.class));
                break;
            case R.id.bCancel:
                startActivity(new Intent(this, TodoActivity.class));
                break;
        }
    }

}
