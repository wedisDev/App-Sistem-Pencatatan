package com.example.ternakin.pemilik.masterTernak;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ternakin.DBContract;
import com.example.ternakin.R;
import com.example.ternakin.VolleyConnection;
import com.example.ternakin.pemilik.masterKandang.MasterKandang;
import com.example.ternakin.pemilik.masterKandang.ModelKandang;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InsertMasterTernak extends AppCompatActivity {

    private EditText tanggal_datang, jumlah_bibit, tvDateResult;
    private Button btnInsert, btnKembali;
    private Spinner kandang;
    private SimpleDateFormat dateFormatter;
    ProgressDialog progressDialog;
    boolean valid = true;
    Locale localeID = new Locale("in", "ID");
    String idKandang;

    ArrayList<String> namaKandang = new ArrayList<>();
    ArrayList<String> listIdKandang = new ArrayList<>();
    String DATA_JSON_STRING, data_json_string;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_master_ternak);

        tanggal_datang = findViewById(R.id.tanggal_datang);
        jumlah_bibit = findViewById(R.id.jumlah_bibit);
        btnInsert = findViewById(R.id.btnInsert);
        btnKembali = findViewById(R.id.btnKembali);
        kandang = findViewById(R.id.kandang);

        //btn kembali
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MasterTernak.class));
                finish();
            }
        });

        //menampilkan pop up kalende
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", localeID);
        tvDateResult = findViewById(R.id.tanggal_datang);
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stanggal_datang = tanggal_datang.getText().toString();
                String sjumlah_bibit = jumlah_bibit.getText().toString();

                CreateDataToServer(stanggal_datang, sjumlah_bibit, idKandang);
                


                startActivity(new Intent(getApplicationContext(), MasterTernak.class));
            }
        });

        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readData();
            }
        }, 500);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namaKandang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kandang.setAdapter(adapter);
        kandang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idKandang = listIdKandang.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void CreateDataToServer(final String tanggal_datang, final String jumlah_bibit, final String idKandang) {
        if (checkNetworkConnection()){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_INSERT_MASTER_TERNAK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("{\"server_response\":[{\"status\":\"Tambah Periode Berhasil\"}]}")){
                                    Toast.makeText(getApplicationContext(), "Insert Berhasil", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Update Stok Berhasil", Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("tanggal_datang", tanggal_datang);
                    params.put("jumlah_bibit", jumlah_bibit);
                    params.put("id_kandang", idKandang);

                    return params;
                }
            };
            VolleyConnection.getInstance(InsertMasterTernak.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //progressDialog.cancel();
                }
            }, 2000);
        }else {
            Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public boolean checkField (EditText textField){
        if (textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }

    private void readData() {
        if (checkNetworkConnection()) {
//            arrayList.clear();
            try {
                JSONObject object = new JSONObject(data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");
                String nama_kandang, kapasitas, id_kandang;

                for (int i=0; i < serverResponse.length(); i++){
                    JSONObject jsonObject = serverResponse.getJSONObject(i);
                    nama_kandang = jsonObject.getString("nama_kandang");
                    kapasitas = jsonObject.getString("kapasitas");
                    id_kandang = jsonObject.getString("id_kandang");

                    namaKandang.add(nama_kandang);
                    listIdKandang.add(id_kandang);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getJSON() {
        new InsertMasterTernak.BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBContract.SERVER_TAMPIL_MASTER_KANDANG;
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