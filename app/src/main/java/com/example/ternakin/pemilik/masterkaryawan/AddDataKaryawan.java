package com.example.ternakin.pemilik.masterkaryawan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ternakin.DBContract;
import com.example.ternakin.R;
import com.example.ternakin.VolleyConnection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddDataKaryawan extends AppCompatActivity {

    private EditText nama, noTelp, email, password;
    private RadioGroup status;
    private RadioButton statusTerpilih;
    private RadioButton aktif;
    private RadioButton belumAktif;
    private Button btnSimpan, btnKembali;
    private CheckBox showpass;
    ProgressDialog progressDialog;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_karyawan);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        nama = findViewById(R.id.nama);
        noTelp = findViewById(R.id.notelp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        status = findViewById(R.id.status);
        aktif = findViewById(R.id.aktif);
        belumAktif = findViewById(R.id.belumAktif);
        showpass = findViewById(R.id.showPass);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnKembali = findViewById(R.id.btnKembali);
        progressDialog = new ProgressDialog(AddDataKaryawan.this);

        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showpass.isChecked()){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MasterKaryawan.class));
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(nama);
                checkField(email);
                checkField(password);
                checkField(noTelp);

                if (valid){
                    // start the user registrasi proses
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            //Toast.makeText(Registrasi.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("nama", nama.getText().toString());
                            userInfo.put("noTelp", noTelp.getText().toString());
                            userInfo.put("email", email.getText().toString());

                            //specify if the user is admin
                            userInfo.put("isKaryawan","1");

                            df.set(userInfo);

                            startActivity(new Intent(getApplicationContext(), MasterKaryawan.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to Create Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                String sNama = nama.getText().toString();
                String snoTelp = noTelp.getText().toString();
                String sEmail = email.getText().toString();
                String sPassword = password.getText().toString();


                CreateDataToServer(sNama, snoTelp, sEmail, sPassword);
            }
        });

    }


    public void CreateDataToServer(final String nama, final String noTelp, final String email, final String password){
        if (checkNetworkConnection()){
            //progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_ADD_MASTER_KARYAWAN,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");

                                if (resp.equals("{\"server_response\":[{\"status\":\"Registrasi Berhasil\"}]}")){
                                    Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Tambah Karyawan Berhasil", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                    params.put("nama", nama);
                    params.put("noTelp", noTelp);
                    params.put("email", email);
                    params.put("password", password);
                    if (aktif.isChecked()){
                        params.put("status", "1");
                    }else {
                        params.put("status", "2");
                    }
                    return params;
                }
            };

            VolleyConnection.getInstance(AddDataKaryawan.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
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
