package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class AcilActivity extends AppCompatActivity {
    TextView nabizDegeri3;
    ImageView acilYardim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acil);

        nabizDegeri3 = findViewById(R.id.nabizDegeri3);
        acilYardim = findViewById(R.id.acilYardim);

        acilYardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:155"));
                startActivity(i);
            }
        });


    }

}