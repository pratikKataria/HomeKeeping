package com.tricky_tweaks.homekeeping.admin.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tricky_tweaks.homekeeping.databinding.CardviewHireVendorBinding;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;
import com.tricky_tweaks.homekeeping.model.vendors.PersonalDetailModel;

public class HireVendorViewHolder extends RecyclerView.ViewHolder {

    CardviewHireVendorBinding binding;

    public HireVendorViewHolder(@NonNull CardviewHireVendorBinding itemView) {
        super(itemView.getRoot());
        binding = itemView;
    }

    public void setBinding(PersonalDetailModel data) {
        binding.setPersonalInfo(data);
        binding.executePendingBindings();
    }

}
