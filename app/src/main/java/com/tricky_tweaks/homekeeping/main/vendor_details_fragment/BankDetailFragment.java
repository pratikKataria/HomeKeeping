package com.tricky_tweaks.homekeeping.main.vendor_details_fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.main.utils.SharedPrefsUtils;
import com.tricky_tweaks.homekeeping.model.vendors.BankDetailsModel;

import static android.app.Activity.RESULT_OK;

public class BankDetailFragment extends Fragment {

    private static final int PASSBOOK_CODE = 4574;

    private TextInputEditText _textViewName;
    private TextInputEditText _textViewAccountNumber;
    private TextInputEditText _textViewConfAccountNumber;
    private TextInputEditText _textViewIFSCCode;

    private ProgressBar _progressBar;
    private MaterialButton _saveBtn;

    private ImageView _imageViewPassbook;

    private Uri imageUri;

    private Bitmap compressedImageFile;

    private NavController _navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_detail, container, false);

        _textViewName = view.findViewById(R.id.bank_detail_name);
        _textViewAccountNumber = view.findViewById(R.id.bank_detail_account_number);
        _textViewConfAccountNumber = view.findViewById(R.id.bank_detail_confirm_account_number);
        _textViewIFSCCode = view.findViewById(R.id.bank_detail_ifsc_code);
        _progressBar = view.findViewById(R.id.progressBar);
        _saveBtn = view.findViewById(R.id.bank_detail_mb_save);
        _imageViewPassbook = view.findViewById(R.id.bank_detail_iv_passbook);


        _imageViewPassbook.setOnClickListener(n -> {

            Intent intent = CropImage.activity().setAspectRatio(16, 9).getIntent(getActivity());
            startActivityForResult(intent, PASSBOOK_CODE);
        });


        _saveBtn.setOnClickListener(n -> {
            if (imageUri == null) {
                Toast.makeText(getActivity(), "select image", Toast.LENGTH_SHORT).show();
                return;
            }

            if (_textViewName.getText().toString().isEmpty()) {
                _textViewName.setError("should not be empty");
                _textViewName.requestFocus();
                return;
            }

            if (_textViewAccountNumber.getText().toString().isEmpty()) {
                _textViewAccountNumber.setError("should not be empty");
                _textViewAccountNumber.requestFocus();
                return;
            }

            if (_textViewConfAccountNumber.getText().toString().isEmpty()) {
                _textViewConfAccountNumber.setError("should not be empty");
                _textViewConfAccountNumber.requestFocus();
                return;
            }

            if (_textViewIFSCCode.getText().toString().isEmpty()) {
                _textViewIFSCCode.setError("should not be empty");
                _textViewIFSCCode.requestFocus();
                return;
            }

            if (!_textViewConfAccountNumber.getText().toString().equals(_textViewAccountNumber.getText().toString())) {
                Toast.makeText(getActivity(), "account number doesnot match", Toast.LENGTH_SHORT).show();
                _textViewConfAccountNumber.setError("should not be empty");
                _textViewAccountNumber.setError("should not be empty");
                return;
            }

            saveToSharedPrefs();

        });


        return view;
    }

    private void saveToSharedPrefs() {
        BankDetailsModel bankDetailsModel =  new BankDetailsModel(
                _textViewName.getText().toString(),
                _textViewAccountNumber.getText().toString(),
                imageUri.toString(),
                _textViewIFSCCode.getText().toString()

        );

        SharedPrefsUtils.setStringPreference(getActivity(), "bankDetailsModel", new Gson().toJson(bankDetailsModel));
        _navController.popBackStack(R.id.vendorFragment, false);

    }


//    private void uploadImage() {
//        _progressBar.setVisibility(View.VISIBLE);
//        File newImageFile = new File(imageUri.getPath());
//
//        try {
//            compressedImageFile = new Compressor(getActivity())
//                    .setQuality(30).compressToBitmap(newImageFile);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vendors");
//        String key = reference.push().getKey();
//
//        StorageReference storage = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getUid())
//                .child("IdentityProofImages/");
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 30, baos);
//        byte[] thumbData = baos.toByteArray();
//
//        UploadTask uploadTask = storage.child(key + ".jpeg").putBytes(thumbData);
//        uploadTask.addOnSuccessListener(taskSnapshot -> {
//            if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
//                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
//                result.addOnSuccessListener(uri -> {
//                    String thumbImageDownloadUri = uri.toString();
//                    reference.child(FirebaseAuth.getInstance().getUid() + "/bankDetailsModel").setValue(
//                            new BankDetailsModel(
//                                    _textViewName.getText().toString(),
//                                    _textViewAccountNumber.getText().toString(),
//                                    _textViewIFSCCode.getText().toString(),
//                                    thumbImageDownloadUri
//                            )
//                    ).addOnSuccessListener(aVoid -> {
//                        Toast.makeText(getActivity(), "details upload successfully", Toast.LENGTH_SHORT).show();
//                        _navController.popBackStack(R.id.vendorFragment, false);
//                        _progressBar.setVisibility(View.GONE);
//                    }).addOnFailureListener(e -> {
//                        Toast.makeText(getContext(), "failed to upload task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        _progressBar.setVisibility(View.GONE);
//                    });
//                });
//            }
//        }).addOnFailureListener(e -> {
//            _progressBar.setVisibility(View.GONE);
//            Toast.makeText(getActivity(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        });
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (requestCode == PASSBOOK_CODE) {
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Log.e("BAnkDetail fragment ", imageUri + "");
                if (imageUri != null) {
                    _imageViewPassbook.setImageURI(imageUri);
                }
            }
        }
    }




}
