package com.tricky_tweaks.homekeeping.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.binding.IVendorApplication;
import com.tricky_tweaks.homekeeping.databinding.FragmentAppliedVendorsBinding;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

import java.util.ArrayList;
import java.util.List;

public class AppliedVendorsFragment extends Fragment implements IVendorApplication {

    List<VendorDataModel> arrayList;
    FragmentAppliedVendorsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_applied_vendors,
                container,
                false
        );
        binding.setActivity(getActivity());

        View view = binding.getRoot();

        arrayList = new ArrayList<>();
        binding.setVendorData(arrayList);
        populateList();

        return view;
    }

    private void populateList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VendorApplications");

        Query query = reference.limitToLast(10);
        query.keepSynced(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot sp : dataSnapshot.getChildren()) {
                    Log.e("Applied vendor ", sp+"");
                    for (DataSnapshot snapshot : sp.getChildren()) {
                        Log.e("Applied vendor ", snapshot+"");
                        VendorDataModel vdm = snapshot.getValue(VendorDataModel.class);
                        if (vdm != null) {
                            arrayList.add(vdm);
                            if (binding.recyclerview.getAdapter() != null)
                                binding.recyclerview.getAdapter().notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void inflateViewApplication(VendorDataModel vendorData) {
        if (vendorData != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataModel", vendorData);
            Navigation.findNavController(getActivity(), R.id.activity_main_nav_host).navigate(R.id.action_appliedVendorsFragment_to_viewApplicationFragment, bundle);
        }
    }
}
