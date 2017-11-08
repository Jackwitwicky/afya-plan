package com.afyaplan.afya_plan.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.afyaplan.afya_plan.ChamaOptionsActivity;
import com.afyaplan.afya_plan.InviteMembers;
import com.afyaplan.afya_plan.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentContribute extends Fragment {
    Button checkout;
    EditText amount, phone;
    String txt_amount, txt_phone;
    ProgressBar progressBar;
    TextView responseView;
    String data;


    public FragmentContribute() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_contribute, container, false);
        responseView = (TextView) v.findViewById(R.id.title);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        amount = (EditText) v.findViewById(R.id.amount);

        phone = (EditText) v.findViewById(R.id.editText2);

        checkout = (Button) v.findViewById(R.id.button);


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_amount = amount.getText().toString();

                txt_phone = phone.getText().toString();

                if (!txt_phone.equals("") && !txt_amount.equals("")) {
//                    try {
//                        //String response = GetText(txt_amount, txt_phone);
//                        new RetrieveFeedTask().execute();
//                        // Toast.makeText(getApplicationContext(), "Heheh", Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//
//                    }

                    final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Sending Contribution...");
                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //open the welcome activity
                            //start chama options activity
                            progressDialog.dismiss();

                            Toast.makeText(getActivity(), "Contribution has been sent to your chama",
                                    Toast.LENGTH_SHORT).show();

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_fragment_containers, new FragmentChamaDetails());
                            transaction.commit();
                        }
                    }, 2500);
                } else {
                    Toast.makeText(v.getContext(), "Both fields must be filled", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return v;
    }


    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");
            try {
                data = URLEncoder.encode("amount", "UTF-8")
                        + "=" + URLEncoder.encode(amount.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("number", "UTF-8") + "="
                        + URLEncoder.encode(phone.getText().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {

            }
        }

        protected String doInBackground(Void... urls) {

            // Do some validation here

            try {

                URL url = new URL("http://codetroids.com/mpesa/requestcheckout.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
                //   os.close();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            responseView.setText(response);
        }
    }

}
