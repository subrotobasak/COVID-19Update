package com.example.covid19news.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.covid19news.Fragment.About;
import com.example.covid19news.Fragment.Country;
import com.example.covid19news.Fragment.Global;
import com.example.covid19news.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    int view = R.layout.activity_main;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.setTitle("Corona Live News");

        loadFragment(new Global());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_global:
                    toolbar.setTitle("Corona Live News");
                    fragment = new Global();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_country:
                    toolbar.setTitle("Country");
                    fragment = new Country();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_about:
                    toolbar.setTitle("About");
                    fragment = new About();
                    loadFragment(fragment);
                    return true;


            }
            return false;
        }

    };

    // Load Fragment
    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    // For Exit

    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(1);
            return;

        } else {
            Toast.makeText(MainActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();

    }
}
