package com.afyaplan.afya_plan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
//                FragmentManager fm = getSupportFragmentManager();
//
//                RegisterChamaFragment registerChamaFragment = RegisterChamaFragment.newInstance("Some Title");
//
//                registerChamaFragment.show(fm, "fragment_register_chama");

                //inflate the custom login view and define the dialog box
                LayoutInflater myInflater= LayoutInflater.from(this);
                final View loginView=myInflater.inflate(R.layout.fragment_register_chama, null);


                new AlertDialog.Builder(this)
                        .setTitle("Register Chama")
                        .setView(loginView)

                        .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final String chamaName = ((EditText)
                                        loginView.findViewById(R.id.chamaNameEdit)).getText().toString();
                                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("CHAMA_NAME", chamaName);
                                editor.commit();

                                final ProgressDialog progressDialog = new ProgressDialog(ChamaOptionsActivity.this,
                                        R.style.AppTheme_Dark_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setCancelable(false);
                                progressDialog.setMessage("Registering Chama...");
                                progressDialog.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //open the welcome activity
                                        //start chama options activity
                                        progressDialog.dismiss();

                                        Toast.makeText(ChamaOptionsActivity.this, "Chama has been registered successfully",
                                                Toast.LENGTH_SHORT).show();

                                        Intent inviteMember=new Intent(getApplicationContext(),InviteMembers.class);
                                        inviteMember.putExtra("CHAMA_NAME", chamaName);
                                        startActivity(inviteMember);


                                    }
                                }, 2500);


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();


                break;
            case R.id.joinChamaButton:
                //join user to existing chama
                LayoutInflater joinInflater= LayoutInflater.from(this);
                final View joinView = joinInflater.inflate(R.layout.dialog_join_chama, null);


                new AlertDialog.Builder(this)
                        .setTitle("Enter your invitation code")
                        .setView(joinView)

                        .setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final String chamaName = ((EditText)
                                        joinView.findViewById(R.id.invitationCodeEdit)).getText().toString();

                                final ProgressDialog progressDialog = new ProgressDialog(ChamaOptionsActivity.this,
                                        R.style.AppTheme_Dark_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setCancelable(false);
                                progressDialog.setMessage("Verifying Code...");
                                progressDialog.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //open the welcome activity
                                        //start chama options activity
                                        progressDialog.dismiss();

                                        Toast.makeText(ChamaOptionsActivity.this, "Code successfully verified",
                                                Toast.LENGTH_SHORT).show();

                                        Intent chamaIntent=new Intent(getApplicationContext(),MainBoard.class);
                                        startActivity(chamaIntent);


                                    }
                                }, 2500);


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                break;
            case R.id.browseChamaButton:
                //browse through chamas
                Toast.makeText(ChamaOptionsActivity.this, "Browse Chama coming soon", Toast.LENGTH_SHORT).show();
                Intent browseChamaIntent=new Intent(getApplicationContext(),BrowseChama.class);
                startActivity(browseChamaIntent);

                break;
        }
    }

}
