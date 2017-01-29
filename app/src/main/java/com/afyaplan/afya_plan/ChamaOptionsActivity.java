package com.afyaplan.afya_plan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chama_options);


    }

    public void onClick(View view) {
        int viewID = view.getId();

        switch (viewID) {
            case R.id.registerChamaButton:
                //open the register dialog fragment
                FragmentManager fm = getSupportFragmentManager();

                RegisterChamaFragment registerChamaFragment = RegisterChamaFragment.newInstance("Some Title");

                registerChamaFragment.show(fm, "fragment_register_chama");
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

}
