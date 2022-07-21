package com.example.myapplication.pemilik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.DBContract;
import com.example.myapplication.R;
import com.example.myapplication.VolleyConnection;
import com.example.myapplication.pemilik.masterkaryawan.AddDataKaryawan;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InsertDataHarian extends AppCompatActivity {

    Button back, simpan;

    EditText hari,umurAyam,beratBadan,sisaAyam,kodePakan,jumlahPakan,jmlKaling,jmlMati;

    TextView dates;
    String tampil_id_periode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data_harian);

        back = findViewById(R.id.back);
        simpan = findViewById(R.id.simpan);

        hari = findViewById(R.id.hari);
        umurAyam = findViewById(R.id.umurAyam);
        beratBadan = findViewById(R.id.beratBadan);
        sisaAyam = findViewById(R.id.sisaAyam);
        kodePakan = findViewById(R.id.kodePakan);
        jumlahPakan = findViewById(R.id.jumlahPakan);
        jmlKaling = findViewById(R.id.jmlKaling);
        jmlMati = findViewById(R.id.jmlMati);
        dates = findViewById(R.id.date);

        tampil_id_periode = getIntent().getStringExtra("id_periode");

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

        dates.setText(dateFormat.format(new Date()));

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String sNama = nama.getText().toString();
//                String snoTelp = noTelp.getText().toString();
//                String sEmail = email.getText().toString();
//                String sPassword = password.getText().toString();
//
//
//                CreateDataToServer(sNama, snoTelp, sEmail, sPassword);
                Toast.makeText(InsertDataHarian.this, "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    public void CreateDataToServer(final String nama, final String noTelp, final String email, final String password){
//        if (checkNetworkConnection()){
//            //progressDialog.show();
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_ADD_CATATAN,
//                    new Response.Listener<String>(){
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                String resp = jsonObject.getString("server_response");
//
//                                if (resp.equals("{\"server_response\":[{\"status\":\"Registrasi Berhasil\"}]}")){
//                                    Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Toast.makeText(getApplicationContext(), "Tambah Karyawan Berhasil", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener(){
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }){
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("nama", nama);
//                    params.put("noTelp", noTelp);
//                    params.put("email", email);
//                    params.put("password", password);
//                    if (aktif.isChecked()){
//                        params.put("status", "1");
//                    }else {
//                        params.put("status", "2");
//                    }
//                    return params;
//                }
//            };
//
//            VolleyConnection.getInstance(AddDataKaryawan.this).addToRequestQue(stringRequest);
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    progressDialog.cancel();
//                }
//            }, 2000);
//        }else {
//            Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public boolean checkNetworkConnection(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }
//
//    public boolean checkField (EditText textField){
//        if (textField.getText().toString().isEmpty()){
//            textField.setError("Error");
//            valid = false;
//        }else {
//            valid = true;
//        }
//
//        return valid;
//    }
}