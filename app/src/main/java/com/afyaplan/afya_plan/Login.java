package com.afyaplan.afya_plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class Login extends AppCompatActivity implements View.OnClickListener {
    //Defining views
    private EditText editTextPhone;
    private EditText editTextPassword;
    private AppCompatButton buttonLogin;
    ProgressBar loginProgress;
    TextView signUpLink;
    private static final int REQUEST_SIGNUP = 0;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    //strings to hold entered phone and password
    private String phone;
    private String password;

    private AuthCallback authCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(Config.TWITTER_KEY, Config.TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // Do something with the session
                //Creating a shared preference
                SharedPreferences sharedPreferences = Login.this.getSharedPreferences(
                        Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                editor.putString(Config.PHONE_SHARED_PREF, phone);

                //Saving values to editor
                editor.commit();

                //Starting profile activity
                Intent intent = new Intent(Login.this, ChamaOptionsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
                Toast.makeText(Login.this, "Could not verify this user" , Toast.LENGTH_SHORT).show();
            }
        };

        //Initializing views
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);
        loginProgress = (ProgressBar) findViewById(R.id.progressBarLogin);
        signUpLink = (TextView) findViewById(R.id.linkSignup);

        //Adding click listener
        buttonLogin.setOnClickListener(this);

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(Login.this, MainBoard.class);
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {

        //Getting values from edit texts
        phone = editTextPhone.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        //check if values are good
        if(validate(phone, password)) {
            //check if connected
            if(isOnline()) {
                //Calling the login async function
                LoginAsync loginAsync = new LoginAsync();
                loginAsync.execute();
            }
            else {
                Toast.makeText(Login.this,
                        "You are offline. Enable data to proceed", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //validate entered fields
    public boolean validate(String phone, String password) {
        boolean isValidated = true;

        //check if phone is empty
        if(phone == null || phone.isEmpty()) {
            isValidated = false;
            editTextPhone.setError("Cannot leave this blank!");
        }
        else {
            editTextPhone.setError(null);
        }
        //check phone is correct length
        if(phone.length() != 10) {
            isValidated = false;
            editTextPhone.setError("Invalid phone number");
        }
        else {
            editTextPhone.setError(null);
        }
        //check password is not blank
        if(password == null || password.isEmpty()) {
            isValidated = false;
            editTextPassword.setError("Cannot leave this blank!");
        }
        else {
            editTextPassword.setError(null);
        }

        return isValidated;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    private class LoginAsync extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            loginProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {


            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //If we are getting success from server
                            if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {

                                String tempPhone = phone.substring(1, phone.length());

                                AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                                        .withAuthCallBack(authCallback)
                                        .withPhoneNumber("+254" + tempPhone);

                                Digits.authenticate(authConfigBuilder.build());
//
                            } else {
                                //If the server response is not success
                                //Displaying an error message on toast

                                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want
                            Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Config.KEY_PHONE, phone);
                    params.put(Config.KEY_PASSWORD, password);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue

            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(stringRequest);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            loginProgress.setVisibility(View.INVISIBLE);
        }
    }
}