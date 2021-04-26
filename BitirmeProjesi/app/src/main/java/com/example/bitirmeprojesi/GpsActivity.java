package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GpsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    //GPS Kontrol
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID=3;

    private String konumSaglayici = "gps";
    private LocationManager locationManager;
    private int izinKontrol;
    //GPS Kontrol
    private ImageView imageView49,imageView50;
    private TextView textView12;
    private Button buttonAcil;
    private double enlem;
    private double boylam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        imageView49 = findViewById(R.id.imageView49);
        imageView50 = findViewById(R.id.imageView50);
        buttonAcil = findViewById(R.id.buttonAcil);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        izinKontrol = ContextCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(izinKontrol != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(GpsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else{
            Location konum = locationManager.getLastKnownLocation(konumSaglayici);
            if(konum != null){
                onLocationChanged(konum);
            }else{
                Toast.makeText(getApplicationContext(),"Konum Aktif Degil",Toast.LENGTH_SHORT).show();
            }
        }

        //GPS Kısmı


        imageView50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng Eskisehir = new LatLng(enlem,boylam);
                mMap.addMarker(new MarkerOptions().position(Eskisehir).title("Eskisehir'desin."));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Eskisehir,16f));

            }
        });
        imageView49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeniInten = new Intent(GpsActivity.this,MainActivity.class);
                startActivity(yeniInten);
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buttonAcil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izinKontrol = ContextCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if(izinKontrol != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(GpsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
                }else{
                    Location konum = locationManager.getLastKnownLocation(konumSaglayici);

                    if(konum != null){
                        onLocationChanged(konum);
                        Intent intent = new Intent(GpsActivity.this,MekanlarActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"hata",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Eskisehir = new LatLng(enlem,boylam);
        mMap.addMarker(new MarkerOptions().position(Eskisehir).title("Eskisehir'desin."));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Eskisehir,16f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100){
            izinKontrol = ContextCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"İZİN VERİLDİ",Toast.LENGTH_SHORT).show();
                Location konum = locationManager.getLastKnownLocation(konumSaglayici);
                if(konum != null){
                    onLocationChanged(konum);
                }else{
                    Toast.makeText(getApplicationContext(),"Konum Aktif Degil",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"İZİN VERİLMEDİ",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        enlem = location.getLatitude();
        boylam = location.getLongitude();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}