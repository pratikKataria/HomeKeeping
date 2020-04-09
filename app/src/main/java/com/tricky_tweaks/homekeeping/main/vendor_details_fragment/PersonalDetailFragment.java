package com.tricky_tweaks.homekeeping.main.vendor_details_fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.main.utils.SharedPrefsUtils;
import com.tricky_tweaks.homekeeping.model.PersonalDetailModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PersonalDetailFragment extends Fragment {

    private TextInputEditText _textViewName;
    private TextInputEditText _textViewAadhaarNo;
    private TextInputEditText _textViewPancardNo;
    private TextInputEditText _textViewDob;
    private TextInputEditText _textViewParentName;

    private RadioGroup _radioGroup;
    private RadioButton _radioButtonMale;
    private RadioButton _radioButtonFemale;
    private RadioButton _radioButtonOther;

    private MaterialButton _saveBtn;

    private ProgressBar _progressBar;

    private String gender = "";

    private NavController _navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_detail, container, false);

        _textViewName = view.findViewById(R.id.pd_name);
        _textViewAadhaarNo = view.findViewById(R.id.pd_adhar_no);
        _textViewPancardNo = view.findViewById(R.id.pd_pan_no);
        _textViewDob = view.findViewById(R.id.date_picker);
        _textViewParentName = view.findViewById(R.id.pd_father_name);

        _radioGroup = view.findViewById(R.id.radioGroup);
        _radioButtonMale = view.findViewById(R.id.rb_male);
        _radioButtonFemale = view.findViewById(R.id.rb_female);
        _radioButtonOther = view.findViewById(R.id.rb_other);

        _saveBtn = view.findViewById(R.id.pd_mb_save);

        _progressBar = view.findViewById(R.id.progressBar);

        _radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_male:
                    gender = _radioButtonMale.getText().toString();
                    break;
                case R.id.rb_female:
                    gender = _radioButtonFemale.getText().toString();
                    break;
                case R.id.rb_other:
                    gender = _radioButtonOther.getText().toString();
                    break;
            }
        });

        final Calendar cal = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view1, year, month, dayOfMonth) -> {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            _textViewDob.setText(
                    new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime())
            );
        };

        _textViewDob.setOnClickListener(n -> {
            new DatePickerDialog(
                    getActivity(), date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });



        _saveBtn.setOnClickListener(n -> {


            if (_textViewName.getText().toString().isEmpty()) {
                _textViewName.setError("should not be empty");
                _textViewName.requestFocus();
                return;
            }

            if (_textViewAadhaarNo.getText().toString().isEmpty()) {
                _textViewAadhaarNo.setError("should not be empty");
                _textViewAadhaarNo.requestFocus();
                return;
            }

            if (_textViewPancardNo.getText().toString().isEmpty()) {
                _textViewPancardNo.setError("should not be empty");
                _textViewPancardNo.requestFocus();
                return;
            }

            if (_textViewDob.getText().toString().isEmpty()) {
                _textViewDob.setError("should not be empty");
                _textViewDob.requestFocus();
                return;
            }

            if (_textViewParentName.getText().toString().isEmpty()) {
                _textViewParentName.setError("should not be empty");
                _textViewParentName.requestFocus();
                return;
            }

            if (gender.isEmpty()) {
                Toast.makeText(getActivity(), "select gender", Toast.LENGTH_SHORT).show();
                return;
            }

//            uploadPersonalDetails();

            savedToSharedPrefs();

        });

        return view;
    }

    private void savedToSharedPrefs() {
        PersonalDetailModel personalDetailModel = new PersonalDetailModel(
                        _textViewName.getText().toString(),
                        _textViewAadhaarNo.getText().toString(),
                        _textViewPancardNo.getText().toString(),
                        _textViewDob.getText().toString(),
                        gender,
                        _textViewParentName.getText().toString()
        );

        SharedPrefsUtils.setStringPreference(getActivity(), "personalDetailModel", new Gson().toJson(personalDetailModel));
        _navController.popBackStack(R.id.vendorFragment, false);
    }


//    private void uploadPersonalDetails() {
//        _progressBar.setVisibility(View.VISIBLE);
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vendors");
//        reference.child(FirebaseAuth.getInstance().getUid() + "/personalDetailModel").setValue(
//                new PersonalDetailModel(
//                        _textViewName.getText().toString(),
//                        _textViewAadhaarNo.getText().toString(),
//                        _textViewPancardNo.getText().toString(),
//                        _textViewDob.getText().toString(),
//                        gender,
//                        _textViewParentName.getText().toString()
//                )
//        ).addOnSuccessListener(aVoid -> {
//            _progressBar.setVisibility(View.GONE);
//            _navController.popBackStack(R.id.vendorFragment, false);
//            Toast.makeText(getActivity(), "upload successfully", Toast.LENGTH_SHORT).show();
//        }).addOnFailureListener(e -> {
//            _progressBar.setVisibility(View.GONE);
//            Toast.makeText(getActivity(), "upload failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        });
//    }


}
