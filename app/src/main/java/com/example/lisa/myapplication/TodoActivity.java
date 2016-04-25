package com.example.lisa.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener{

    Appdaten ad;
    Button bLogout;
    DataSource ds;
    private ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<ToDo> allTasks, sortTasks ;
    ArrayList<ToDo> wichtig, unwichtig;
    ArrayList<ToDo> sicherungAllTasks ;
    ArrayList<String> myData;
    CustomAdapter ca;
    private static final String LOG_TAG = DataSource.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbTodo);
        setSupportActionBar(toolbar);

        ad = new Appdaten();
        ds = new DataSource(this);
        ds.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ;
        createListView();
    }

    private void createListView(){
        lv = (ListView) findViewById(R.id.listView);
        allTasks  = ds.getAllTasks();
        sicherungAllTasks = new ArrayList<ToDo>();
        //sicherungAllTasks = ds.getAllTasks();
        ca = new CustomAdapter(this,allTasks, ds, ad);
        lv.setAdapter(ca);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout:
                startActivity(new Intent(this, TodoActivity.class));
                break;
            case R.id.fab:
                ad.deleteTodo(this);
                startActivity(new Intent(this, Task.class));
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private static boolean hide = false;
    private void hideFinishedToDos(){
        hide = !hide;
        if(hide){
            for (int i = 0; i < allTasks.size(); i++){
                if(allTasks.get(i).getErledigt() == 1){
//                lv.setVisibility(View.INVISIBLE);
                    sicherungAllTasks.add(allTasks.get(i));
                    allTasks.remove(i);
                    //TODO
                    Log.e(LOG_TAG, "Verstecken");
                }
            }
        }else{
            for (int j = 0; j< sicherungAllTasks.size(); j++){
                allTasks.add(sicherungAllTasks.get(j));
                sicherungAllTasks.remove(j);
            }

            Log.e(LOG_TAG, "Anzeigen");
        }

        ca.notifyDataSetChanged();
        ca.notifyDataSetInvalidated();
    }

    private void alleloeschenallert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(TodoActivity.this);
        alert.setTitle("Vorsicht!");
        alert.setMessage("Sind Sie sich sicher?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO
                ds.deleteAll();
                ;
                Intent myIntent = new Intent(((Dialog) dialog).getContext(), TodoActivity.class);
                startActivity(myIntent);
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();

    }

    private void getDateandTime(ToDo adTodo){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//        int year = Integer.valueOf(adTodo.getDate().split("-")[0]);
//        int month = Integer.valueOf(adTodo.getDate().split("-")[1])+1;
//        int day = Integer.valueOf(adTodo.getDate().split("-")[2]);
//
//
//        int hour = Integer.valueOf(adTodo.getTime().split(":")[0]);
//        int minute = Integer.valueOf(adTodo.getTime().split(":")[1]);

        String test = df.format(Date.parse((adTodo.getDate() + " " + adTodo.getTime())));
        Log.e(LOG_TAG,"test");

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_verwaltung:
                startActivity(new Intent(this, Verwaltung.class));
                break;
            case R.id.action_newtodo:
                ad.deleteTodo(this);
                startActivity(new Intent(this, Task.class));
                break;
            case R.id.action_deleteall:
                alleloeschenallert();
                break;
            case R.id.action_hideDone:
                hideFinishedToDos();
                break;
            case R.id.action_sortDate:
                sortierenDatumUhrzeit();
                break;
            case R.id.action_sortImpDate:
                sortierenWichtigDatum();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    public void sortierenWichtigDatum(){
        unwichtig = new ArrayList<ToDo>();
        wichtig = new ArrayList<ToDo>();
        sortTasks = new ArrayList<ToDo>();

        for (int p = 0; p < allTasks.size(); p++){
            if(allTasks.get(p).getWichtig() == 0){
                unwichtig.add(allTasks.get(p));
            }else{
                wichtig.add(allTasks.get(p));
            }
        }


        Collections.sort(unwichtig, new Comparator<ToDo>() {
            @Override
            public int compare(ToDo td1, ToDo td2) {
                //getDateandTime(td1);
                String s1 = td1.getDate()+" "+td1.getTime();
                String s2 = td2.getDate()+" "+td2.getTime();
                return s1.compareToIgnoreCase(s2);
            }

        });



        Collections.sort(wichtig, new Comparator<ToDo>() {
            @Override
            public int compare(ToDo td1, ToDo td2) {
                String s1 = td1.getDate() + " " + td1.getTime();
                String s2 = td2.getDate() + " " + td2.getTime();
                return s1.compareToIgnoreCase(s2);
            }

        });

        for (int i= 0; i < wichtig.size(); i++){
            sortTasks.add(wichtig.get(i));
        }
        for (int j= 0; j < unwichtig.size(); j++){
            sortTasks.add(unwichtig.get(j));
        }
        allTasks.clear();
        for (int s = 0; s < sortTasks.size(); s++){
            allTasks.add(sortTasks.get(s));
        }

        ca.notifyDataSetChanged();
        ca.notifyDataSetInvalidated();

    }

    public void sortierenDatumUhrzeit(){
        sortTasks = new ArrayList<ToDo>();

        Collections.sort(allTasks, new Comparator<ToDo>() {
            @Override
            public int compare(ToDo td1, ToDo td2) {
                //getDateandTime(td1);
                String s1 = td1.getDate()+" "+td1.getTime();
                String s2 = td2.getDate()+" "+td2.getTime();
                return s1.compareToIgnoreCase(s2);
            }

        });

//        allTasks.clear();
//        for (int s = 0; s < sortTasks.size(); s++){
//            allTasks.add(sortTasks.get(s));
//        }

        ca.notifyDataSetChanged();
        ca.notifyDataSetInvalidated();

    }
}