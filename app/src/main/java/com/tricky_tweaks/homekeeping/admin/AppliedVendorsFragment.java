package com.tricky_tweaks.homekeeping.admin;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.model.BankDetailsModel;
import com.tricky_tweaks.homekeeping.model.CurrentAddressModel;
import com.tricky_tweaks.homekeeping.model.IdentityProofModel;
import com.tricky_tweaks.homekeeping.model.PersonalDetailModel;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

public class AppliedVendorsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_vendors, container, false);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vendors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot sp : dataSnapshot.getChildren()) {
                    VendorDataModel vdm = sp.getValue(VendorDataModel.class);
                    if (vdm != null) {

                    }
                    Log.e("applied vendor " , vdm.getPersonalDetailModel().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
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
