package com.example.ternakin.pemilik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ternakin.Login;
import com.example.ternakin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfile extends AppCompatActivity {

    private TextView nama;
    private FirebaseUser firebaseUser;
    private Button btnLogout, btnEdit;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    TextView tvPhone, tvEmail, tvUsername, tvPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fStore = FirebaseFirestore.getInstance();

        nama = findViewById(R.id.nama);
        btnLogout = findViewById(R.id.btnLogout);
        tvPhone = findViewById(R.id.phoneNumber);
        tvEmail = findViewById(R.id.email);
        tvUsername = findViewById(R.id.username);
        tvPassword = findViewById(R.id.password);
        btnEdit = findViewById(R.id.btnEdit);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditAkun.class));
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Log.d("TAG ", "onCreate: "+firebaseUser.getUid());
            checkUserAccessLevel(firebaseUser.getUid());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Log.d("TAG ", "onCreate: "+firebaseUser.getUid());
            checkUserAccessLevel(firebaseUser.getUid());
        }
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df =  fStore.collection("Users").document(uid);

        //extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("TAG", "onSuccess" + documentSnapshot.getData());
//
                nama.setText(documentSnapshot.getString("nama"));
                tvPhone.setText(documentSnapshot.getString("noTelp"));
                tvEmail.setText(documentSnapshot.getString("email"));
                tvUsername.setText(documentSnapshot.getString("nama"));
                tvPassword.setText("*****");

                if (documentSnapshot.getString("isKaryawan") != null){
                    btnEdit.setVisibility(View.GONE);
                }
//                FirebaseAuth.getInstance().signOut();
            }
        });
    }

}