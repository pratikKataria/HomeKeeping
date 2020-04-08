package com.tricky_tweaks.homekeeping.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firestore.v1.StructuredQuery;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.main.bottom_navigation_fragment.HomeFragment;
import com.tricky_tweaks.homekeeping.main.bottom_navigation_fragment.OrdersFragment;
import com.tricky_tweaks.homekeeping.main.bottom_navigation_fragment.ProfileFragment;

import java.util.HashSet;
import java.util.Set;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

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
