package com.tricky_tweaks.homekeeping.main.vendor;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.button.MaterialButton;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.main.utils.SharedPrefsUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorCategory extends Fragment {

    private NavController _navController;
    private MaterialButton nextBtn;
    private MaterialButton updateBtn;
    private AlertDialog alertDialog;


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

        updateBtn = view.findViewById(R.id.vendor_category_update);

//        ProgressDialog dialog; = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
//        dialog.setMessage("Loading..");
//        dialog.setCancelable(false);
//        dialog.show();

        String getService = SharedPrefsUtils.getStringPreference(getActivity(), "SERVICE_SELECTED", 0);
        if (getService == null) {
            Toast.makeText(getActivity(), "select service", Toast.LENGTH_SHORT).show();
        } else {
            showAlertDialog();
        }


        final String[] categorySelected = {""};

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radioButtonElectrician = view.findViewById(R.id.rb_electrician);
        RadioButton radioButtonCarpainter = view.findViewById(R.id.rb_carpainter);
        RadioButton radioButtonPlumber = view.findViewById(R.id.rb_plumber);
        RadioButton radioButtonAirCoolerRepair = view.findViewById(R.id.rb_air_cooler_repair);
        RadioButton radioButtonAirCondRepair = view.findViewById(R.id.rb_air_condi_repair_store);

        nextBtn = view.findViewById(R.id.vendor_category_next);

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

        nextBtn.setOnClickListener(n -> {

            if (categorySelected[0].isEmpty()) {
                Toast.makeText(getActivity(), "select category", Toast.LENGTH_SHORT).show();
                return;
            }
            uploadSelectedService(categorySelected[0]);
        });

        return view;
    }

    public void uploadSelectedService(String selectedService) {
        SharedPrefsUtils.setStringPreference(getActivity(), "SERVICE_SELECTED", selectedService);
        _navController.navigate(R.id.action_vendorCategory_to_vendorFragment);
    }


    private void showAlertDialog() {
        Log.e("vendor category", "dialog shownd");
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("Vendor Application")
                    .setMessage("would you like change category")
                    .setCancelable(false)
                    .setPositiveButton("next", (dialog, which) -> _navController.navigate(R.id.action_vendorCategory_to_vendorFragment)).setNegativeButton("cancel", (dialog, which) -> _navController.popBackStack(R.id.homeFragment, false))
                    .setNeutralButton("change", (dialog, which) -> {
                        nextBtn.setText("update");
                    });

            alertDialog = builder.create();
            alertDialog.show();
        }

    }


}
