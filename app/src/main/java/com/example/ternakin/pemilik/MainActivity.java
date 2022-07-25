package com.example.ternakin.pemilik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.ternakin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ImageView profilUser;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profilUser = findViewById(R.id.profilUser);

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
                        selectedFragment = new DataFragmentPemilik();
                        break;

                    case R.id.nav_chart:
                        selectedFragment = new ChartFragmentPemilik();
                        break;

                    case R.id.nav_storage:
                        selectedFragment = new StorageFragmentPemilik();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
                return true;
            }

        });

        profilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });

    }
}