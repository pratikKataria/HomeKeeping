package com.tricky_tweaks.homekeeping.main.vendor_details_fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.model.IdentityProofModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class IdentityProofFragment extends Fragment {

    private ImageView _aadhaarFrontImage;
    private ImageView _aadhaarBackImage;
    private ImageView _panImage;
    private MaterialButton _saveButton;
    private ProgressBar _progressBar;

    private NavController _navController;

    private Uri _aadhaarFrontImageUri;
    private Uri _aadhaarBackImageUri;
    private Uri _panImageUri;

    private Uri imageUri;

    private ArrayList<Uri> imageUriList;

    private final static int AADHAAR_FRONT_CODE = 1567;
    private final static int AADHAAR_BACK_CODE = 1457;
    private final static int PAN_CODE = 5416;

    private Bitmap compressedImageFile;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_identity_proof, container, false);

        _aadhaarFrontImage = view.findViewById(R.id.id_iv_adhar_font);
        _aadhaarBackImage = view.findViewById(R.id.id_iv_adhar_back);
        _panImage = view.findViewById(R.id.id_iv_pan);
        _saveButton = view.findViewById(R.id.id_mb_verify);
        _progressBar = view.findViewById(R.id.progressBar);

        imageUriList = new ArrayList<>();

        _aadhaarFrontImage.setOnClickListener(n -> {
            Intent intent = CropImage.activity().setAspectRatio(1, 1).getIntent(getActivity());
            startActivityForResult(intent, AADHAAR_FRONT_CODE);
        });

        _aadhaarBackImage.setOnClickListener(n -> {
            Intent intent = CropImage.activity().setAspectRatio(1, 1).getIntent(getActivity());
            startActivityForResult(intent, AADHAAR_BACK_CODE);
        });

        _panImage.setOnClickListener(n -> {
            Intent intent = CropImage.activity().setAspectRatio(1, 1).getIntent(getActivity());
            startActivityForResult(intent, PAN_CODE);
        });

        _saveButton.setOnClickListener(n -> {
            if (_aadhaarFrontImageUri == null) {
                Toast.makeText(getActivity(), "select image", Toast.LENGTH_SHORT).show();
                return;
            }

            if (_aadhaarBackImageUri == null) {
                Toast.makeText(getActivity(), "select image", Toast.LENGTH_SHORT).show();
                return;
            }

            if (_panImageUri == null) {
                Toast.makeText(getActivity(), "select image", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getActivity(), "uploading image...", Toast.LENGTH_SHORT).show();
            uploadImage(_aadhaarFrontImageUri, AADHAAR_FRONT_CODE);

            uploadImage(_aadhaarBackImageUri, AADHAAR_BACK_CODE);

            uploadImage(_panImageUri, PAN_CODE);

        });

        return view;
    }

    private void uploadImage(Uri imageUri, int code) {
        _progressBar.setVisibility(View.VISIBLE);
        File newImageFile = new File(imageUri.getPath());

        try {
            compressedImageFile = new Compressor(getActivity())
                    .setQuality(30).compressToBitmap(newImageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageReference storage = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getUid())
                .child("IdentityProofImages/");

        Map<String, Object> imageUrlMap = new HashMap<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Vendors");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] thumbData = baos.toByteArray();

        UploadTask uploadTask = storage.child(getSaltString() + ".jpeg").putBytes(thumbData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                result.addOnSuccessListener(uri -> {
                    String thumbImageDownloadUri = uri.toString();
                    if (code == AADHAAR_FRONT_CODE) {
                        imageUrlMap.put("aadhaarCardFrontImageUrl", thumbImageDownloadUri);
                        reference.child(FirebaseAuth.getInstance().getUid() + "/IdentityProof").updateChildren(imageUrlMap);
                        _progressBar.setVisibility(View.GONE);
                    } else if (code == AADHAAR_BACK_CODE) {
                        _progressBar.setVisibility(View.GONE);
                        reference.child(FirebaseAuth.getInstance().getUid() + "/IdentityProof").updateChildren(imageUrlMap);
                        imageUrlMap.put("aadhaarCardBackImageUrl", thumbImageDownloadUri);
                    } else if (code == PAN_CODE) {
                        imageUrlMap.put("panCardUrl", thumbImageDownloadUri);
                        reference.child(FirebaseAuth.getInstance().getUid() + "/IdentityProof").updateChildren(imageUrlMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                _navController.popBackStack(R.id.vendorFragment, false);
                                _progressBar.setVisibility(View.GONE);
                            }
                        });
                    }

                });
            }
        }).addOnFailureListener(e -> {
            _progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            if (result.getUri() != null) {
                Toast.makeText(getActivity(), "" + requestCode, Toast.LENGTH_SHORT).show();
                setImage(requestCode, result);
            }
        }
    }

    private void setImage(int code, CropImage.ActivityResult result) {
        switch (code) {
            case AADHAAR_FRONT_CODE:
                _aadhaarFrontImageUri = result.getUri();
                imageUri = result.getUri();
                imageUriList.add(imageUri);
                _aadhaarFrontImage.setImageURI(imageUri);
                break;
            case AADHAAR_BACK_CODE:
                _aadhaarBackImageUri = result.getUri();
                _aadhaarBackImage.setImageURI(result.getUri());
                break;
            case PAN_CODE:
                _panImageUri = result.getUri();
                _panImage.setImageURI(result.getUri());
                break;
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
