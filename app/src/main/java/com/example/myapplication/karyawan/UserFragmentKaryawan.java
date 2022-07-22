package com.example.myapplication.karyawan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Login;
import com.example.myapplication.R;
import com.example.myapplication.Registrasi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragmentKaryawan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragmentKaryawan extends Fragment {

    private Button btnLogout;

    private TextView nama;
    private FirebaseUser firebaseUser;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    TextView tvPhone, tvEmail, tvUsername, tvPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragmentKaryawan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragmentKaryawan.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragmentKaryawan newInstance(String param1, String param2) {
        UserFragmentKaryawan fragment = new UserFragmentKaryawan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_karyawan, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);

        fStore = FirebaseFirestore.getInstance();

        nama = view.findViewById(R.id.nama);
        btnLogout = view.findViewById(R.id.btnLogout);
        tvPhone = view.findViewById(R.id.phoneNumber);
        tvEmail = view.findViewById(R.id.email);
        tvUsername = view.findViewById(R.id.username);
        tvPassword = view.findViewById(R.id.password);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Log.d("TAG ", "onCreate: "+firebaseUser.getUid());
            checkUserAccessLevel(firebaseUser.getUid());
        }

        return view;
    }

    @Override
    public void onResume() {
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
            }
        });
    }
}