package com.tricky_tweaks.homekeeping;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

public class VendorFragment extends Fragment {

    NavController _navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor, container, false);

        MaterialButton _vendIDProof = view.findViewById(R.id.vendor_mb_id_proof);
        MaterialButton _vendPD = view.findViewById(R.id.vendor_mb_pd);
        MaterialButton _vendCA = view.findViewById(R.id.vendor_mb_cd);
        MaterialButton _vendBankDetails = view.findViewById(R.id.vendor_mb_bank_details);

        _vendIDProof.setOnClickListener(n -> {
            _navController.navigate(R.id.action_vendorFragment_to_identityProofFragment);
        });

        _vendPD.setOnClickListener(n -> {
            _navController.navigate(R.id.action_vendorFragment_to_personalDetailFragment);

        });

        _vendCA.setOnClickListener(n -> {
            _navController.navigate(R.id.action_vendorFragment_to_currentAddressFragment);

        });

        _vendBankDetails.setOnClickListener(n -> {
            _navController.navigate(R.id.action_vendorFragment_to_bankDetailFragment);

        });


        return view;
    }
}
