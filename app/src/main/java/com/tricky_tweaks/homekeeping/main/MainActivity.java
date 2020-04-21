package com.tricky_tweaks.homekeeping.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tricky_tweaks.homekeeping.R;

public class MainActivity extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration;

    NavController navController;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bottomBar);

        setUpBottomNavigationView();
    }

    private void setUpBottomNavigationView() {

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.activity_main_nav_host);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bnv, navHostFragment.getNavController());

//            Set<Integer> topLevelDestinations = new HashSet<>();
//            topLevelDestinations.add(R.id.bottom_nav_home);
//            topLevelDestinations.add(R.id.bottom_nav_orders);
//            topLevelDestinations.add(R.id.bottom_nav_profile);
//            appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
//            NavigationUI.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration);
        }

    }
}
