package com.example.ternakin.pemilik.masterTernak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ternakin.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailMasterTernak extends AppCompatActivity {

    private TextView id_periode;
    private EditText tanggal_datang, jumlah_bibit, tvDateResult;
    private Button btnInsert, btnHapus;
    private ImageView btnKembali;
    private SimpleDateFormat dateFormatter;
    ProgressDialog progressDialog;
    boolean valid = true;
    Locale localeID = new Locale("in", "ID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_master_ternak);

        id_periode = findViewById(R.id.id_periode);
        tanggal_datang = findViewById(R.id.tanggal_datang);
        jumlah_bibit = findViewById(R.id.jumlah_bibit);
        btnInsert = findViewById(R.id.btnInsert);
        btnHapus = findViewById(R.id.btnHapus);
        btnKembali = findViewById(R.id.btnKembali);

        getIncomingExtra();
    }

    private void getIncomingExtra() {
        if (getIntent().hasExtra("jumlah_bibit") && getIntent().hasExtra("tanggal_datang") && getIntent().hasExtra("id_periode")){
            String tampil_jumlah_bibit = getIntent().getStringExtra("jumlah_bibit");
            String tampil_tanggal_datang = getIntent().getStringExtra("tanggal_datang");
            String tampil_id_periode = getIntent().getStringExtra("id_periode");

            setDataActivity(tampil_jumlah_bibit, tampil_tanggal_datang, tampil_id_periode);
        }
    }

    private void setDataActivity(String tampil_jumlah_bibit, String tampil_tanggal_datang, String tampil_id_periode) {

        jumlah_bibit.setText(tampil_jumlah_bibit);
        tanggal_datang.setText(tampil_tanggal_datang);
        id_periode.setText(tampil_id_periode);
    }
}