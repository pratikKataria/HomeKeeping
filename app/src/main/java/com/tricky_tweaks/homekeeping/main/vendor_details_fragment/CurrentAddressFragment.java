package com.tricky_tweaks.homekeeping.main.vendor_details_fragment;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.model.CurrentAddressModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentAddressFragment extends Fragment {

    private TextInputEditText _textViewHouseNo;
    private TextInputEditText _textViewStreet;
    private TextInputEditText _textViewPincode;
    private TextInputEditText _textViewCity;
    private TextInputEditText _textViewState;

    private ProgressBar _progressBar;
    private MaterialButton _saveBtn;

    private NavController _navController;

    public CurrentAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_address, container, false);


        _textViewHouseNo = view.findViewById(R.id.add_house_no);
        _textViewStreet = view.findViewById(R.id.add_street);
        _textViewPincode = view.findViewById(R.id.add_area_pincode);
        _textViewCity = view.findViewById(R.id.add_city);
        _textViewState = view.findViewById(R.id.add_state);
        _progressBar = view.findViewById(R.id.progressBar);
        _saveBtn = view.findViewById(R.id.add_mb_save);

        _saveBtn.setOnClickListener(n -> {

            if (_textViewHouseNo.getText().toString().isEmpty()) {
                _textViewHouseNo.setError("should not be empty");
                _textViewHouseNo.requestFocus();
                return;
            }

            if (_textViewStreet.getText().toString().isEmpty()) {
                _textViewStreet.setError("should not be empty");
                _textViewStreet.requestFocus();
                return;
            }

            if (_textViewPincode.getText().toString().isEmpty()) {
                _textViewPincode.setError("should not be empty");
                _textViewPincode.requestFocus();
                return;
            }

            if (_textViewCity.getText().toString().isEmpty()) {
                _textViewCity.setError("should not be empty");
                _textViewCity.requestFocus();
                return;
            }

            if (_textViewState.getText().toString().isEmpty()) {
                _textViewState.setError("should not be empty");
                _textViewState.requestFocus();
                return;
            }

            uploadAddress();

        });

        return view;
    }

    private void uploadAddress() {
        _progressBar.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vendors");
        reference.child(FirebaseAuth.getInstance().getUid() + "/CurrentAddress").setValue(
                new CurrentAddressModel(
                        _textViewHouseNo.getText().toString(),
                        _textViewStreet.getText().toString(),
                        _textViewPincode.getText().toString(),
                        _textViewCity.getText().toString(),
                        _textViewState.getText().toString()
                )
        ).addOnSuccessListener(aVoid -> {
            _progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(),"upload successfully", Toast.LENGTH_SHORT).show();
            _navController.popBackStack(R.id.vendorFragment,false);
        }).addOnFailureListener(e -> {
            _progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(),"upload failed : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
