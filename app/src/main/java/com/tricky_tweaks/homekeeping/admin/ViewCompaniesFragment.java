package com.tricky_tweaks.homekeeping.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.databinding.FragmentViewCompaniesBinding;
import com.tricky_tweaks.homekeeping.model.company.CompanyInfoModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewCompaniesFragment extends Fragment {

    private FragmentViewCompaniesBinding binding;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private List<CompanyInfoModel> list;

    public ViewCompaniesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("ViewCompaniesFragment", "onStart");
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
        Log.e("ViewCompaniesFragment", "onCreate");

        list = new ArrayList<>();
        binding.setBranch(list);
        binding.setActivity(getActivity());
        reference = FirebaseDatabase.getInstance().getReference("Companies");
        populateList();

        return binding.getRoot();
    }

    public void populateList() {
        Log.e("ViewCompaniesFragment", "popu");

        childEventListener = reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("ViewCompaniesFragment", "onChildAdded");
                CompanyInfoModel companyInfoModel = dataSnapshot.getValue(CompanyInfoModel.class);
                    if (companyInfoModel != null) {
                        list.add(companyInfoModel);

                        if (binding.recyclerview.getAdapter() != null) {
                            binding.recyclerview.getAdapter().notifyDataSetChanged();
                            Toast.makeText(getActivity(), "loading... ", Toast.LENGTH_SHORT).show();
                        }
                    }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("ViewCompaniesFragment", "onChildChanged");
                Iterator<CompanyInfoModel> companyInfoModelIterator = list.iterator();
                while (companyInfoModelIterator.hasNext()) {
                    CompanyInfoModel infoModel = companyInfoModelIterator.next();
                    if (infoModel != null && infoModel.getMetadata().getDatabaseRefKey().equals(dataSnapshot.getKey())) {
                        companyInfoModelIterator.remove();
                        CompanyInfoModel newData = dataSnapshot.getValue(CompanyInfoModel.class);
                        if (newData != null)
                            list.add(newData);
                        if (binding.recyclerview.getAdapter() != null) {
                            binding.recyclerview.getAdapter().notifyDataSetChanged();
                            Toast.makeText(getActivity(), "updating... ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (childEventListener != null) reference.removeEventListener(childEventListener);
        Log.e("ViewCompaniesFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (childEventListener != null) reference.removeEventListener(childEventListener);
        Log.e("ViewCompaniesFragment", "onStop");
    }
}
