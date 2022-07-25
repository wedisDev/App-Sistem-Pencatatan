package com.example.ternakin.pemilik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ternakin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditAkun extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    EditText nama, notelp;
    Button editAkun;
    FirebaseFirestore fStore;
    String uid, email, pemilik, karyawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);

        fStore = FirebaseFirestore.getInstance();

        nama = findViewById(R.id.nama);
        notelp = findViewById(R.id.noTelp);
        editAkun = findViewById(R.id.edit_akun);

        editAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference df = fStore.collection("Users").document(uid);
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("nama", nama.getText().toString());
                userInfo.put("noTelp", notelp.getText().toString());

                //specify if the user is admin
                userInfo.put("email", email);
                if (karyawan != null){
                    userInfo.put("isKaryawan", "1");
                }
                if (pemilik != null){
                    userInfo.put("isPemilik", "1");
                }

                df.set(userInfo);
                Toast.makeText(EditAkun.this, "Sukses edit data", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            getFirestore(firebaseUser.getUid());
            uid = firebaseUser.getUid();
        }
    }

    private void getFirestore(String uid) {
        DocumentReference df =  fStore.collection("Users").document(uid);

        //extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("TAG", "onSuccess" + documentSnapshot.getData());
//
                nama.setText(documentSnapshot.getString("nama"));
                notelp.setText(documentSnapshot.getString("noTelp"));
                email = documentSnapshot.getString("email");

                if (documentSnapshot.getString("isKaryawan") != null){
                    karyawan = documentSnapshot.getString("isKaryawan");
                }

                if (documentSnapshot.getString("isPemilik") != null){
                    pemilik = documentSnapshot.getString("isPemilik");
                }
//                FirebaseAuth.getInstance().signOut();
            }
        });
    }
}