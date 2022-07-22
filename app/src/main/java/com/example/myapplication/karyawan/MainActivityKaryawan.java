package com.example.myapplication.karyawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.Login;
import com.example.myapplication.R;
import com.example.myapplication.pemilik.ChartFragmentPemilik;
import com.example.myapplication.pemilik.DataFragmentPemilik;
import com.example.myapplication.pemilik.HomeFragmentPemilik;
import com.example.myapplication.pemilik.StorageFragmentPemilik;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityKaryawan extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_karyawan);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragmentPemilik()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){

                    case R.id.nav_home:
                        selectedFragment = new HomeFragmentPemilik();
                        break;

                    case R.id.nav_data:
                        selectedFragment = new DataFragmentKaryawan();
                        break;

                    case R.id.nav_user:
                        selectedFragment = new UserFragmentKaryawan();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
                return true;
            }

        });

    }
}