package com.example.myapplication.pemilik.masterpakan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DBContract;
import com.example.myapplication.R;
import com.example.myapplication.VolleyConnection;
import com.example.myapplication.pemilik.masterkaryawan.AddDataKaryawan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdatePakan extends AppCompatActivity {

    private EditText tanggal_masuk, pakan_masuk, tvDateResult;
    private Button btnSimpan, btnKembali;
    private Spinner kode_pakan;
    ArrayList<String> kodeList = new ArrayList<>();
    ArrayAdapter<String> kodeAdapter;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    boolean valid = true;
    private SimpleDateFormat dateFormatter;
    Locale localeID = new Locale("in", "ID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pakan);

        pakan_masuk = findViewById(R.id.pakan_masuk);
        btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MasterPakan.class));
                finish();
            }
        });

        //menampilkan pop up tanggal
        tanggal_masuk = findViewById(R.id.tanggal_masuk);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", localeID);
        tvDateResult = findViewById(R.id.tanggal_masuk);
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        kode_pakan = findViewById(R.id.kode_pakan);

        spinnerLoad();

        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String skode_pakan = kode_pakan.getSelectedItem().toString();
                String stanggal_masuk = tanggal_masuk.getText().toString();
                String spakan_masuk = pakan_masuk.getText().toString();

                CreateDataToServer(skode_pakan, stanggal_masuk, spakan_masuk);

                updateStok(skode_pakan, spakan_masuk);

                startActivity(new Intent(getApplicationContext(), MasterPakan.class));
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

    private void spinnerLoad() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DBContract.SPINNER_PAKAN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("server_response");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama_pakan = jsonObject.optString("nama_pakan");
                                kodeList.add(nama_pakan);
                                kodeAdapter = new ArrayAdapter<>(UpdatePakan.this,
                                        android.R.layout.simple_spinner_item, kodeList);
                                kodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                kode_pakan.setAdapter(kodeAdapter);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void CreateDataToServer(final String kode_pakan, final String tanggal_masuk, final String pakan_masuk) {
        if (checkNetworkConnection()){
            //progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_INSERT_PAKAN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("{\"server_response\":[{\"status\":\"Update Berhasil\"}]}")){
                                    Toast.makeText(getApplicationContext(), "Update Berhasil", Toast.LENGTH_SHORT).show();
                                }else {
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
                    params.put("kode_pakan", kode_pakan);
                    params.put("tanggal_masuk", tanggal_masuk);
                    params.put("pakan_masuk", pakan_masuk);

                    return params;
                }
            };
            VolleyConnection.getInstance(UpdatePakan.this).addToRequestQue(stringRequest);

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

    private void updateStok(String kode_pakan,  String pakan_masuk) {
        if (checkNetworkConnection()){
            //progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_UPDATE_STOCK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("{\"message\":\"Berhasil diperbarui\"}{\"server_response\":{\"message\":\"Berhasil diperbarui\"}}")){
//                                    Toast.makeText(getApplicationContext(), "Update Berhasil", Toast.LENGTH_SHORT).show();
                                }else {
                                    //Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
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
                    params.put("kode_pakan", kode_pakan);
                    params.put("pakan_masuk", pakan_masuk);

                    return params;
                }
            };
            VolleyConnection.getInstance(UpdatePakan.this).addToRequestQue(stringRequest);

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


}