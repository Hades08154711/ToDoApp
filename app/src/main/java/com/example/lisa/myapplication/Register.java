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

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button bRegister;
    EditText etUsername, etPassword, etPassword2;
    private DataSource ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
        ds = new DataSource(this);
        ds.open();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                attemptRegister();
                break;

        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void  attemptRegister() {

        // Reset errors.
        etUsername.setError(null);
        etPassword.setError(null);
        etPassword2.setError(null);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String password2 = etPassword2.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if(!password.equals(password2)){
            // TODO richtigen error einfügen
            etPassword2.setError(getString(R.string.error_invalid_password));
            focusView = etPassword2;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // TODO richtige eroor in string.xml eintragen
        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            etUsername.setError(getString(R.string.error_field_required));
            focusView = etUsername;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            etUsername.setError(getString(R.string.error_invalid_username));
            focusView = etUsername;
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

            User u = new User(username,password);
            //ds.open();
            if(ds.registerUser(u)){
                ds.close();
                startActivity(new Intent(this, MainActivity.class));
            }else{
                //TODO error massages richtig augeben
//                etPassword.setError(getString(R.string.error_invalid_email));
//                etPassword.setError(getString(R.string.error_invalid_password));
//                focusView = etPassword;
                cancel = true;
               // ds.close();
            }
        }
    }
    private boolean isUsernameValid(String user) {
        return !user.contains("'");
    }

    private boolean isPasswordValid(String password) {
        //TODO sonderzeichen nicht erlauben
        for (int i =0; i<password.length(); i++) {
            if (password.charAt(i) > -1 && password.charAt(i) <=47
                    || password.charAt(i) >= 58&& password.charAt(i) <= 64
                    || password.charAt(i) >= 91&& password.charAt(i) <= 96){
                return false;
            }
        }
        Pattern p = Pattern.compile("([A-Za-z].+[0-9])");
        Matcher m = p.matcher(password);

        return password.length() > 4 && password.length() < 21 && m.find();
    }

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

