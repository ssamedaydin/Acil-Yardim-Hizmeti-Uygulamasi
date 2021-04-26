package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NabizSayfasi extends AppCompatActivity {
    ImageView geriDonus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nabiz_sayfasi);

        geriDonus = findViewById(R.id.geriDonus);

        geriDonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NabizSayfasi.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}