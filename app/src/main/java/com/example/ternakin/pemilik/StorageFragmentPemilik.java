package com.example.ternakin.pemilik;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ternakin.R;
import com.example.ternakin.pemilik.masterKandang.MasterKandang;
import com.example.ternakin.pemilik.masterTernak.MasterTernak;
import com.example.ternakin.pemilik.masterkaryawan.MasterKaryawan;
import com.example.ternakin.pemilik.masterpakan.MasterPakan;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StorageFragmentPemilik#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StorageFragmentPemilik extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public StorageFragmentPemilik() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StorageFragmentPemilik.
     */
    // TODO: Rename and change types and number of parameters
    public static StorageFragmentPemilik newInstance(String param1, String param2) {
        StorageFragmentPemilik fragment = new StorageFragmentPemilik();
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
        View view = inflater.inflate(R.layout.fragment_storage_pemilik, container, false);

        LinearLayout dataKaryawan, dataPakan, dataTernak, dataKandang;

        dataKaryawan = view.findViewById(R.id.cardDataKaryawan);
        dataPakan = view.findViewById(R.id.cardDataPakan);
        dataTernak = view.findViewById(R.id.cardDataTernak);
        dataKandang = view.findViewById(R.id.cardDataKandang);

        dataKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MasterKaryawan.class);
                startActivity(intent);
            }
        });

        dataPakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MasterPakan.class);
                startActivity(intent);
            }
        });

        dataTernak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MasterTernak.class);
                startActivity(intent);

            }
        });

        dataKandang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MasterKandang.class);
                startActivity(intent);
            }
        });

        return view;
    }
}