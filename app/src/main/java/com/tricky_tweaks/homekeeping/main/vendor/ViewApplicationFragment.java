package com.tricky_tweaks.homekeeping.main.vendor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.databinding.FragmentViewApplicationBinding;
import com.tricky_tweaks.homekeeping.model.Metadata;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

public class ViewApplicationFragment extends Fragment {

    private FragmentViewApplicationBinding binding;
    private VendorDataModel vendorDataModel;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_application, container, false);
        View view = binding.getRoot();

        MaterialButton acceptApplicationBtn = binding.btnAccept;
        progressBar = binding.progressBar;
        acceptApplicationBtn.setOnClickListener(n -> {
            updateMetadataApplicationAccepted();
        });


        return view;
    }

    private void updateMetadataApplicationAccepted() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VendorApplications");
        if (vendorDataModel != null) {
            Metadata metadata = vendorDataModel.getMetadata();
            metadata.setStatus("accepted");
            reference.child(metadata.getUserId())
                    .child(metadata.getApplicationId())
                    .child("metadata/")
                    .setValue(metadata)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "application accepted", Toast.LENGTH_SHORT).show();
                        addToVendors();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "failed to accept " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    });
        }
    }

    private void addToVendors() {
        if (vendorDataModel != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vendors");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    reference.child(vendorDataModel.getMetadata().getUserId()).setValue(
                            new VendorDataModel(
                                    vendorDataModel.getMetadata(),
                                    vendorDataModel.getCurrentAddressModel()
                            )
                    ).addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "application accepted", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getActivity(), "failed to accept " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        VendorDataModel vendorDataModel = (VendorDataModel) getArguments().getSerializable("dataModel");
        binding.setVendorDataModel(vendorDataModel);
        this.vendorDataModel = vendorDataModel;
        Log.e("View AppFragment", vendorDataModel.getPersonalDetailModel().toString());
    }
}
