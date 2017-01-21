package com.afyaplan.afya_plan;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afyaplan.afya_plan.com.savagelook.android.UrlJsonAsyncTask;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String REGISTER_URL = "http://ethy.co.ke/Afya-Plan/api/register.php";

    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_IDNUMBER = "id";
    private static final String KEY_DOB = "dob";
    private static final String KEY_PASSWORD = "password";

    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText nationalIDEdit;
    private EditText phoneNumberEdit;
    private EditText dobEdit;
    private EditText passwordEdit;
    private EditText passwordConfirmEdit;
    private Button saveUserButton;

    //values of edit texts
    private String firstName;
    private String lastName;
    private String nationalID;
    private String phoneNumber;
    private String dob;
    private String password;
    private String passwordConfirm;

    private boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize the views
        firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        nationalIDEdit = (EditText) findViewById(R.id.nationalIDEdit);
        phoneNumberEdit = (EditText) findViewById(R.id.phoneNumberEdit);
        dobEdit = (EditText) findViewById(R.id.dobEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        passwordConfirmEdit = (EditText) findViewById(R.id.passwordConfirmEdit);
        saveUserButton = (Button) findViewById(R.id.saveUserButton);
    }

    //method to handle button click
    public void onClick(View view) {
        int viewID = view.getId();

        if(viewID == R.id.saveUserButton) {

            Toast.makeText(RegisterActivity.this, "I am working!", Toast.LENGTH_SHORT).show();
            try {

                if(!nationalIDEdit.getText().toString().isEmpty()) {
                    firstName = firstNameEdit.getText().toString();
                    lastName = lastNameEdit.getText().toString();
                    nationalID = nationalIDEdit.getText().toString();
                    phoneNumber = phoneNumberEdit.getText().toString();
                    dob = dobEdit.getText().toString();
                    password = passwordEdit.getText().toString();
                    passwordConfirm = passwordConfirmEdit.getText().toString();

                    if(isValid) {
                        registerUser();
                    }
                }
                else {
                    nationalIDEdit.setText("");
                    Toast.makeText(RegisterActivity.this, "Your national id cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
            catch (NumberFormatException e) {
                Toast.makeText(RegisterActivity.this, "Your national ID cannot contain a letter", Toast.LENGTH_SHORT).show();
            }


        }
    }

    //method to verify the data entered
    private void validateFields() {

        //check that firstName is okay
        if(!firstName.isEmpty() && firstName != null) {
            //it contains a number
            if (!firstName.matches(".*\\\\d+.*")) {
                //check that last name is okay
                if(!lastName.isEmpty() && lastName != null) {
                    if(lastName.matches(".*\\\\d+.*")) {
                        //check that national id is a number
                        if(nationalID.matches("[0-9]+")) {
                            //check that phone number is okay
                            if(!phoneNumber.isEmpty() && phoneNumber != null) {
                                if(phoneNumber.matches("[0-9]+")) {
                                    if(phoneNumber.length() == 10) {
                                        //check that dob is okay

                                        //check that password is okay
                                        if (!password.isEmpty() && password != null) {
                                            //check that password confirm matches password
                                            if(password.equals(passwordConfirm)) {
                                                Toast.makeText(RegisterActivity.this, "Hooray", Toast.LENGTH_SHORT).show();
                                                isValid = true;
                                            }
                                            else {
                                                passwordConfirmEdit.setText("");
                                                Toast.makeText(RegisterActivity.this, "The two passwords do no match", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                            passwordEdit.setText("");
                                            Toast.makeText(RegisterActivity.this, "Your password cannot be empty", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        phoneNumberEdit.setText("");
                                        Toast.makeText(RegisterActivity.this, "Your phone number is invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    phoneNumberEdit.setText("");
                                    Toast.makeText(RegisterActivity.this, "Your phone number cannot contain a letter", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                phoneNumberEdit.setText("");
                                Toast.makeText(RegisterActivity.this, "Your phone number cannot be empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "National ID cannot contain a letter", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else {
                        lastNameEdit.setText("");
                        Toast.makeText(RegisterActivity.this, "Your last name cannot contain a number", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Ensure you have entered your last name", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                firstNameEdit.setText("");
                Toast.makeText(RegisterActivity.this, "Your first name cannot contain a number", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(RegisterActivity.this, "Ensure you have entered your first name", Toast.LENGTH_SHORT).show();
            firstNameEdit.setText("");
        }
    }

    public void registerUser() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put(KEY_FIRSTNAME, firstName);
                params.put(KEY_LASTNAME, lastName);
                params.put(KEY_IDNUMBER, nationalID);
                params.put(KEY_PHONE, phoneNumber);
                params.put(KEY_DOB, dob);
                params.put(KEY_PASSWORD, password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}