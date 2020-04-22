package com.tricky_tweaks.homekeeping.admin.viewholder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.admin.AppliedVendorsFragment;
import com.tricky_tweaks.homekeeping.databinding.CardviewVendorsBinding;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;
import com.tricky_tweaks.homekeeping.model.vendors.PersonalDetailModel;

public class VendorCardViewHolder extends RecyclerView.ViewHolder {

    CardviewVendorsBinding cardViewBinding;

    public VendorCardViewHolder(@NonNull CardviewVendorsBinding itemView) {
        super(itemView.getRoot());
        cardViewBinding = itemView;
    }

    public void setBinding(PersonalDetailModel personalInfo, VendorDataModel vendorDataModele) {
        cardViewBinding.setPersonalInfo(personalInfo);
        cardViewBinding.setVendorData(vendorDataModele);
        NavHostFragment hostFragment = (NavHostFragment) ((AppCompatActivity)cardViewBinding.getRoot().getContext()).getSupportFragmentManager().findFragmentById(R.id.activity_main_nav_host);
        Fragment currFragment = hostFragment.getChildFragmentManager().getFragments().get(0);
        cardViewBinding.setIVendorApplication((AppliedVendorsFragment)currFragment);
        cardViewBinding.executePendingBindings();
    }


}

