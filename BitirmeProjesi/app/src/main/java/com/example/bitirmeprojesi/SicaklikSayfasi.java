package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SicaklikSayfasi extends AppCompatActivity {
    ImageView geriDonus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sicaklik_sayfasi);

        geriDonus = findViewById(R.id.geriDonus);

        geriDonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SicaklikSayfasi.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}