package com.tricky_tweaks.homekeeping.main.bottom_navigation_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.tricky_tweaks.homekeeping.R;

public class HomeFragment extends Fragment {

    NavController controller;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        MaterialButton _applyAsVendorBtn = view.findViewById(R.id.fragment_home_mb_vendor);
        _applyAsVendorBtn.setOnClickListener( n -> controller.navigate(R.id.action_homeFragment_to_vendorCategory));
        return view;
    }

}
