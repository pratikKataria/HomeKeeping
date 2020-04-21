package com.tricky_tweaks.homekeeping.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.admin.AppliedVendorsFragment;
import com.tricky_tweaks.homekeeping.databinding.CardviewVendorsBinding;
import com.tricky_tweaks.homekeeping.model.vendors.Metadata;
import com.tricky_tweaks.homekeeping.model.vendors.PersonalDetailModel;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

import java.util.ArrayList;

public class VendorApplicationRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<VendorDataModel> vendorDataModelList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public VendorApplicationRecyclerAdapter(Context context, ArrayList<VendorDataModel> vendorDataModelList) {
        this.context = context;
        this.vendorDataModelList = vendorDataModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        CardviewVendorsBinding binding = CardviewVendorsBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        holder = new VendorCardViewHolder(binding, listener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VendorCardViewHolder vendorCardViewHolder = (VendorCardViewHolder) holder;

        PersonalDetailModel personalDetailModel = vendorDataModelList.get(position).getPersonalDetailModel();

        vendorCardViewHolder.setBinding(personalDetailModel);

        NavHostFragment hostFragment = (NavHostFragment) ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.activity_main_nav_host);
        Fragment currFragment = hostFragment.getChildFragmentManager().getFragments().get(0);
        vendorCardViewHolder.cardViewBinding.setVendorData(vendorDataModelList.get(position));
        vendorCardViewHolder.cardViewBinding.setIVendorApplication((AppliedVendorsFragment)currFragment);

        vendorCardViewHolder.setStatus(vendorDataModelList.get(position).getMetadata());
        vendorCardViewHolder.cardViewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return vendorDataModelList.size();
    }


    static class VendorCardViewHolder extends RecyclerView.ViewHolder {

        MaterialButton statusTextView;
        CardviewVendorsBinding cardViewBinding;

        public VendorCardViewHolder(@NonNull CardviewVendorsBinding itemView, OnItemClickListener listener) {
            super(itemView.getRoot());
            cardViewBinding = itemView;

            statusTextView = cardViewBinding.status;
            itemView.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });

        }

        void setBinding(PersonalDetailModel personalInfo) {
            cardViewBinding.setPersonalInfo(personalInfo);
        }

        void setStatus(Metadata metadata) {
            statusTextView.setText(metadata.getStatus());
        }

    }

}
