package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnalizSayfasi extends AppCompatActivity {
    TextView nabizDunya,nabizKullanici,nabizDegeri10,oksijenDunya,
            oksijenKullanici,oksijenDegeri10,sicaklikDunya,sicaklikKullanici,sicaklikDegeri10;
    ImageView geriDonus;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analiz_sayfasi);

        nabizDunya = findViewById(R.id.nabizDunya);
        nabizKullanici = findViewById(R.id.nabizKullanici);
        nabizDegeri10 = findViewById(R.id.nabizDegeri10);
        oksijenDunya = findViewById(R.id.oksijenDunya);
        oksijenKullanici = findViewById(R.id.oksijenKullanici);
        oksijenDegeri10 = findViewById(R.id.oksijenDegeri10);
        sicaklikDunya = findViewById(R.id.sicaklikDunya);
        sicaklikKullanici = findViewById(R.id.sicaklikKullanici);
        sicaklikDegeri10 = findViewById(R.id.sicaklikDegeri10);

        geriDonus = findViewById(R.id.geriDonus);

        geriDonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalizSayfasi.this,MainActivity.class);
                startActivity(intent);

            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("genelverianalizi"); // Firebase'deki realtime database ismi eşleştirildi.
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { // Database veri alanlarından veriler çekildi.
                String nabizDunya1 = snapshot.child("nabizDunya").getValue().toString();
                String nabizKullanici1 = snapshot.child("nabizKullanici").getValue().toString();
                String oksijenDunya1 = snapshot.child("oksijenDunya").getValue().toString();
                String oksijenKullanici1 = snapshot.child("oksijenKullanici").getValue().toString();
                String sicaklikDunya1 = snapshot.child("sicaklikDunya").getValue().toString();
                String sicaklikKullanici1 = snapshot.child("sicaklikKullanici").getValue().toString();

                nabizDunya.setText(nabizDunya1); // Çekilen veriler analiz sayfasında görüntülendi.
                nabizKullanici.setText(nabizKullanici1);
                oksijenDunya.setText(oksijenDunya1);
                oksijenKullanici.setText(oksijenKullanici1);
                sicaklikDunya.setText(sicaklikDunya1);
                sicaklikKullanici.setText(sicaklikKullanici1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}