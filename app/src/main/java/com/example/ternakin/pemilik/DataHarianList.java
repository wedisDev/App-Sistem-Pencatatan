package com.example.ternakin.pemilik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ternakin.DBContract;
import com.example.ternakin.R;
import com.example.ternakin.pemilik.masterTernak.ModelClassCatatan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataHarianList extends AppCompatActivity {

    LinearLayout btnAdd;
    String tampil_id_periode;
    TextView tanggal;
    String DATA_JSON_STRING, data_json_string, namaKandang, idKandang;
    ArrayList<ModelClassCatatan> arrayList = new ArrayList<>();
    AdapterCatatan adapterTernak;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_harian_list);

        btnAdd = findViewById(R.id.buttonAdd);
        tanggal = findViewById(R.id.tanggal);
        TextView judul = findViewById(R.id.judul);

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

        tanggal.setText(dateFormat.format(new Date()));

        tampil_id_periode = getIntent().getStringExtra("id_periode");
        namaKandang = getIntent().getStringExtra("nama_kandang");
        idKandang = getIntent().getStringExtra("id_kandang");

        judul.setText(namaKandang);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), InsertDataHarian.class);

                intent.putExtra("id_periode", tampil_id_periode);
                intent.putExtra("id_kandang", idKandang);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), InsertDataHarian.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerTernak);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapterTernak = new AdapterCatatan(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapterTernak);

        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readData();
            }
        }, 100);
    }

    private void readData() {
        if (checkNetworkConnection()) {
//            arrayList.clear();
            try {
                JSONObject object = new JSONObject(data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");
                String pakan_harian, berat_badan, tanggal_catatan, jumlah_mati, jumlah_kaling, kode_pakan, sisa_ayam, umur_ayam, jumlah_pakan;

                for (int i=0; i < serverResponse.length(); i++){
                    JSONObject jsonObject = serverResponse.getJSONObject(i);
                    pakan_harian = jsonObject.getString("pakan_harian");
                    berat_badan = jsonObject.getString("berat_badan");
                    tanggal_catatan = jsonObject.getString("tanggal_catatan");
                    jumlah_mati = jsonObject.getString("jumlah_mati");
                    jumlah_kaling = jsonObject.getString("jumlah_kaling");
                    kode_pakan = jsonObject.getString("kode_pakan");
                    sisa_ayam = jsonObject.getString("sisa_ayam");
                    umur_ayam = jsonObject.getString("umur_ayam");
                    jumlah_pakan = jsonObject.getString("jumlah_pakan");

                    arrayList.add(new ModelClassCatatan(pakan_harian, berat_badan, tanggal_catatan, jumlah_mati, jumlah_kaling, kode_pakan, sisa_ayam, umur_ayam, jumlah_pakan));
                }

                adapterTernak.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void getJSON() {
        new DataHarianList.BackgroundTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBContract.SERVER_GET_LIST_CATATAN(tampil_id_periode);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((DATA_JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(DATA_JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            data_json_string = result;
        }
    }
}