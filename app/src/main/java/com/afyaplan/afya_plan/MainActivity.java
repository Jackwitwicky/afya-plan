package com.afyaplan.afya_plan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText firstname, lastname, phone, idNumber, dob, password;
    Button btn_register_user;
    public static final String REGISTER_URL = "URL for the API";

    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_IDNUMBER = "id";
    public static final String KEY_DOB = "dob";
    public static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstname = (EditText) findViewById(R.id.editTextFirstName);
        lastname = (EditText) findViewById(R.id.editTextLastName);
        phone = (EditText) findViewById(R.id.editTextPhone);
        idNumber = (EditText) findViewById(R.id.editTextId);
        dob = (EditText) findViewById(R.id.editTextDob);
        password = (EditText) findViewById(R.id.editTextPassword);
        btn_register_user = (Button) findViewById(R.id.buttonRegister);
        btn_register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    public void registerUser() { //get data from the fields
        final String stringFirstName = firstname.getText().toString().trim();
        final String stringLastName = lastname.getText().toString().trim();
        final String stringPhone = phone.getText().toString().trim();
        final String stringID = idNumber.getText().toString().trim();
        final String stringDob = dob.getText().toString().trim();
        final String stringPassword = password.getText().toString().trim();

        //network call
        StringRequest postRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                            String site = jsonResponse.getString("site"),
                                    network = jsonResponse.getString("network");
                            System.out.println("Site: "+site+"\nNetwork: "+network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put(KEY_FIRSTNAME, stringFirstName);
                params.put(KEY_LASTNAME, stringLastName);
                params.put(KEY_PHONE, stringPhone);
                params.put(KEY_IDNUMBER, stringID);
                params.put(KEY_DOB, stringDob);
                params.put(KEY_PASSWORD, stringPassword);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

}
