package com.example.lisa.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
    EditText etName, etAge, etUsername, etPassword;
    Appdaten toDo;
    ArrayList<Task> list;
    DataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ds = new DataSource(this);
        ds.open();
        if(firstLogin()){
            startActivity(new Intent(this, Register.class));
        }else{
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);



//            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//            fab.setOnClickListener(this);

            //createLinearLayout();
        }
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
            cb.setText(allTasks.get(i).getTitel());
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
                startActivity(new Intent(this, Login.class));
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
