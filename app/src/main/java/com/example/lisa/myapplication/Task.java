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

    Button bBestaetigen;
    Appdaten toDo;
    int bWichtig;
    EditText etTitel, etBeschreibung;
    SimpleDateFormat simpleFormat;
    private String titel, beschreibung;
    Date datum;
    Time uhrzeit;
    private int wichtig;
    private DataSource ds;
    long longTime;
    EditText etDate;
    EditText etTime;
    DatePicker dp;
    TimePicker tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ds = new DataSource(this);
        ds.open();
        setContentView(R.layout.activity_task);
        etTitel = (EditText) findViewById(R.id.Titel);
        etBeschreibung = (EditText) findViewById(R.id.Beschreibung);
        dp = (DatePicker) findViewById(R.id.datePicker);
        tp = (TimePicker) findViewById(R.id.timePicker);
//        etDate = (EditText) findViewById(R.id.Datum);
//        etTime = (EditText) findViewById(R.id.Uhrzeit);



        bWichtig = (int) (R.id.Wichtig);

        bBestaetigen = (Button) findViewById(R.id.bBestaetigen);
        bBestaetigen.setOnClickListener(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
}
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    private void addToDo(){
        titel = etTitel.toString();
        beschreibung = etBeschreibung.toString();
//        simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//        try {
//            datum = simpleFormat.parse(etDate.getText().toString());
//        }catch(ParseException e){
//            e.printStackTrace();
//        }
//        longTime = Long.parseLong(etTime.getText().toString());
//        uhrzeit.setTime(longTime);
//        wichtig = bWichtig;
//
//        if(ds.addTodo(titel, beschreibung, datum, uhrzeit, wichtig)){
//
//        }else{
//
//        }
//        Task neueTask = new Task(titel, beschreibung, datum, uhrzeit, wichtig );
//        toDo = new Appdaten(neueTask);


    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bBestaetigen:
                addToDo();
                startActivity(new Intent(this, MainActivity.class));
                break;
//            case R.id.fab:
//                startActivity(new Intent(this, Task.class));


        }
    }

}
