package com.tricky_tweaks.homekeeping.main.vendor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
    
    private MaterialButton vendPD;
    private MaterialButton vendCA;
    private MaterialButton vendBankDetails;
    private MaterialButton vendIDProof;

    private ProgressBar progressBar;
    private MaterialButton sendProfileForVerification;

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

        vendIDProof = view.findViewById(R.id.vendor_mb_id_proof);
        vendPD = view.findViewById(R.id.vendor_mb_pd);
        vendCA = view.findViewById(R.id.vendor_mb_cd);
        vendBankDetails = view.findViewById(R.id.vendor_mb_bank_details);
        progressBar = view.findViewById(R.id.progressBar);
        sendProfileForVerification = view.findViewById(R.id.fragment_home_mb_send);

        onApplicationAlreadyPresent();

        vendIDProof.setOnClickListener(n -> {
            _navController.navigate(R.id.action_vendorFragment_to_identityProofFragment);
        });

        vendPD.setOnClickListener(n -> {
            _navController.navigate(R.id.action_vendorFragment_to_personalDetailFragment);

        });

        vendCA.setOnClickListener(n -> {
            _navController.navigate(R.id.action_vendorFragment_to_currentAddressFragment);

        });

        vendBankDetails.setOnClickListener(n -> {
            _navController.navigate(R.id.action_vendorFragment_to_bankDetailFragment);

        });

        sendProfileForVerification.setOnClickListener(n -> {

            if (bankDetailsModel == null) {
                vendBankDetails.setStrokeColor(ColorStateList.valueOf(Color.RED));
                Toast.makeText(getActivity(), "complete above details bankDetails", Toast.LENGTH_SHORT).show();
                return;
            }

            if (personalDetailModel == null) {
                vendPD.setStrokeColor(ColorStateList.valueOf(Color.RED));
                Toast.makeText(getActivity(), "complete above details personalDetail", Toast.LENGTH_SHORT).show();
                return;
            }

            if (identityProofModel == null) {
                vendIDProof.setStrokeColor(ColorStateList.valueOf(Color.RED));
                Toast.makeText(getActivity(), "complete above details identityProof", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentAddressModel == null) {
                vendCA.setStrokeColor(ColorStateList.valueOf(Color.RED));
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

            progressBar.setVisibility(View.VISIBLE);

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
                .getReference("VendorApplications");

        String key = reference.push().getKey();

        Map<String, Object> metadata = new HashMap<>();

        metadata.put("applicationId", key);
        metadata.put("UserId", FirebaseAuth.getInstance().getUid());
        metadata.put("date", new SimpleDateFormat("dd MMMM yyyy").format(new Date()));
        metadata.put("status", "pending");
        metadata.put("service", SharedPrefsUtils.getStringPreference(getActivity(), "SERVICE_SELECTED", 0));
        reference.child(FirebaseAuth.getInstance().getUid())
                .child(key)
                .setValue(
                new VendorDataModel(
                        bankDetailsModel,
                        identityProofModel,
                        currentAddressModel,
                        personalDetailModel
                )
        ).addOnSuccessListener(aVoid -> {
            reference.child(FirebaseAuth.getInstance().getUid()).child(key).child("metadata/").setValue(metadata);
            progressBar.setVisibility(View.GONE);
            sendProfileForVerification.setText("successfully send");
            SharedPrefsUtils.setBooleanPreference(getActivity(), "APPLICATION_ALREADY_EXIST",  true);
            SharedPrefsUtils.removeValuePreference(getActivity(), "SERVICE_SELECTED");
            Toast.makeText(getActivity(), "application send successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "failed to send " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
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

    private void onApplicationAlreadyPresent() {
        boolean isApplicationPresent = SharedPrefsUtils.getBooleanPreference(getActivity(), "APPLICATION_ALREADY_EXIST", false);
        if (isApplicationPresent) {
            showAlertDialog();
        }else{
            setButtonColor();
            init_data_model();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Vendor Application")
                .setMessage("would you like create new application?")
                .setCancelable(false)
                .setPositiveButton("CREATE NEW",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefsUtils.removeValuePreference(getContext(), "bankDetailsModel");
                        SharedPrefsUtils.removeValuePreference(getContext(), "identityProofModel");
                        SharedPrefsUtils.removeValuePreference(getContext(), "personalDetailModel");
                        SharedPrefsUtils.removeValuePreference(getContext(), "currentAddressModel");
                        SharedPrefsUtils.setBooleanPreference(getActivity(), "APPLICATION_ALREADY_EXIST",  false);
                        init_data_model();
                        setButtonColor();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _navController.popBackStack(R.id.homeFragment, false);

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        init_data_model();
        setButtonColor();
    }

    private void setButtonColor() {
        if (bankDetailsModel == null) {
            vendBankDetails.setStrokeColor(ColorStateList.valueOf(Color.RED));
        } else {
            vendBankDetails.setStrokeColor(ColorStateList.valueOf(Color.GREEN));
        }

        if (personalDetailModel == null) {
            vendPD.setStrokeColor(ColorStateList.valueOf(Color.RED));
        } else {
            vendPD.setStrokeColor(ColorStateList.valueOf(Color.GREEN));
        }

        if (identityProofModel == null) {
            vendIDProof.setStrokeColor(ColorStateList.valueOf(Color.RED));
        } else {
            vendIDProof.setStrokeColor(ColorStateList.valueOf(Color.GREEN));

        }

        if (currentAddressModel == null) {
            vendCA.setStrokeColor(ColorStateList.valueOf(Color.RED));
        } else {
            vendCA.setStrokeColor(ColorStateList.valueOf(Color.GREEN));

        }
    }

}
