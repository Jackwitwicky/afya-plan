package com.afyaplan.afya_plan;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afyaplan.afya_plan.com.savagelook.android.UrlJsonAsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private static final String REGISTER_USER_URL = "";

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
    private int nationalID;
    private String phoneNumber;
    private String dob;
    private String password;
    private String passwordConfirm;

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
            try {

                if(!nationalIDEdit.getText().toString().isEmpty()) {
                    firstName = firstNameEdit.getText().toString();
                    lastName = lastNameEdit.getText().toString();
                    nationalID = Integer.parseInt(nationalIDEdit.getText().toString());
                    phoneNumber = phoneNumberEdit.getText().toString();
                    dob = dobEdit.getText().toString();
                    password = passwordEdit.getText().toString();
                    passwordConfirm = passwordConfirmEdit.getText().toString();

                    validateFields();
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

    //class to post user data to the server
    private class SaveUserTask extends UrlJsonAsyncTask {
        //constructor
        public SaveUserTask(Context context) {
            super(context);
        }

        @Override
        public JSONObject doInBackground(String... urls) {
            JSONObject json = new JSONObject();
            try {

                URL url = new URL(REGISTER_USER_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                //write the email and password to stream

                //OutputStreamWriter writer = new OutputStreamWriter(out);
//                String data = "{\"user\": {\"email\": \"" + email + "\"," +
//                        " \"password\": \"" + password + "\", \"password_confirmation\": \"" + passwordConfirmation + "\"," +
//                        " \"user_name\": \"" + username + "\"}}";
                //wr.writeBytes(data);
                wr.flush();
                wr.close();
                //writer.append("{\"user\": {\"email\": \"jack@gmail.com\", \"password\": \"jack123\", \"password_confirmation\": \"jack123\", \"user_name\": \"jack\"}}");
                //writer.flush();
                urlConnection.connect();

                //read the response
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                StringBuffer buffer = new StringBuffer();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;

                while((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                String response = buffer.toString();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
    }
}