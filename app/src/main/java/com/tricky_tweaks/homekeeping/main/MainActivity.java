package com.tricky_tweaks.homekeeping.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firestore.v1.StructuredQuery;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.main.bottom_navigation_fragment.HomeFragment;
import com.tricky_tweaks.homekeeping.main.bottom_navigation_fragment.OrdersFragment;
import com.tricky_tweaks.homekeeping.main.bottom_navigation_fragment.ProfileFragment;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private OrdersFragment ordersFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);

        final SmoothBottomBar smoothBottomBar = findViewById(R.id.bottomBar);
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {
                switch (i) {
                    case 0:
                        loadFragment(homeFragment);
                        break;
                    case 1:
                        ordersFragment = new OrdersFragment();
                        loadFragment(ordersFragment);
                        break;
                    case 2:
                        profileFragment = new ProfileFragment();
                        loadFragment(profileFragment);
                        break;
                }
            }
        });
    }

    private void loadFragment(Fragment frag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.activity_main_frame_layout, frag);
        transaction.commit();
    }
}
