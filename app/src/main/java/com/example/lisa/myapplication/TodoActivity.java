package com.example.lisa.myapplication;

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
            test();
          //  createLinearLayout();
        }
    }

    private void test(){
        lv = (ListView) findViewById(R.id.listView);
        final ArrayList<ToDo> allTasks = ds.getAllTasks();
        final ArrayList<String> myData = new ArrayList<String>() ;
        String test;
        for (int i = 0; i< allTasks.size(); i++){
            myData.add(allTasks.get(i).getTitel());
        }

//        if (allTasks.size() == 0){
//            CheckBox cb = new CheckBox(getApplicationContext());
//            cb.setText("dummy");
//            cb.setTextColor(getResources().getColorStateList(R.color.colorBlack));
//            ll.addView(cb);
//        }else{
//            for(int i = 0; i < allTasks.size(); i++) {
//                CheckBox cb = new CheckBox(getApplicationContext());
//                //  cb.setText(allTasks.get(i).getTitel());
//                cb.setText("test");
//                cb.setTextColor(getResources().getColorStateList(R.color.colorBlack));
//                ll.addView(cb);
//            }
//        }
       // lv.setAdapter(new ArrayAdapter<String>(this,0, myData){
        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_multiple_choice, myData));
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

//            private View row;
//            private LayoutInflater inflater = getLayoutInflater();
//            private CheckBox cb;
//            TextView tv;
//
//            public View getView(int position, View convertView, ViewGroup parent){
//
//                row = inflater.inflate(android.R.layout.simple_list_item_1,parent, false);
//                cb = (CheckBox) row.findViewById(android.R.id.checkbox);
//                cb.setText(allTasks.get(position).getTitel());
//                tv = (TextView) row.findViewById(android.R.id.text1);
//                tv.setText(myData.get(position));
//                return row;
//            }
//        });
    }


    private void createLinearLayout(){

        ScrollView sv = new ScrollView(getApplicationContext());

        final LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        ArrayList<ToDo> allTasks = ds.getAllTasks();

        if (allTasks.size() == 0){
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText("dummy");
            cb.setTextColor(getResources().getColorStateList(R.color.colorBlack));
            ll.addView(cb);
        }else{
            for(int i = 0; i < allTasks.size(); i++) {
                CheckBox cb = new CheckBox(getApplicationContext());
                //  cb.setText(allTasks.get(i).getTitel());
                cb.setText("test");
                cb.setTextColor(getResources().getColorStateList(R.color.colorBlack));
                ll.addView(cb);
            }
        }


        Button logout = new Button(getApplicationContext());
        logout.setText("Logout");
        logout.setId(R.id.bLogout);
        logout.setOnClickListener(this);
        ll.addView(logout);

        this.setContentView(sv);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
