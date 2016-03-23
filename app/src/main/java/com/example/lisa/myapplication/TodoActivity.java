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
    ArrayList<ToDo> allTasks ;
    ArrayList<String> myData;

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
        CustomAdapter ca = new CustomAdapter(this,allTasks, ds);
        lv.setAdapter(ca);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout:
                startActivity(new Intent(this, TodoActivity.class));
                break;
            case R.id.fab:
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
                startActivity(new Intent(this, Task.class));
                break;
            case R.id.action_deleteall:
                alleloeschenallert();
                break;
            case R.id.action_sortDate:
                sortierenWichtig();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    public void sortierenWichtig(){
        Collections.sort(allTasks, new Comparator<ToDo>() {
            @Override
            public int compare(ToDo td1, ToDo td2) {
                return td1.getTitel().compareToIgnoreCase(td2.getTitel());
            }

        });
        CustomAdapter ca2 = new CustomAdapter(this,allTasks,ds);
        lv.setAdapter(ca2);
    }

}