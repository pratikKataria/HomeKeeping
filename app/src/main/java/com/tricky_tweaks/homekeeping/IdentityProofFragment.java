package com.tricky_tweaks.homekeeping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.app.Activity.RESULT_OK;

public class IdentityProofFragment extends Fragment {

    ImageView _adharFrontImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_identity_proof, container, false);

        _adharFrontImage = view.findViewById(R.id.id_iv_adhar_font);
        _adharFrontImage.setOnClickListener(n -> {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(getActivity());

        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (result.getUri() != null) {
                    _adharFrontImage.setImageURI(result.getUri());
                }
            }
        }
    }
}
