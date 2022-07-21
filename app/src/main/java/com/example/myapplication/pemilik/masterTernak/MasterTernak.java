package com.example.myapplication.pemilik.masterTernak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DBContract;
import com.example.myapplication.R;
import com.example.myapplication.pemilik.MainActivity;
import com.example.myapplication.pemilik.StorageFragmentPemilik;
import com.example.myapplication.pemilik.masterKandang.AdapterKandang;
import com.example.myapplication.pemilik.masterKandang.MasterKandang;
import com.example.myapplication.pemilik.masterKandang.ModelKandang;
import com.example.myapplication.pemilik.masterkaryawan.AddDataKaryawan;
import com.example.myapplication.pemilik.masterkaryawan.ModelClassMasterKaryawan;

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
import java.util.ArrayList;

public class MasterTernak extends AppCompatActivity {

    private TextView subJudul, judul;
    private Button btnTernak;
    private ImageView btnBack;
    RecyclerView recyclerTernak;
    RecyclerView.LayoutManager layoutManager;
    Adapter_MasterTernak adapterTernak;
    ArrayList<ModelClassMasterTernak> arrayList = new ArrayList<>();
    String DATA_JSON_STRING, data_json_string;
    ProgressDialog progressDialog;
    int countData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_ternak);

        subJudul = findViewById(R.id.subjudul);
        judul = findViewById(R.id.judul);
        btnTernak = findViewById(R.id.btnTernak);
        btnBack = findViewById(R.id.btnBack);

        //Insert Data Ternak
        btnTernak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InsertMasterTernak.class));
                finish();
            }
        });

        // btn back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });

        //recycker kandang
        recyclerTernak = findViewById(R.id.recyclerTernak);
        layoutManager = new LinearLayoutManager(this);
        recyclerTernak.setLayoutManager(layoutManager);
        recyclerTernak.setHasFixedSize(true);
        adapterTernak = new Adapter_MasterTernak(MasterTernak.this, arrayList);
        recyclerTernak.setAdapter(adapterTernak);
        progressDialog = new ProgressDialog(MasterTernak.this);

        //loadDataKandang();
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
                String tanggal_datang, jumlah_bibit, id_periode;

                for (int i=0; i < serverResponse.length(); i++){
                    JSONObject jsonObject = serverResponse.getJSONObject(i);
                    tanggal_datang = jsonObject.getString("tanggal_datang");
                    jumlah_bibit = jsonObject.getString("jumlah_bibit");
                    id_periode = jsonObject.getString("id_periode");

                    arrayList.add(new ModelClassMasterTernak(tanggal_datang, jumlah_bibit, id_periode));
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
        new MasterTernak.BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBContract.SERVER_TAMPIL_MASTER_TERNAK;
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