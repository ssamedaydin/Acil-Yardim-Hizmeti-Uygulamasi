package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LandingPage4 extends AppCompatActivity {
    ImageView blueButon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page4);

        blueButon = findViewById(R.id.blueButon);

        blueButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage4.this,BluetoothConnectedActivity.class);
                startActivity(intent);
            }
        });
    }
}