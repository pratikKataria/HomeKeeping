package com.tricky_tweaks.homekeeping.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.button.MaterialButton;
import com.tricky_tweaks.homekeeping.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleServiceFragment extends Fragment implements View.OnClickListener {

    ArrayList<String> checkedItemList;

    public MultipleServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_service, container, false);

        checkedItemList = new ArrayList<>();
        LinearLayout linearLayout = view.findViewById(R.id.linearLayout);
        MaterialButton nextBtn = view.findViewById(R.id.nextBtn);

        String[] services = getActivity().getResources().getStringArray(R.array.customer_services);

        for (int i = 0; i < services.length; i++) {
            CheckBox cb = new CheckBox(getContext());
            cb.setText("" + services[i]);
            CompoundButtonCompat.setButtonTintList(cb, ContextCompat.getColorStateList(getContext(), R.color.colorBlack));
            cb.setId(i + 6);
            cb.setOnClickListener(this);
            linearLayout.addView(cb);
        }

        nextBtn.setOnClickListener(
                n -> {

                    if (checkedItemList.size() <= 0) {
                        Toast.makeText(getActivity(), "select service", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("SERVICES_SELECTED", checkedItemList);
                    Navigation.findNavController(getActivity(), R.id.activity_main_nav_host).navigate(R.id.action_multipleServiceFragment_to_addCompanyFragment, bundle);
                });

        return view;
    }

    @Override
    public void onClick(View v) {
        CheckBox box =  v.findViewById(v.getId());
        if (box.isChecked()) {
            checkedItemList.add(box.getText().toString());
        } else {
            checkedItemList.remove(box.getText().toString());
        }
    }
}
