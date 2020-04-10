package com.tricky_tweaks.homekeeping.main.bottom_navigation_fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tricky_tweaks.homekeeping.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    NavController _navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        MaterialButton materialButton = view.findViewById(R.id.moderator);
        materialButton.setOnClickListener(n -> {
//            _navController.navigate(R.id.action_profileFragment_to_adminFragment);
            showAlertDialog();
        });

        return view;
    }

    private void showAlertDialog() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.admin_dialog_layout, null);
        TextInputEditText password = view.findViewById(R.id.editTextPassword);
        MaterialButton verifyBtn = view.findViewById(R.id.id_mb_verify);
        MaterialButton cancelBtn = view.findViewById(R.id.id_mb_cancel);
        ProgressBar progressBar= view.findViewById(R.id.progressBar);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        verifyBtn.setOnClickListener(n -> {


            if (password.getText().toString().isEmpty()) {
                password.setText("should not be empty");
                password.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");
            reference.child("locker/password").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        if (password.getText().toString().equals(dataSnapshot.getValue(String.class))) {
                            progressBar.setVisibility(View.GONE);
                            _navController.navigate(R.id.action_profileFragment_to_adminFragment);
                            alertDialog.dismiss();
                        }else {
                            password.setText("");
                            password.setHint("password incorrect");
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        });

        cancelBtn.setOnClickListener(n -> {
            alertDialog.dismiss();
        });
    }

}
