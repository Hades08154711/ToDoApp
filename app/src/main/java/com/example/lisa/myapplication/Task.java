package com.example.lisa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Task extends AppCompatActivity {

    Button bBestätigen;
    Appdaten toDo;
    int bWichtig;
    EditText etTitel, etBeschreibung, etDatum, etUhrzeit;

    private String titel, beschreibung, datum, uhrzeit;
    private int wichtig;

    public Task(String titel , String beschreibung, String datum, String uhrzeit, int wichtig){
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        etTitel = (EditText) findViewById(R.id.Titel);
        etBeschreibung = (EditText) findViewById(R.id.Beschreibung);
        etDatum = (EditText) findViewById(R.id.Datum);
        etUhrzeit = (EditText) findViewById(R.id.Uhrzeit);
        bWichtig = (int) (R.id.Wichtig);

        bBestätigen = (Button)(R.id.bBestätigen);
        bBestätigen.setOnClickListener(this);
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
        datum = etDatum.toString();
        uhrzeit = etUhrzeit.toString();
        wichtig = bWichtig;

        Task neueTask = new Task(titel, beschreibung, datum, uhrzeit, wichtig );
        toDo = new Appdaten(neueTask);


    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bBestätigen:
                addToDo();
                startActivity(new Intent(this, MainActivity.class));
                break;
//            case R.id.fab:
//                startActivity(new Intent(this, Task.class));


        }
    }
    public String getTitel() {
        return titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getDatum() {
        return datum;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }

    public int getWichtig() {
        return wichtig;
    }
}
