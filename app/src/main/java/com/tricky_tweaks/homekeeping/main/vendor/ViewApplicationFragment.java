package com.tricky_tweaks.homekeeping.main.vendor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.databinding.FragmentViewApplicationBinding;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

public class ViewApplicationFragment extends Fragment {

    private FragmentViewApplicationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        View view = inflater.inflate(R.layout.fragment_view_application, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_application, container, false);

        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        VendorDataModel vendorDataModel = (VendorDataModel) getArguments().getSerializable("dataModel");
        binding.setVendorDataModel(vendorDataModel);
        Log.e("View AppFragment", vendorDataModel.getPersonalDetailModel().toString());
    }
}
