package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    /*BLUETOOTH KISMI*/
    Handler mHandler = new Handler(Looper.getMainLooper());
    private static final String TAG = "BlueTest5-MainActivity";
    private int mMaxChars = 50000;//Default
    private UUID mDeviceUUID;
    private BluetoothSocket mBTSocket;
    private OksijenSayfasi.ReadInput mReadThread = null;
    private boolean mIsUserInitiatedDisconnect = false;
    private boolean mIsBluetoothConnected = false;
    private BluetoothDevice mDevice;
    private ProgressDialog progressDialog;
    /*BLUETOOTH KISMI*/
    private ImageView nabizButon,nabizButonu2,oksijenButon,
            sicaklikButon,analizButon,navigasyonButonu,acilButon,
            dumanButonu,ayarlarButonu;
    private TextView mTxtNabiz;
    private TextView mTxtSicaklik;
    private TextView mTxtOksijen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*BLUETOOTH KISMI*/
        ActivityHelper.initialize(this);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        mDevice = b.getParcelable(BluetoothConnectedActivity.DEVICE_EXTRA);
        mDeviceUUID = UUID.fromString(b.getString(BluetoothConnectedActivity.DEVICE_UUID));
        mMaxChars = b.getInt(BluetoothConnectedActivity.BUFFER_SIZE);
        Log.d(TAG, "Hazır");
        mTxtNabiz = (TextView) findViewById(R.id.txtNabiz);
        mTxtSicaklik = (TextView) findViewById(R.id.txtSicaklik);
        mTxtOksijen = (TextView) findViewById(R.id.mTxtOksijen);
        /*BLUETOOTH KISMI*/

        nabizButon = findViewById(R.id.nabizButonu);
        oksijenButon = findViewById(R.id.oksijenButonu);
        sicaklikButon = findViewById(R.id.sicaklikButonu);
        analizButon = findViewById(R.id.analizButonu);
        nabizButonu2 = findViewById(R.id.nabizButonu2);
        navigasyonButonu = findViewById(R.id.navigasyonButonu);
        acilButon = findViewById(R.id.acilButon);
        dumanButonu = findViewById(R.id.dumanButonu);
        ayarlarButonu = findViewById(R.id.ayarlarButonu);

        nabizButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NabizSayfasi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        oksijenButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OksijenSayfasi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        

        sicaklikButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SicaklikSayfasi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        analizButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AnalizSayfasi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        nabizButonu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NabizSayfasi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        navigasyonButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GpsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        dumanButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GpsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        ayarlarButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AyarlarActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        acilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:155"));
                startActivity(i);
            }
        });

    }

    /*BLUETOOTH KISMI*/

    private class ReadInput implements Runnable {

        private boolean bStop = false;
        private Thread t;

        public ReadInput() {
            t = new Thread(this, "");
            t.start();
        }

        public boolean isRunning() {
            return t.isAlive();
        }

        @Override
        public void run() {
            InputStream inputStream;
            try {
                inputStream = mBTSocket.getInputStream();
                while (!bStop) {
                    byte[] buffer = new byte[256];
                    if (inputStream.available() > 0) {
                        inputStream.read(buffer);
                        int i = 0;

                        for (i = 0; i < buffer.length && buffer[i] != 0; i++) {
                        }
                        final String strInput = new String(buffer, 0, i); //Arduino'dan gelen veri buffer alınarak String'e dönüştürüldü.

                        mHandler.post(new Runnable() { //Sürekli veri çekilmesi için Handler oluşturuldu.
                            @Override
                            public void run() {
                                String[] parcalananVeri = strInput.split(",", -1); //Arduiodan gelen veriler virgül ile ayrılarak kendi alanlarına bölündü.

                                String[] arrayNabiz = parcalananVeri[0].split(":", -1); //İlk nabız verisi çekildi.
                                mTxtNabiz.setText(arrayNabiz[1].trim());

                                String[] arrayOksijen = parcalananVeri[1].split(":", -1); // İkinci oksijen verisi çekildi.
                                mTxtOksijen.setText(arrayNabiz[1].trim());

                                String[] arraySicaklik = parcalananVeri[2].split(":", -1); // Üçüncü sıcaklık verisi çekildi.
                                mTxtSicaklik.setText(arraySicaklik[1].trim());
                            }
                        });
                    }
                    Thread.sleep(1000); // Her bir saniyede bir işlem tekrarı uygulandı.
                }
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void stop() {
            bStop = true;
        }
    }


    class DisConnectBT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (mReadThread != null) {
                mReadThread.stop();
                while (mReadThread.isRunning())
                    ;
                mReadThread = null;

            }

            try {
                mBTSocket.close();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mIsBluetoothConnected = false;
            if (mIsUserInitiatedDisconnect) {
                finish();
            }
        }

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (mBTSocket != null && mIsBluetoothConnected) {
            new DisConnectBT().execute();
        }
        Log.d(TAG, "");
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mBTSocket == null || !mIsBluetoothConnected) {
            new ConnectBT().execute();
        }
        Log.d(TAG, "");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }


    class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean mConnectSuccessful = true;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, "Bekleyin", "Bağlanıyor");// http://stackoverflow.com/a/11130220/1287554
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (mBTSocket == null || !mIsBluetoothConnected) {
                    mBTSocket = mDevice.createInsecureRfcommSocketToServiceRecord(mDeviceUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    mBTSocket.connect();
                }
            } catch (IOException e) {
// Unable to connect to device
                e.printStackTrace();
                mConnectSuccessful = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!mConnectSuccessful) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                finish();
            } else {
                msg("Cıhaza bağlandı");
                mIsBluetoothConnected = true;

            }

            progressDialog.dismiss();
        }

        /*BLUETOOTH KISMI*/
    }

}
