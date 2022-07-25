package com.example.ternakin.pemilik.masterkaryawan;

import androidx.appcompat.app.AppCompatActivity;
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

import com.example.ternakin.DBContract;
import com.example.ternakin.R;
import com.example.ternakin.pemilik.MainActivity;

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

public class MasterKaryawan extends AppCompatActivity {

    private TextView subJudul, judul;
    private Button btnAdd;
    private ImageView btnBack;
    RecyclerView recyclerKaryawan;
    RecyclerView.LayoutManager layoutManager;
    Adapter_MasterKaryawan adapterKaryawan;
    ArrayList<ModelClassMasterKaryawan> arrayList = new ArrayList<>();
    String DATA_JSON_STRING, data_json_string;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_karyawan);

        subJudul = findViewById(R.id.subjudul);
        judul = findViewById(R.id.judul);

        //add data karyawan
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddDataKaryawan.class));
                finish();
            }
        });

        // btn back
        btnBack = findViewById(R.id. btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });

        //recycker kandang
        recyclerKaryawan = findViewById(R.id.recyclerKaryawan);
        layoutManager = new LinearLayoutManager(this);
        recyclerKaryawan.setLayoutManager(layoutManager);
        recyclerKaryawan.setHasFixedSize(true);
        adapterKaryawan = new Adapter_MasterKaryawan(getApplicationContext(), arrayList);
        recyclerKaryawan.setAdapter(adapterKaryawan);
        progressDialog = new ProgressDialog(MasterKaryawan.this);

        //loadDataKandang();
        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readData();
            }
        }, 500);

    }


    private void readData() {
        if (checkNetworkConnection()) {
            arrayList.clear();
            try {
                JSONObject object = new JSONObject(data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");
                String jabatan, nama, notelp, email, status;

                for (int i=0; i < serverResponse.length(); i++){
                    JSONObject jsonObject = serverResponse.getJSONObject(i);
                    jabatan = jsonObject.getString("jabatan");
                    nama = jsonObject.getString("nama");
                    notelp = jsonObject.getString("notelp");
                    email = jsonObject.getString("email");
                    status = jsonObject.getString("status");

                    arrayList.add(new ModelClassMasterKaryawan(jabatan, nama, notelp, email, status));
                }

                adapterKaryawan.notifyDataSetChanged();
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
        new MasterKaryawan.BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBContract.SERVER_TAMPIL_MASTER_KARYAWAN;
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