package com.dyfi.jeevani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseReference childref;
    String bldgroup;
    String[] bloodgroup={"O+ve","O-ve","A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve"};
    private int call_permission_code=1;
    ImageButton call,ser,back;
    TextView yes,no1,no2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bldgroup = bloodgroup[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        childref = database.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodgroup);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        call = findViewById(R.id.caller);
        ser = findViewById(R.id.searcbt);
        back= findViewById(R.id.back);
        yes = findViewById(R.id.textyes);
        no1 = findViewById(R.id.textno1);
        no2 = findViewById(R.id.textno2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });


        ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(bldgroup)) {
                            yes.setVisibility(View.VISIBLE);
                            call.setVisibility(View.VISIBLE);
                            no1.setVisibility(View.GONE);
                            no2.setVisibility(View.GONE);
                        }
                        else{

                            yes.setVisibility(View.GONE);
                            call.setVisibility(View.GONE);
                            no1.setVisibility(View.VISIBLE);
                            no2.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = "9048245101";
                        String S = "tel:" + number;
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(S));
                        if (ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            requestcallpermission();
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            return;
                        }
                        startActivity(intent);
                    }
                });

            }

            private void requestcallpermission() {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SearchActivity.this, Manifest.permission.CALL_PHONE)) {
                    new AlertDialog.Builder(SearchActivity.this).setTitle("Permission Needed").setMessage("Call Permission is needed to call")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.CALL_PHONE}, call_permission_code);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                } else {
                    ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.CALL_PHONE}, call_permission_code);
                }
            }


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        home();
    }

    private void home() {

        finish();
        Intent homeintent = new Intent(this, HomeActivity.class);
        startActivity(homeintent);
    }
}
