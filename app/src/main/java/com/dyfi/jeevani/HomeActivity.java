package com.dyfi.jeevani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Button b1,b2;
    long backPressedTime;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        b1=findViewById(R.id.regis);
        b2=findViewById(R.id.searchbt);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationact();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchact();
            }
        });
    }
    public void registrationact(){
        Intent regintent = new Intent(this, RegActivity.class);
        startActivity(regintent);
        finish();
    }
    public void searchact(){
        Intent searchintent = new Intent(this, SearchActivity.class);
        startActivity(searchintent);
        finish();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
}
