package com.example.ternakin.pemilik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.example.ternakin.DBContract;
import com.example.ternakin.R;
import com.example.ternakin.VolleyConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    String tampil_id_periode, id_kandang;

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
    private FirebaseUser firebaseUser;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data_harian);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){

            userId = firebaseUser.getUid();
        }

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
        id_kandang = getIntent().getStringExtra("id_kandang");

        dates.setText(dateFormat.format(new Date()));

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataHari = hari.getText().toString();
                String dataUmurAyam = umurAyam.getText().toString();
                String dataBerat = beratBadan.getText().toString();
                String dataSisaAyam = sisaAyam.getText().toString();
                String dataKodePakan = kodePakan.getText().toString();
                String dataJumlahPakan = jumlahPakan.getText().toString();
                String dataJmlKaling = jmlKaling.getText().toString();
                String dataJmlMati = jmlMati.getText().toString();
//
//
                CreateDataToServer(dataHari, dataUmurAyam, dataBerat, dataSisaAyam,dataKodePakan,dataJumlahPakan,dataJmlKaling,dataJmlMati);
//                Toast.makeText(InsertDataHarian.this, "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void CreateDataToServer(String dataHari, String dataUmurAyam, String dataBerat, String dataSisaAyam, String dataKodePakan, String dataJumlahPakan, String dataJmlKaling, String dataJmlMati){
        if (checkNetworkConnection()){
            //progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_ADD_CATATAN,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");

                                finish();
                                Toast.makeText(getApplicationContext(), "Tambah catatan berhasil", Toast.LENGTH_SHORT).show();
//                                if (resp.equals("{\"server_response\":[{\"status\":\"1\"}]}")){
//                                } else {
//                                    Log.d("TAG " , "onResponse: errror "+resp);
//                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("TAG ", "onResponse: "+e.toString());
                            }
                        }
                    }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id_periode", tampil_id_periode);
                    params.put("id_kandang", id_kandang);
                    params.put("id_catatan", dateFormat.format(new Date()));
                    params.put("id_karyawan", userId);
                    params.put("kode_pakan", dataKodePakan);
                    params.put("jumlah_kaling", dataJmlKaling);
                    params.put("jumlah_mati", dataJmlMati);
                    params.put("tanggal_catatan_harian", dateFormat.format(new Date()));
                    params.put("berat_badan", dataBerat);
                    params.put("status_vaksin", "sudah");
                    params.put("pakan_harian", "ya");
                    params.put("id_panen", "1");
                    params.put("sisa_ayam", dataSisaAyam);
                    params.put("umur_ayam", dataUmurAyam);
                    params.put("jumlah_pakan", dataJumlahPakan);
                    return params;
                }
            };

            VolleyConnection.getInstance(InsertDataHarian.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    progressDialog.cancel();
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
}