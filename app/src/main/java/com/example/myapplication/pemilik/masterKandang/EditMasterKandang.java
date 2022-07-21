package com.example.myapplication.pemilik.masterKandang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditMasterKandang extends AppCompatActivity {

    TextView id_kandang;
    EditText nama_kandang, kapasitas;
    Button btnEdit, btnHapus;
    ImageView btnKembali;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_master_kandang);

        nama_kandang = findViewById(R.id.nama_kandang);
        kapasitas = findViewById(R.id.kapasitas);
        id_kandang = findViewById(R.id.id_kandang);
        btnKembali = findViewById(R.id.btnKembali);
        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);

        getIncomingExtra();

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditMasterKandang.this, MasterKandang.class));
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid_kandang = id_kandang.getText().toString();
                String snama_kandang = nama_kandang.getText().toString();
                String skapasitas = kapasitas.getText().toString();

                CreateDataToServer(sid_kandang, snama_kandang, skapasitas);

                startActivity(new Intent(getApplicationContext(), MasterKandang.class));
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid_kandang = id_kandang.getText().toString();

                HapusData(sid_kandang);

                startActivity(new Intent(getApplicationContext(), MasterKandang.class));
            }
        });

    }

    private void HapusData(String id_kandang) {
        if (checkNetworkConnection()){
            //progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_DELETE_MASTER_KANDANG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("{\"server_response\":[{\"status\":\"Delete Kandang Berhasil\"}]}")){
                                    Toast.makeText(getApplicationContext(), "Hapus Kandang Berhasil", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Hapus Kandang Berhasil", Toast.LENGTH_SHORT).show();
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
                    params.put("id_kandang", id_kandang);

                    return params;
                }
            };
            VolleyConnection.getInstance(EditMasterKandang.this).addToRequestQue(stringRequest);

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

    private void CreateDataToServer(String id_kandang, String nama_kandang, String kapasitas) {
        if (checkNetworkConnection()){
            //progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_EDIT_MASTER_KANDANG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("{\"server_response\":[{\"status\":\"Edit Kandang Berhasil\"}]}")){
                                    Toast.makeText(getApplicationContext(), "Edit Kandang Berhasil", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Edit Kandang Berhasil", Toast.LENGTH_SHORT).show();
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
                    params.put("id_kandang", id_kandang);
                    params.put("nama_kandang", nama_kandang);
                    params.put("kapasitas", kapasitas);

                    return params;
                }
            };
            VolleyConnection.getInstance(EditMasterKandang.this).addToRequestQue(stringRequest);

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

    private void getIncomingExtra() {
        if (getIntent().hasExtra("nama_kandang") && getIntent().hasExtra("kapasitas") && getIntent().hasExtra("id_kandang")){
            String tampil_nama_kandang = getIntent().getStringExtra("nama_kandang");
            String tampil_kapasitas = getIntent().getStringExtra("kapasitas");
            String tampil_id_kandang = getIntent().getStringExtra("id_kandang");

            setDataActivity(tampil_nama_kandang, tampil_kapasitas, tampil_id_kandang);
        }
    }

    private void setDataActivity(String tampil_nama_kandang, String tampil_kapasitas, String tampil_id_kandang) {

        nama_kandang.setText(tampil_nama_kandang);
        kapasitas.setText(tampil_kapasitas);
        id_kandang.setText(tampil_id_kandang);
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