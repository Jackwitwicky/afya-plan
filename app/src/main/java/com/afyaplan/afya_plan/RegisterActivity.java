package com.afyaplan.afya_plan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String REGISTER_URL = " http://ethy.co.ke/Afya-Plan/api/Register.php";
    ProgressBar registerPb;
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ID = "id";
    public static final String KEY_DOB = "dob";
    public static final String KEY_PASS = "password";

    private EditText editTextFullName;
    private EditText editTextPhone;
    private EditText editTextId;
    private EditText editTextdob;
    private EditText editTexPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPb = (ProgressBar) findViewById(R.id.progressBarRegister);

        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextdob=(EditText)findViewById(R.id.editTextDOB);
        editTexPassword=(EditText)findViewById(R.id.editTextPassWord);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            RegisterAsync registerAsync = new RegisterAsync();

            registerAsync.execute();
        }
    }


    private class RegisterAsync extends AsyncTask<String, String, String> {
        final String fullname = editTextFullName.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String id = editTextId.getText().toString().trim();
        final String dob = editTextdob.getText().toString().trim();

        final String password=editTexPassword.getText().toString();

        @Override
        protected void onPreExecute() {
            registerPb.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_FULLNAME, fullname);
                    params.put(KEY_PHONE, phone);
                    params.put(KEY_ID, id);
                    params.put(KEY_DOB, dob);
                    params.put(KEY_PASS, password);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            requestQueue.add(stringRequest);

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            registerPb.setVisibility(View.INVISIBLE);
        }
    }
}