package com.tricky_tweaks.homekeeping.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.admin.adapter.VendorApplicationRecyclerAdapter;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

import java.util.ArrayList;

public class AppliedVendorsFragment extends Fragment {

    ArrayList<VendorDataModel> arrayList;
    private VendorApplicationRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_vendors, container, false);

        arrayList = new ArrayList<>();
        init_recyclerView(view);
        populateList();

        return view;
    }

    private void init_recyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new VendorApplicationRecyclerAdapter(getActivity(), arrayList);
        adapter.setOnItemClickListener(position -> {
            VendorDataModel vendorDataModel = arrayList.get(position);
            if (vendorDataModel != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataModel", vendorDataModel);
                Navigation.findNavController(getActivity(), R.id.activity_main_nav_host).navigate(R.id.action_appliedVendorsFragment_to_viewApplicationFragment, bundle);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void populateList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VendorApplications");

        Query query = reference.limitToLast(10);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot sp : dataSnapshot.getChildren()) {
                    Log.e("Applied vendor ", sp+"");
                    for (DataSnapshot snapshot : sp.getChildren()) {
                        Log.e("Applied vendor ", snapshot+"");
                        VendorDataModel vdm = snapshot.getValue(VendorDataModel.class);
                        if (vdm != null) {
                            arrayList.add(vdm);
                            adapter.notifyDataSetChanged();
                            Log.e("Applied vendometa test ", vdm.getMetadata().toString()+"");
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

//test code

//        reference.child("asdfa").setValue(new VendorDataModel(
//
//                new BankDetailsModel(
//                        "_textViewName.getText().toString()",
//                        "_textViewAccountNumber.getText().toString()",
//                        "_textViewIFSCCode.getText().toString()",
//                        "thumbImageDownloadUri"
//                ),
//
//                new IdentityProofModel(
//                        "asd",
//                        "asf",
//                        "asfa"
//                ),
//
//                new CurrentAddressModel(
//                        "_textViewHouseNo.getText().toString()",
//                        " _textViewStreet.getText().toString()",
//                        "_textViewPincode.getText().toString()",
//                        "_textViewCity.getText().toString()",
//                        "_textViewState.getText().toString()"
//                ),
//
//                new PersonalDetailModel(
//                        "_textViewName.getText().toString()",
//                        "       _textViewAadhaarNo.getText().toString()",
//                        "_textViewPancardNo.getText().toString()",
//                        "_textViewDob.getText().toString()",
//                        " gender",
//                        "_textViewParentName.getText().toString()"
//                )
//
//        )).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getActivity(), "failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
