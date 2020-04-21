package com.tricky_tweaks.homekeeping.admin;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.tricky_tweaks.homekeeping.R;


public class AdminFragment extends Fragment {

    NavController _navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        ImageButton viewAllVendors = view.findViewById(R.id.id_mb_applied_vends);
        viewAllVendors.setOnClickListener(n -> _navController.navigate(R.id.action_adminFragment_to_appliedVendorsFragment));

        ImageButton addCompBtn = view.findViewById(R.id.addComp);
        addCompBtn.setOnClickListener(n -> _navController.navigate(R.id.action_adminFragment_to_multipleServiceFragment));

        ImageButton viewCompanies = view.findViewById(R.id.viewCompany);
        viewCompanies.setOnClickListener(n -> _navController.navigate(R.id.action_adminFragment_to_viewCompaniesFragment));

        return view;
    }
}
