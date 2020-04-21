package com.tricky_tweaks.homekeeping.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.databinding.FragmentViewCompaniesBinding;
import com.tricky_tweaks.homekeeping.model.company.CompanyInfoModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewCompaniesFragment extends Fragment {

    private FragmentViewCompaniesBinding binding;
    private List<CompanyInfoModel> list;

    public ViewCompaniesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_view_companies,
                container,
                false
        );

        list = new ArrayList<>();
        binding.setBranch(list);
        populateList();

        return binding.getRoot();
    }

    public void populateList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CompanyInfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CompanyInfoModel companyInfoModel = snapshot.getValue(CompanyInfoModel.class);
                    if (companyInfoModel != null) {
                        list.add(companyInfoModel);
                        if (binding.recyclerview.getAdapter() != null)
                            binding.recyclerview.getAdapter().notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
