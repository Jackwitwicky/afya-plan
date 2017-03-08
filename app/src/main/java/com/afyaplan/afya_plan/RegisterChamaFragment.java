package com.afyaplan.afya_plan;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterChamaFragment extends DialogFragment {

    private static final String REGISTER_CHAMA_URL = "";

    private static final String KEY_CHAMA_NAME = "ChamaName";
    private static final String KEY_CHAMA_AMOUNT= "ChamaAmount";
    private static final String KEY_CHAMA_RATE = "ChamaRate";

    //define views for register chama dialog
    private EditText chamaNameEdit;
    private EditText chamaAmountEdit;
    private Spinner contributionRateSpinner;


    private String chamaName;
    private String chamaAmount;
    private String contributionRate;

    //private OnFragmentInteractionListener mListener;
    ProgressDialog progressDialog;

    public RegisterChamaFragment() {
        // Required empty public constructor
    }

    public static RegisterChamaFragment newInstance(String title) {

        RegisterChamaFragment frag = new RegisterChamaFragment();

        Bundle args = new Bundle();

        args.putString("title", title);

        frag.setArguments(args);

        return frag;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View registerChama = inflater.inflate(R.layout.fragment_register_chama, container, false);

        return registerChama;
    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        //initialize the views
        chamaNameEdit = (EditText)
                view.findViewById(R.id.chamaNameEdit);
        chamaAmountEdit = (EditText)
                view.findViewById(R.id.chamaAmountEdit);
        contributionRateSpinner = (Spinner)
                view.findViewById(R.id.contributionRateSpinner);



        // Fetch arguments from bundle and set title

        String title = getArguments().getString("title", "Create Chama");

        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field

        chamaNameEdit.requestFocus();

        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }



    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validate() {
        boolean isValidated = true;
        chamaName = chamaNameEdit.getText().toString().trim();
        chamaAmount = chamaAmountEdit.getText().toString().trim();
        contributionRate = contributionRateSpinner.getSelectedItem().toString().trim();

        //check if chamaName is empty

        if(chamaName.isEmpty() || chamaName == null) {
            isValidated = false;
            chamaNameEdit.setError("Cannot be blank!");
        }
        else {
            chamaNameEdit.setError(null);
        }

        //check if amount is empty
        if(chamaAmount.isEmpty() || chamaAmount == null) {
            isValidated = false;
            chamaAmountEdit.setError("Cannot be empty");
        }
        else {
            chamaAmountEdit.setError(null);
        }

        return isValidated;
    }

    //class to send data to server
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
                            Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
            progressDialog.dismiss();
        }
    }
}
