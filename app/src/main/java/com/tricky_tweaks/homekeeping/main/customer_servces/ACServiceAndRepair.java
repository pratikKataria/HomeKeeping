package com.tricky_tweaks.homekeeping.main.customer_servces;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tricky_tweaks.homekeeping.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ACServiceAndRepair extends Fragment {

    public ACServiceAndRepair() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a_c_service_and_repair, container, false);
    }
}
