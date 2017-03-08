package com.afyaplan.afya_plan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
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


                                Toast.makeText(getApplicationContext(),"Yolo",Toast.LENGTH_LONG).show();
                               Intent inviteMember=new Intent(getApplicationContext(),InviteMembers.class);
                                startActivity(inviteMember);


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
                Toast.makeText(ChamaOptionsActivity.this, "Join Chama coming soon", Toast.LENGTH_SHORT).show();
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
