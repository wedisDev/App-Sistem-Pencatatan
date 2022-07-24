package com.example.myapplication;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registrasi extends AppCompatActivity {

    private TextView goToLogin;
    private EditText nama, noTelp, email, password;
    private Button registrasi;
    private CheckBox showpass;
    ProgressDialog progressDialog;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String idPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        nama = findViewById(R.id.nama);
        noTelp = findViewById(R.id.noTelp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        showpass = findViewById(R.id.showPass);
        registrasi = findViewById(R.id.registrasi);
        goToLogin = findViewById(R.id.goToLogin);
        progressDialog = new ProgressDialog(Registrasi.this);


        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registrasi.this, Login.class));
            }
        });

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

        registrasi.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(Registrasi.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();

                            String sNama = nama.getText().toString();
                            String snoTelp = noTelp.getText().toString();
                            String sEmail = email.getText().toString();
                            String sPassword = password.getText().toString();

                            userInfo.put("nama", sNama);
                            userInfo.put("noTelp", snoTelp);
                            userInfo.put("email", sEmail);

                            //specify if the user is admin
                            userInfo.put("isPemilik","1");
                            idPegawai = user.getUid();


                            df.set(userInfo);

                            CreateDataToServer(sNama, snoTelp, sEmail, sPassword);

                            String newPhone = snoTelp.substring(0,0)+"+62"+snoTelp.substring(1);

                            Intent i = new Intent(getApplicationContext(), OtpScreen.class);
                            i.putExtra("phoneNumber", newPhone);
                            startActivity(i);

                            FirebaseAuth.getInstance().signOut();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registrasi.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    public void CreateDataToServer(final String nama, final String noTelp, final String email, final String password){
        if (checkNetworkConnection()){
            //progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_REGISTER_URL,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
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
                    params.put("id_pegawai", idPegawai);
                    params.put("nama", nama);
                    params.put("noTelp", noTelp);
                    params.put("email", email);
                    params.put("password", password);

                    return params;
                }
            };

            VolleyConnection.getInstance(Registrasi.this).addToRequestQue(stringRequest);

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