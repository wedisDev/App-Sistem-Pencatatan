package com.example.myapplication.pemilik.masterKandang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.DBContract;
import com.example.myapplication.R;
import com.example.myapplication.VolleyConnection;
import com.example.myapplication.pemilik.masterpakan.MasterPakan;
import com.example.myapplication.pemilik.masterpakan.UpdatePakan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddMasterKandang extends AppCompatActivity {

    private EditText nama_kandang, kapasitas;
    private Button btnSimpan, btnKembali;
    ProgressDialog progressDialog;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_master_kandang);

        nama_kandang = findViewById(R.id.nama_kandang);
        kapasitas = findViewById(R.id.kapasitas);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnKembali = findViewById(R.id.btnKembali);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MasterKandang.class));
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snama_kandang = nama_kandang.getText().toString();
                String skapasitas = kapasitas.getText().toString();

                CreateDataToServer(snama_kandang, skapasitas);

                startActivity(new Intent(getApplicationContext(), MasterKandang.class));
            }
        });

    }



    private void CreateDataToServer(final String nama_kandang, final String kapasitas) {
        if (checkNetworkConnection()){
            //progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_ADD_MASTER_KANDANG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("{\"server_response\":[{\"status\":\"Tambah Kandang Berhasil\"}]}")){
                                    Toast.makeText(getApplicationContext(), "Tambah Kandang Berhasil", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Tambah Kandang Berhasil", Toast.LENGTH_SHORT).show();
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
                    params.put("nama_kandang", nama_kandang);
                    params.put("kapasitas", kapasitas);

                    return params;
                }
            };
            VolleyConnection.getInstance(AddMasterKandang.this).addToRequestQue(stringRequest);

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