package com.tricky_tweaks.homekeeping.main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.main.utils.SharedPrefsUtils;
import com.tricky_tweaks.homekeeping.main.utils.UploadImageUtils;
import com.tricky_tweaks.homekeeping.model.BankDetailsModel;
import com.tricky_tweaks.homekeeping.model.CurrentAddressModel;
import com.tricky_tweaks.homekeeping.model.IdentityProofModel;
import com.tricky_tweaks.homekeeping.model.PersonalDetailModel;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VendorFragment extends Fragment {

    private NavController _navController;
    private BankDetailsModel bankDetailsModel;
    private CurrentAddressModel currentAddressModel;
    private PersonalDetailModel personalDetailModel;
    private IdentityProofModel identityProofModel;

    private final static byte PASSBOOK_CODE = 13;
    private final static byte ADDHAAR_FRONT_CODE = 14;
    private final static byte AADHAAR_BACK_CODE = 15;
    private final static byte PAN_CODE = 16;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    private void init_data_model() {

        String currentAddressJson = SharedPrefsUtils.getStringPreference(getActivity(), "currentAddressModel", 0);
        currentAddressModel = new Gson().fromJson(currentAddressJson, CurrentAddressModel.class);

        String personalDetailJson = SharedPrefsUtils.getStringPreference(getActivity(), "personalDetailModel", 0);
        personalDetailModel = new Gson().fromJson(personalDetailJson, PersonalDetailModel.class);

        String identityProofJson = SharedPrefsUtils.getStringPreference(getActivity(), "identityProofModel", 0);
        identityProofModel = new Gson().fromJson(identityProofJson, IdentityProofModel.class);

        String bankDetailJson = SharedPrefsUtils.getStringPreference(getActivity(), "bankDetailsModel", 0);
        bankDetailsModel = new Gson().fromJson(bankDetailJson, BankDetailsModel.class);
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
        MaterialButton _sendProfileForVerification = view.findViewById(R.id.fragment_home_mb_send);

        init_data_model();

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

        _sendProfileForVerification.setOnClickListener(n -> {

            if (bankDetailsModel == null) {
                _vendBankDetails.setStrokeColor(ColorStateList.valueOf(Color.RED));
                Toast.makeText(getActivity(), "complete above details bankDetails", Toast.LENGTH_SHORT).show();
                return;
            }

            if (personalDetailModel == null) {
                _vendPD.setStrokeColor(ColorStateList.valueOf(Color.RED));
                Toast.makeText(getActivity(), "complete above details personalDetail", Toast.LENGTH_SHORT).show();
                return;
            }

            if (identityProofModel == null) {
                _vendIDProof.setStrokeColor(ColorStateList.valueOf(Color.RED));
                Toast.makeText(getActivity(), "complete above details identityProof", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentAddressModel == null) {
                _vendCA.setStrokeColor(ColorStateList.valueOf(Color.RED));
                Toast.makeText(getActivity(), "complete above details currentAddress", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!fileExist(new File(Objects.requireNonNull(Uri.parse(bankDetailsModel.getPassbookImageUrl()).getPath())))) {
                //Todo show bank error
                return;
            }

            for (File file : new ArrayList<File>(
                    Arrays.asList(
                            new File(Objects.requireNonNull(Uri.parse(identityProofModel.getAadhaarCardFrontImageUrl()).getPath())),
                            new File(Objects.requireNonNull(Uri.parse(identityProofModel.getAadhaarCardBackImageUrl()).getPath())),
                            new File(Objects.requireNonNull(Uri.parse(identityProofModel.getPanCardUrl()).getPath()))
                    )
            )) {
                if (!fileExist(file)) {
                    //Todo show identity error
                    return;
                }
            }


            uploadImage("fdsdaf", new File(Objects.requireNonNull(Uri.parse(bankDetailsModel.getPassbookImageUrl()).getPath())), PASSBOOK_CODE );

            uploadImage("fdsdaf", new File(Objects.requireNonNull(Uri.parse(identityProofModel.getAadhaarCardFrontImageUrl()).getPath())), ADDHAAR_FRONT_CODE);
            uploadImage("fdsdaf", new File(Objects.requireNonNull(Uri.parse(identityProofModel.getAadhaarCardBackImageUrl()).getPath())), AADHAAR_BACK_CODE );
            uploadImage("fdsdaf", new File(Objects.requireNonNull(Uri.parse(identityProofModel.getPanCardUrl()).getPath())), PAN_CODE );

        });

        onBackPressed();

        return view;
    }

    private boolean fileExist(File file) {
        return file.exists();
    }

    private void uploadImage(String storagePath, File file , int code) {
        UploadImageUtils uploadImageUtils = new UploadImageUtils(storagePath, getActivity());
        uploadImageUtils.setOnUploadListener(new UploadImageUtils.OnImageUploadListener() {
            @Override
            public void onFileNotFoundException(IOException xe) {
                Toast.makeText(getActivity(), "file to get bank passbook image re upload", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccessListener(String uri, int code) {
                Log.e("upload listener code ", code+"");
                Log.e("upload listener", uri);

                switch (code) {
                    case PASSBOOK_CODE:
                        bankDetailsModel.setPassbookImageUrl(uri);
                        break;
                    case ADDHAAR_FRONT_CODE:
                        identityProofModel.setAadhaarCardFrontImageUrl(uri);
                        break;
                    case AADHAAR_BACK_CODE:
                        identityProofModel.setAadhaarCardBackImageUrl(uri);
                        break;
                    case PAN_CODE:
                        identityProofModel.setPanCardUrl(uri);
                        sendApplicationRequest();
                        break;
                }
            }

            @Override
            public void onFailedListener(String message) {

            }
        });

        uploadImageUtils.uploadImage(file, code);
    }

    private void sendApplicationRequest() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("VendorApplications")
                .child(FirebaseAuth.getInstance().getUid());

        Map<String, Object> metadata = new HashMap<>();

        String key = reference.push().getKey();
        metadata.put("applicationId", key);
        metadata.put("UserId", FirebaseAuth.getInstance().getUid());
        metadata.put("date", new SimpleDateFormat("dd MMMM yyyy").format(new Date()));
        metadata.put("status", "pending");
        reference.child(key+"/").setValue(
                new VendorDataModel(
                        bankDetailsModel,
                        identityProofModel,
                        currentAddressModel,
                        personalDetailModel
                )
        ).addOnSuccessListener(aVoid -> {
            reference.child(key+"/"+"metadata/").setValue(metadata);
            Toast.makeText(getActivity(), "application send successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(getActivity(), "failed to send " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void onBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                _navController.popBackStack(R.id.homeFragment, false);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(
                this,
                callback
        );
    }

}
