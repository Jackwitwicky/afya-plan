package com.afyaplan.afya_plan.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.afyaplan.afya_plan.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRequest extends Fragment {


    public FragmentRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_request, container, false);

        Button requestButton = (Button) v.findViewById(R.id.requestButton);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Requesting Loan...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //open the welcome activity
                        //start chama options activity
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), "Request has been sent to the members",
                                Toast.LENGTH_SHORT).show();

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers, new FragmentChamaDetails());
                        transaction.commit();
                    }
                }, 2500);
            }
        });

        return v;
    }

}
