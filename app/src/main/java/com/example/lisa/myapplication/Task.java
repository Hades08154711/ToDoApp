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
    Boolean bWichtig;
    EditText etTitel, etBeschreibung, etDatum, etUhrzeit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        etTitel = (EditText) findViewById(R.id.Titel);
        etBeschreibung = (EditText) findViewById(R.id.Beschreibung);
        etDatum = (EditText) findViewById(R.id.Datum);
        etUhrzeit = (EditText) findViewById(R.id.Uhrzeit);
        bWichtig = (Boolean) (R.id.Wichtig);

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
    private void addToDo(Task task){

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bBestätigen:

                addToDo(this);
                startActivity(new Intent(this, MainActivity.class));
                break;
//            case R.id.fab:
//                startActivity(new Intent(this, Task.class));


        }
    }
}
