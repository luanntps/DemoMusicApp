package com.dungps19554.gatifymusic;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dungps19554.gatifymusic.fragment.*  ;
import com.dungps19554.gatifymusic.fragment.home_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNV;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        bottomNV = findViewById(R.id.bottomnavigationView);
        Fragment fragment = new home_fragment();
        loadFragment(fragment, "Home");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                Class  classFragment;
                switch (item.getItemId()){
                    case R.id.it_home:
                        item.setCheckable(true);
                        classFragment = home_fragment.class;
                        bottomNV.getMenu().findItem(R.id.it_home).setChecked(true);
                        break;
                    case R.id.it_bxh:
                        item.setCheckable(true);
                        classFragment = bxh_fragment.class;
                        bottomNV.getMenu().findItem(R.id.it_bxh).setChecked(true);
                        break;
                    case R.id.it_live:
                        item.setCheckable(true);
                        classFragment = live_fragment.class;
                        bottomNV.getMenu().findItem(R.id.it_live).setChecked(true);
                        break;
                    case R.id.it_search:
                        item.setCheckable(true);
                        classFragment = search_fragment.class;
                        bottomNV.getMenu().findItem(R.id.it_search).setChecked(true);
                        break;
                    default:
                        item.setChecked(true);
                        classFragment = home_fragment.class;
                        bottomNV.getMenu().findItem(R.id.it_home).setChecked(true);
                        break;
                }
                try {
                    fragment = (Fragment) classFragment.newInstance();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
                    setTitle(item.getTitle());
                }catch (Exception e){

                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        bottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                if (item.getItemId() == R.id.it_home) {
                    item.setChecked(true);
                    fragment = new home_fragment();
                    loadFragment(fragment, item.getTitle()+"");
                    navigationView.setCheckedItem(R.id.it_home);
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.it_bxh) {
                    item.setChecked(true);
                    fragment = new bxh_fragment();
                    loadFragment(fragment, item.getTitle()+"");
                    navigationView.setCheckedItem(R.id.it_bxh);
                    Toast.makeText(MainActivity.this, "BXH", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.it_live) {
                    item.setChecked(true);
                    fragment = new live_fragment();
                    loadFragment(fragment, item.getTitle()+"");
                    navigationView.setCheckedItem(R.id.it_live);
                    Toast.makeText(MainActivity.this, "Live", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.it_search) {
                    item.setChecked(true);
                    fragment = new search_fragment();
                    loadFragment(fragment, item.getTitle()+"");
                    navigationView.setCheckedItem(R.id.it_search);
                    Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ivHomeDraw) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadFragment(Fragment fragment, String title) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_in, R.anim.anim_out);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        setTitle(title);
    }
}