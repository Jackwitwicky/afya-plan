package com.afyaplan.afya_plan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChamaOptionsActivity extends AppCompatActivity {

    private static final String REGISTER_CHAMA_URL = "";

    private static final String KEY_CHAMA_NAME = "ChamaName";
    private static final String KEY_CHAMA_AMOUNT= "ChamaAmount";
    private static final String KEY_CHAMA_RATE = "ChamaRate";

    //define views for register chama dialog
    EditText chamaNameEdit;
    EditText chamaAmountEdit;
    Spinner contributionRateSpinner;

    String chamaName;
    String chamaAmount;
    String contributionRate;

    boolean isValidated = false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chama_options);


    }

    public void onClick(View view) {
        int viewID = view.getId();

        switch (viewID) {
            case R.id.registerChamaButton:
                //open dialog to register chama
                //inflate the custom register view and define the dialog box
                LayoutInflater myInflater= LayoutInflater.from(this);
                final View registerChama=myInflater.inflate(R.layout.dialog_register_chama, null);

                new AlertDialog.Builder(this)
                        .setTitle("Create Chama")
                        .setView(registerChama)
                        .setMessage("Enter your chama details to proceed")
                        .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //initialize the views
                                chamaNameEdit = (EditText)
                                        registerChama.findViewById(R.id.chamaNameEdit);
                                chamaAmountEdit = (EditText)
                                        registerChama.findViewById(R.id.chamaAmountEdit);
                                contributionRateSpinner = (Spinner)
                                        registerChama.findViewById(R.id.contributionRateSpinner);

                                if(validate()) {
                                    if(isOnline()) {
                                        progressDialog = new ProgressDialog(ChamaOptionsActivity.this,
                                                R.style.AppTheme_Dark_Dialog);
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setCancelable(false);
                                        progressDialog.setMessage("Creating Your Chama...");

                                        RegisterChamaAsync chamaAsync = new RegisterChamaAsync();
                                        chamaAsync.execute();
                                    }
                                    else {
                                        Toast.makeText(ChamaOptionsActivity.this,
                                                "You are offline. Enable data to proceed", Toast.LENGTH_SHORT).show();
                                    }


                                }

                            }
                        }).show();
                break;
            case R.id.joinChamaButton:
                //join user to existing chama
                Toast.makeText(ChamaOptionsActivity.this, "Join Chama coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.browseChamaButton:
                //browse through chamas
                Toast.makeText(ChamaOptionsActivity.this, "Browse Chama coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
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

    private boolean validate() {
        chamaName = chamaNameEdit.getText().toString().trim();
        chamaAmount = chamaAmountEdit.getText().toString().trim();
        contributionRate = contributionRateSpinner.getSelectedItem().toString().trim();

        //check if chamaName is empty

        if(!chamaName.isEmpty() && chamaName != null) {
            if(!chamaAmount.isEmpty() && chamaAmount != null) {
                return true;
            }
            else {
                Toast.makeText(ChamaOptionsActivity.this, "Chama Amount cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(ChamaOptionsActivity.this,
                    "Your chama name is empty", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    //async task to register the chama
    private class RegisterChamaAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_CHAMA_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ChamaOptionsActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ChamaOptionsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_CHAMA_NAME, chamaName);
                    params.put(KEY_CHAMA_AMOUNT, chamaAmount);
                    params.put(KEY_CHAMA_RATE, contributionRate);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(ChamaOptionsActivity.this);
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
            progressDialog.dismiss();
        }
    }
}
