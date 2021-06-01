package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class NabizSayfasi extends AppCompatActivity {
    ImageView geriDonus;
    TextView mTxtNabiz;

    /*BLUETOOTH KISMI*/
    Handler mHandlerr = new Handler(Looper.getMainLooper());
    private static final String TAG = "BlueTest5-MainActivity";
    private int mMaxChars = 50000;//Default
    private UUID mDeviceUUID;
    private BluetoothSocket mBTSocket;
    private NabizSayfasi.ReadInput mReadThread = null;
    private boolean mIsUserInitiatedDisconnect = false;
    private boolean mIsBluetoothConnected = false;
    private BluetoothDevice mDevice;
    private ProgressDialog progressDialog;
    /*BLUETOOTH KISMI*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nabiz_sayfasi);

        geriDonus = findViewById(R.id.geriDonus);
        mTxtNabiz = findViewById(R.id.mTxtNabiz);

        /*BLUETOOTH KISMI*/
        ActivityHelper.initialize(this);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        mDevice = b.getParcelable(BluetoothConnectedActivity.DEVICE_EXTRA);
        mDeviceUUID = UUID.fromString(b.getString(BluetoothConnectedActivity.DEVICE_UUID));
        mMaxChars = b.getInt(BluetoothConnectedActivity.BUFFER_SIZE);
        Log.d(TAG, "Hazır");
        mTxtNabiz = (TextView) findViewById(R.id.txtNabiz);
        /*BLUETOOTH KISMI*/


        geriDonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NabizSayfasi.this, MainActivity.class);
                startActivity(intent);

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

                        mHandlerr.post(new Runnable() { //Sürekli veri çekilmesi için Handler oluşturuldu.
                            @Override
                            public void run() {
                                String[] parcalananVeri = strInput.split(",", -1); //Arduiodan gelen veriler virgül ile ayrılarak kendi alanlarına bölündü.

                                String[] arrayNabiz = parcalananVeri[0].split(":", -1); //İlk nabız verisi çekildi.
                                mTxtNabiz.setText(arrayNabiz[1].trim());

                                if(mTxtNabiz.equals("120")){
                                    Intent intent = new Intent(getApplicationContext(),AcilActivity.class);
                                    startActivity(intent);
                                }

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


    private class DisConnectBT extends AsyncTask<Void, Void, Void> {

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


    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean mConnectSuccessful = true;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(NabizSayfasi.this, "Bekleyin", "Baglanıyor");
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
                msg("Baglanan Cihaz");
                mIsBluetoothConnected = true;
                mReadThread = new ReadInput();
            }

            progressDialog.dismiss();
        }

    }
}
