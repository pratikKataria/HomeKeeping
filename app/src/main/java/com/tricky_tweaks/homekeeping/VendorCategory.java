package com.tricky_tweaks.homekeeping;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorCategory extends Fragment {

    private NavController _navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _navController = Navigation.findNavController(view);
    }

    public VendorCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor_category, container, false);

        vendorCategorySelectorLayout(view);

        ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vendors").child(FirebaseAuth.getInstance().getUid());
        ref.child("Service").child("vendorService").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!dataSnapshot.getValue(String.class).isEmpty()) {
                        _navController.navigate(R.id.action_vendorCategory_to_vendorFragment);
                        dialog.cancel();
                    }
                } else {
                    Toast.makeText(getActivity(), "select service", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void vendorCategorySelectorLayout(View view) {
        final String[] categorySelected = {""};

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radioButtonElectrician = view.findViewById(R.id.rb_electrician);
        RadioButton radioButtonCarpainter = view.findViewById(R.id.rb_carpainter);
        RadioButton radioButtonPlumber = view.findViewById(R.id.rb_plumber);
        RadioButton radioButtonAirCoolerRepair = view.findViewById(R.id.rb_air_cooler_repair);
        RadioButton radioButtonAirCondRepair = view.findViewById(R.id.rb_air_condi_repair_store);

        MaterialButton mbNext = view.findViewById(R.id.vendor_category_next);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_electrician:
                    categorySelected[0] = radioButtonElectrician.getText().toString();
                    break;
                case R.id.rb_carpainter:
                    categorySelected[0] = radioButtonCarpainter.getText().toString();
                    break;
                case R.id.rb_plumber:
                    categorySelected[0] = radioButtonPlumber.getText().toString();
                    break;
                case R.id.rb_air_cooler_repair:
                    categorySelected[0] = radioButtonAirCoolerRepair.getText().toString();
                    break;
                case R.id.rb_air_condi_repair_store:
                    categorySelected[0] = radioButtonAirCondRepair.getText().toString();
                    break;
            }
        });

        mbNext.setOnClickListener(n -> {

            if (categorySelected[0].isEmpty()) {
                Toast.makeText(getActivity(), "select category", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> service = new HashMap<>();
            service.put("vendorService", categorySelected[0]);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vendors").child(FirebaseAuth.getInstance().getUid());
            ref.child("Service").updateChildren(service).addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "service added", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getActivity(), "failed to upload" + e.getMessage(), Toast.LENGTH_SHORT).show());

        });
    }
}
