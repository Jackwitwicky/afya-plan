package com.afyaplan.afya_plan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText firstname, lastname, phone, idNumber, dob, password;
    private Button register_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstname = (EditText) findViewById(R.id.editTextFirstName);
        lastname = (EditText) findViewById(R.id.editTextLastName);
        phone = (EditText) findViewById(R.id.editTextPhone);
        idNumber = (EditText) findViewById(R.id.editTextId);
        dob = (EditText) findViewById(R.id.editTextDob);
        firstname = (EditText) findViewById(R.id.editTextPassword);
        register_user = (Button) findViewById(R.id.buttonRegister);
        register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
