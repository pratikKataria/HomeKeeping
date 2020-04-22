package com.tricky_tweaks.homekeeping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tricky_tweaks.homekeeping.databinding.FragmentHireVendorsBinding;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;
import com.tricky_tweaks.homekeeping.model.company.CompanyInfoModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HireVendorsFragment extends Fragment {

    private FragmentHireVendorsBinding binding;
    private DatabaseReference reference;
    private ValueEventListener valueEventListener;
    private CompanyInfoModel companyInfoModel;
    private ArrayList<VendorDataModel> vendorList;

    public HireVendorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyInfoModel = (CompanyInfoModel) getArguments().getSerializable("COMPANY_INFO_MODEL");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_hire_vendors,
                container,
                false
        );


        if (companyInfoModel != null) {
            binding.setCompanyInformation(companyInfoModel);
            binding.setChipItems(new ArrayList<>(companyInfoModel.getMetadata().getServices().values()));
        }
        binding.setActivity(getActivity());

        reference = FirebaseDatabase.getInstance().getReference("Vendors");
        vendorList = new ArrayList<>();
        binding.setVendors(vendorList);
        populateList();

        return binding.getRoot();
    }


    private void populateList() {
        Log.e("snapshot", "populateList()"+"");
        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("snapshot", dataSnapshot+"");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    VendorDataModel vendorDataModel = snapshot.getValue(VendorDataModel.class);
                    if (vendorDataModel != null) {
                        vendorList.add(vendorDataModel);
                        if (binding.recyclerview.getAdapter() != null )
                            binding.recyclerview.getAdapter().notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (valueEventListener != null) {
            reference.removeEventListener(valueEventListener);
        }
    }
}
