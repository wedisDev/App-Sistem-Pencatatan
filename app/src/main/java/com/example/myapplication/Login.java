package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.android.volley.toolbox.Volley;
import com.example.myapplication.karyawan.MainActivityKaryawan;
import com.example.myapplication.pemilik.MainActivity;
import com.example.myapplication.pemilik.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private CheckBox showpass;
    private TextView gotoregistrasi;
    private Button login;
    ProgressDialog pDialog;
    Context context;

    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    static String phoneNumber;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        showpass = findViewById(R.id.showPass);
        gotoregistrasi = findViewById(R.id.goToRegistrasi);
        login = findViewById(R.id.login);

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

        gotoregistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registrasi.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), OtpScreen.class));


                checkField(email);
                checkField(password);
                Log.d("TAG","onClick" + email.getText().toString());

                if (valid){
                    fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df =  fStore.collection("Users").document(uid);

        //extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess" + documentSnapshot.getData());
                phoneNumber = documentSnapshot.getString("noTelp");

                assert phoneNumber != null;
                String newPhone = phoneNumber.substring(0,0)+"+62"+phoneNumber.substring(1);

                Log.d("TAG ", "onSuccess: "+newPhone);

                //identify user access level
                if (documentSnapshot.getString("isPemilik") != null){
                    // user is admin
//                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    Intent i = new Intent(getApplicationContext(), OtpScreen.class);
                    i.putExtra("phoneNumber", phoneNumber);
                    i.putExtra("access", "pemilik");
                    startActivity(i);
                    finish();
                }

                if (documentSnapshot.getString("isKaryawan") != null){
//                    Intent i = new Intent(getApplicationContext(), MainActivityKaryawan.class);
                    Intent i = new Intent(getApplicationContext(), OtpScreen.class);
                    i.putExtra("phoneNumber", phoneNumber);
                    i.putExtra("access", "karyawan");
                    startActivity(i);
                    finish();
                }
//                FirebaseAuth.getInstance().signOut();
            }
        });
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

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

}