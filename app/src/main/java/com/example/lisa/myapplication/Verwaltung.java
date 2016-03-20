package com.example.lisa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verwaltung extends AppCompatActivity implements View.OnClickListener{

    Button bSave;
    EditText etOldPassword, etNewPassword, etRetypePassword;
    private DataSource ds;
    private String _username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verwaltung);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etRetypePassword = (EditText) findViewById(R.id.etRetypePassword);

        bSave = (Button) findViewById(R.id.bSaveNewPassword);

        bSave.setOnClickListener(this);
        // TODO muß angepasst werden
        _username = "affe";
        ds = new DataSource(this);
        ds.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSaveNewPassword:
                attemptNewPassword();
                break;

        }
    }

    private boolean checkOldPasswordValid(String oldPW){

        User user = new User(_username,oldPW);
        return ds.loginUser(user);
    }

    private boolean isPasswordValid(String password) {
        //TODO sonderzeichen nicht erlauben
        Pattern p = Pattern.compile("([A-Za-z].+[0-9])");
        Matcher m = p.matcher(password);

        return password.length() > 4 && password.length() < 21 && m.find();
    }

    private void attemptNewPassword(){
        // Reset errors.
        etOldPassword.setError(null);
        etNewPassword.setError(null);
        etRetypePassword.setError(null);

        String oldPW = etOldPassword.getText().toString();
        String newPW = etNewPassword.getText().toString();
        String retypePW = etRetypePassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //TODO richten error angeben
        // Check for a valid old password, if the user entered one.
        if(TextUtils.isEmpty(oldPW) || !checkOldPasswordValid(oldPW)){
            etOldPassword.setError(getString(R.string.error_invalid_email));
            focusView = etOldPassword;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!newPW.equals(retypePW)){
            // TODO richtigen error einfügen
            etRetypePassword.setError(getString(R.string.error_invalid_password));
            focusView = etRetypePassword;
            cancel = true;
        }

        //TODO richten error einfügen
        if (TextUtils.isEmpty(newPW) || !isPasswordValid(newPW)) {
            etNewPassword.setError(getString(R.string.error_invalid_password));
            focusView = etNewPassword;
            cancel = true;
        }

        // TODO richtige eroor in string.xml eintragen
        // Check for a valid username.
        if (TextUtils.isEmpty(retypePW)) {
            etRetypePassword.setError(getString(R.string.error_field_required));
            focusView = etRetypePassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);

            User u = new User(_username,newPW);
            ds.setNewPassword(u);
            ds.close();
            startActivity(new Intent(this, MainActivity.class));

        }
    }
}
