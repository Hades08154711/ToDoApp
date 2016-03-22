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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener{

    Button bLogout;
    DataSource ds;
    private ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<ToDo> allTasks ;
    ArrayList<String> myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbTodo);
        setSupportActionBar(toolbar);

        ds = new DataSource(this);
        ds.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(firstLogin()){
            startActivity(new Intent(this, Register.class));
        }else{
            createListView();
        }
    }
    // method to remove list item
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                TodoActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub
                ds.removeItem(allTasks.get(deletePosition));
                // main code on after clicking yes
                myData.remove(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }

    private void createListView(){
        lv = (ListView) findViewById(R.id.listView);
        allTasks  = ds.getAllTasks();
        myData = new ArrayList<String>();
        String test;
        for (int i = 0; i< allTasks.size(); i++){
            myData.add(allTasks.get(i).getTitel());
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, myData);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        for (int j = 0; j< allTasks.size(); j++){
            if(allTasks.get(j).getErledigt() == 1){
                lv.setItemChecked(j, true);
            }
        }
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                removeItemFromList(position);
//                //Do your tasks here
//                final boolean dialogResult = false;
//                AlertDialog.Builder alert = new AlertDialog.Builder(TodoActivity.this);
//                alert.setTitle("Alert!!");
//                alert.setMessage("Are you sure to delete record");
//                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //TODO
//                        dialogResult = true;
//                        dialog.dismiss();
//
//                    }
//                });
//                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        dialog.dismiss();
//                    }
//                });
//
//                alert.show();

                return true;
            }
        });
    }

    private boolean firstLogin(){
        //ds.open();
        boolean result = ds.firstLogin();
        return result;
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
                ds.deleteAll();;
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


        }
        return super.onOptionsItemSelected(item);
    }
}
