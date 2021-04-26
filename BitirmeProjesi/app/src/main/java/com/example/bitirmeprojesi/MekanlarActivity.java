package com.example.bitirmeprojesi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MekanlarActivity extends AppCompatActivity {
    private RecyclerView rv;
    private String enlem;
    private String boylam;
    private ArrayList<Mekanlar> mekanlarArrayList;
    private MekanlarRVAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mekanlar);

        rv = findViewById(R.id.rv);
        enlem = getIntent().getStringExtra("enlem");
        boylam = getIntent().getStringExtra("boylam");

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mekanGetir();
    }
    //WEB SERVİS
    public void mekanGetir(){
        //Google nearbyserach ile alıdığımız api key yardımıyla burada konum etrafındaki belirlediğimiz type deki mekanları çekmektedir.
        String key = "AIzaSyCXYf-RMXeYte2IoRzRbwCv2G_ZPzkE-18";
        String aramaCapi = "1000";
        String konum = enlem+","+boylam;
        String type = "hospital|fire_station|police";
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+konum+"&types="+type+"&radius="+aramaCapi+"&key="+key;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mekanlarArrayList = new ArrayList<>();
                    //Konum etrafndaki mekanlar web serviste Json olarak çekilmektedir. Results içerisindeki name,vicinity ve geometry,location içerisindeki lat ve lng verileri burada çekilmektedir.
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray mekanlar = jsonObject.getJSONArray("results");

                    for(int i = 0;i<mekanlar.length();i++){
                        JSONObject m = mekanlar.getJSONObject(i);
                        String mekan_adi = m.getString("name");
                        String adres = m.getString("vicinity");

                        JSONObject geometry = m.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");

                        String enlem = location.getString("lat");
                        String boylam = location.getString("lng");

                        Mekanlar mekan = new Mekanlar(mekan_adi,Double.parseDouble(enlem),Double.parseDouble(boylam),adres);

                        mekanlarArrayList.add(mekan);

                    }

                    adapter = new MekanlarRVAdapter(MekanlarActivity.this,mekanlarArrayList);
                    rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(MekanlarActivity.this).add(stringRequest);

    }
}