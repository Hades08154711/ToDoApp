package com.example.lisa.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {


    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;
    private DataSource ds;
    private static final String LOG_TAG = DataSource.class.getSimpleName();

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ds = new DataSource(this);
        ds.open();

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword =(EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                attemptLogin();
                break;

            case R.id.tvRegisterLink:

                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void  attemptLogin() {

        // Reset errors.
        etUsername.setError(null);
        etPassword.setError(null);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid user.
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
            User user = new User(username,password);
            if(ds.loginUser(user)){
                startActivity(new Intent(this, MainActivity.class));
            }else{
//                etPassword.setError(getString(R.string.error_invalid_email));
//                etPassword.setError(getString(R.string.error_invalid_password));
//                focusView = etPassword;
                cancel = true;
            }

        }
    }
    private boolean isUsernameValid(String user) {
        return !user.contains("'");
    }

    private boolean isPasswordValid(String password) {
        return !password.contains("'");
    }

}
