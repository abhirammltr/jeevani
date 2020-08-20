package com.dyfi.jeevani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnHoverListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name,phone,place,age;
    ImageButton back,submit;
    CheckBox agree;
    DatabaseReference childref;
    String bldgroup;
    String phn;
    String[] bloodgroup={"O+ve","O-ve","A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve"};
    //boolean flag = false;


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bldgroup = bloodgroup[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class FireBaseDataMap {

        private HashMap<String, String> dataMap = new HashMap<String, String>();

        public HashMap<String, String> firebaseMap(){
            return dataMap;
        }

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bloodgroup);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        childref = database.getReference();
        name=findViewById(R.id.nameedit);
        phone=findViewById(R.id.phoneedit);
        age=findViewById(R.id.ageedit);
        place=findViewById(R.id.placeedit);
        submit=findViewById(R.id.submit);
        back=findViewById(R.id.back);
        agree=findViewById(R.id.checkBox);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });


        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

               /* childref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String ph = String.valueOf(phone.getText());

                        if(dataSnapshot.child().h){
                            flag = true;
                        }
                        else{
                            flag = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                */

                if(name.length()<= 2){
                    Toast.makeText(RegActivity.this, "ദയവായി പേര് പരിശോധിക്കുക", Toast.LENGTH_SHORT).show();
                }
                /*else if(flag){
                    Toast.makeText(RegActivity.this, "Phone Number already registered", Toast.LENGTH_SHORT).show();
                }*/
                else if(place.length()<= 2){
                    Toast.makeText(RegActivity.this, "ദയവായി സ്ഥലപേര് പരിശോധിക്കുക", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.valueOf(String.valueOf(age.getText()))<18 || Integer.valueOf(String.valueOf(age.getText()))>50){
                    Toast.makeText(RegActivity.this, "ദയവായി വയസ്സ് പരിശോധിക്കുക", Toast.LENGTH_SHORT).show();
                }
                else if(phone.length()!=10){
                    Toast.makeText(RegActivity.this, "തെറ്റായ ഫോൺ നമ്പർ", Toast.LENGTH_SHORT).show();
                }
                else{
                if(agree.isChecked()){
                    String nm =String.valueOf(name.getText());
                    phn =String.valueOf(phone.getText());
                    String plc =String.valueOf(place.getText());
                    String ag =String.valueOf(age.getText());

                    FireBaseDataMap obj = new FireBaseDataMap();
                    HashMap<String, String> dataMap = obj.firebaseMap();


                    dataMap.put("Name", nm);
                    dataMap.put("Phone", phn);
                    dataMap.put("Place", plc);
                    dataMap.put("Age", ag);
                    dataMap.put("Group", bldgroup);

                    childref.child(bldgroup).push().setValue(dataMap);
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
                    builder.setMessage("ജീവനിയിൽ രജിസ്റ്റർ ചെയ്തതിന് നന്ദി !!!").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            home();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else{
                    Toast.makeText(RegActivity.this, "please tick the checkbox", Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        home();
        finish();
        super.onBackPressed();
    }

    private void home() {
        finish();
        Intent homeintent = new Intent(this, HomeActivity.class);
        startActivity(homeintent);
    }


}
