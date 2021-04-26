package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

class AyarlarActivity extends AppCompatActivity {
    //GPS Kontrol
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID=3;


    //Bluetooth Kontrol
    BluetoothAdapter myBluetooth;



    private static final int GALLERY_REQUEST_CODE =123;
    private Switch switch1,switch2;
    private ImageView imageView44;
    ImageView imageView45,imageView46;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar_sayfasi);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        imageView44 = findViewById(R.id.imageView44);



        //GPS KISMI
        initGoogleAPIClient();
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    showLocationState();
                    checkPermissions();
                }else{
                }


            }
        });
        //GPS KISMI

        //BLUETOOTH KISMI
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    toggleBluetooth();
                }else{
                    toggleBluetooth();
                }
            }
        });
        //BLUETOOTH KISMI

        // PROFİL FOTOGRAFI GUNCELLEME KISMI
        imageView46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/^");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Bir fotograf secin"),GALLERY_REQUEST_CODE);
            }
        });
        // PROFİL FOTOGRAFI GUNCELLEME KISMI

        imageView44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeniIntent = new Intent(AyarlarActivity.this,MainActivity.class);
                startActivity(yeniIntent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            imageView45.setImageURI(imageData);
        }

        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(AyarlarActivity.this, "İzin Verildi", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(AyarlarActivity.this, "İzin Verilmedi", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void toggleBluetooth() {
        if(myBluetooth == null){
            Toast.makeText(getApplicationContext(),"Bluetooth cihazı yok",Toast.LENGTH_SHORT).show();
        }
        if(!myBluetooth.isEnabled()){
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
        }
        if(myBluetooth.isEnabled()){
            myBluetooth.disable();
        }
    }

    private void initGoogleAPIClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(AyarlarActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    private void showLocationState(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);// Konum isteğinin önceliğini yükseltmek için yapılan ayarlama
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000); // Konum güncellenmesi için yapılan 5 saniyelik zaman ayarı
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //Gps kapalı olsa dahi iletişim kutusunu her zaman gösterilmesini sağlar

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        //Konum ayarları etkin ise buraya
                        // istekler burda

                        Log.d("locationEnable","SUCCESS");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Konum ayarları etkin değil fakat dialog gösterip konum açılmasını sağlıyor isek buraya
                        // Dialog göster
                        try {
                            // startResolutionForResult(), çağırıp kontrol edilir
                            Log.d("locationEnable","RESOLUTION_REQUIRED");
                            status.startResolutionForResult(AyarlarActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Konum eğer açılamıyor ise buraya düşer
                        Log.d("locationEnable","SETTINGS_CHANGE_UNAVAILABLE");
                        break;
                }
            }
        });
    }

    /*  Konum izni için User Permission Metodu  */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AyarlarActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(AyarlarActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(AyarlarActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==ACCESS_FINE_LOCATION_INTENT_ID){
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(AyarlarActivity.this, "İzin verildi", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(AyarlarActivity.this, "İzin verilmedi", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void checkPermissions() {
        if ( Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(AyarlarActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showLocationState();
        } else
            showLocationState();
    }

}