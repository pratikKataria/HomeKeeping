package com.tricky_tweaks.homekeeping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tricky_tweaks.homekeeping.databinding.FragmentAddCompanyActivityBinding;
import com.tricky_tweaks.homekeeping.model.Branch;
import com.tricky_tweaks.homekeeping.model.BranchRepresentative;
import com.tricky_tweaks.homekeeping.model.Company;
import com.tricky_tweaks.homekeeping.model.CompanyInfoModel;

public class AddCompanyFragment extends Fragment {

    private FragmentAddCompanyActivityBinding binding;



    //company
    private TextInputEditText _textViewCompName;
    private TextInputEditText _textViewCompEmail;

    //branch
    private TextInputEditText _textViewBranchAdd;
    private TextInputEditText _textViewBranchPincode;
    private TextInputEditText _textViewBranchEmail;
    private TextInputEditText _textViewBranchCity;
    private TextInputEditText _textViewBranchState;
    private TextInputEditText _textViewBranchLocation;

    //representative
    private TextInputEditText _textViewRepName;
    private TextInputEditText _textViewRepPhone;
    private TextInputEditText _textViewRepEmail;
    private TextInputEditText _textViewRepPosition;
    private MaterialButton _mbSave;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_add_company_activity,
                container,
                false
        );

        //company
        _textViewCompName = binding.addCompName;
        _textViewCompEmail = binding.addCompEmail;

        //branch
        _textViewBranchAdd = binding.addBranchName;
        _textViewBranchPincode = binding.addAreaPincode;
        _textViewBranchEmail = binding.addCompEmail;
        _textViewBranchCity = binding.addCity;
        _textViewBranchState = binding.addState;
        _textViewBranchLocation = binding.addLocation;

        //representative
        _textViewRepName = binding.addRepName;
        _textViewRepPhone = binding.addRepPhone;
        _textViewRepEmail = binding.addRepPhone;
        _textViewRepPosition = binding.addRepPosition;

        _mbSave = binding.addMbSave;

        _mbSave.setOnClickListener(n -> {
            //company
            if (_textViewCompName.getText().toString().isEmpty()) {
                _textViewCompName.setError("should not be empty");
                _textViewCompName.requestFocus();
                return;
            }

            if (_textViewCompEmail.getText().toString().isEmpty()) {
                _textViewCompEmail.setError("should not be empty");
                _textViewCompEmail.requestFocus();
                return;
            }

            //branch
            if (_textViewBranchAdd.getText().toString().isEmpty()) {
                _textViewBranchAdd.setError("should not be empty");
                _textViewBranchAdd.requestFocus();
                return;
            }

            if (_textViewBranchPincode.getText().toString().isEmpty()) {
                _textViewBranchPincode.setError("should not be empty");
                _textViewBranchPincode.requestFocus();
                return;
            }

            if (_textViewBranchEmail.getText().toString().isEmpty()) {
                _textViewBranchEmail.setError("should not be empty");
                _textViewBranchEmail.requestFocus();
                return;
            }

            if (_textViewBranchCity.getText().toString().isEmpty()) {
                _textViewBranchCity.setError("should not be empty");
                _textViewBranchCity.requestFocus();
                return;
            }

            if (_textViewBranchState.getText().toString().isEmpty()) {
                _textViewBranchState.setError("should not be empty");
                _textViewBranchState.requestFocus();
                return;
            }

            //Representative
            if (_textViewRepName.getText().toString().isEmpty()) {
                _textViewRepName.setError("should not be empty");
                _textViewRepName.requestFocus();
                return;
            }

            if (_textViewRepEmail.getText().toString().isEmpty()) {
                _textViewRepEmail.setError("should not be empty");
                _textViewRepEmail.requestFocus();
                return;
            }

            if (_textViewRepPosition.getText().toString().isEmpty()) {
                _textViewRepPosition.setError("should not be empty");
                _textViewRepPosition.requestFocus();
                return;
            }

            if (_textViewRepPhone.getText().toString().isEmpty()) {
                _textViewRepPhone.setError("should not be empty");
                _textViewRepPhone.requestFocus();
                return;
            }

            saveCompanyInfo();

        });

        return binding.getRoot();
    }

    private void saveCompanyInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CompanyInfo");
        String key = reference.push().getKey();

        Company company = new Company(
          ""+ _textViewCompName.getText().toString(),
          ""+ _textViewCompEmail.getText().toString()
        );

        Branch branch = new Branch(
                "" + _textViewBranchAdd.getText().toString(),
                "" + "",
                "" + _textViewBranchEmail.getText().toString(),
                "" + _textViewBranchCity.getText().toString(),
                "" + _textViewBranchState.getText().toString(),
                "" +_textViewBranchLocation.getText().toString(),
                "" + _textViewBranchPincode.getText().toString()
        );

        BranchRepresentative representative = new BranchRepresentative(
                "" + _textViewRepName.getText().toString(),
                ""+ _textViewRepPhone.getText().toString(),
                ""+ _textViewRepEmail.getText().toString(),
                ""+ _textViewRepPosition.getText().toString()
        );

        CompanyInfoModel companyInfo = new CompanyInfoModel(

                branch,
                company,
                representative

        );

        reference.child(key).setValue(
                companyInfo
        ).addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "company info added", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
            Log.e("AddCompanyFragment", e.getMessage());
            Toast.makeText(getActivity(), "fail to add", Toast.LENGTH_SHORT).show();
        });

    }
}
