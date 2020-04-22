package com.tricky_tweaks.homekeeping.admin.adapter;

import android.app.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.admin.AppliedVendorsFragment;
import com.tricky_tweaks.homekeeping.admin.viewholder.HireVendorViewHolder;
import com.tricky_tweaks.homekeeping.admin.viewholder.VendorCardViewHolder;
import com.tricky_tweaks.homekeeping.databinding.CardviewHireVendorBinding;
import com.tricky_tweaks.homekeeping.databinding.CardviewVendorsBinding;
import com.tricky_tweaks.homekeeping.model.vendors.Metadata;
import com.tricky_tweaks.homekeeping.model.vendors.PersonalDetailModel;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

import java.util.ArrayList;

public class VendorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  int currView;
    private Context context;
    private ArrayList<VendorDataModel> vendorDataModelList;

    private static final int VENDOR_APPLICATION_VIEW = 1923;
    private static final int VENDOR_HIRE_VIEW = 1932;
    private static final int EMPTY_VIEW = 9999;

    public VendorRecyclerAdapter(Context context, ArrayList<VendorDataModel> vendorDataModelList, int currView) {
        this.context = context;
        this.vendorDataModelList = vendorDataModelList;
        this.currView = currView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == VENDOR_APPLICATION_VIEW) {
            CardviewVendorsBinding binding = CardviewVendorsBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            holder = new VendorCardViewHolder(binding);
        } else if (viewType == VENDOR_HIRE_VIEW) {
            CardviewHireVendorBinding binding = CardviewHireVendorBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            holder = new HireVendorViewHolder(binding);
        } else {
            holder = new EmptyViewHolder(new View(context));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VendorCardViewHolder) {
            VendorCardViewHolder vendorCardViewHolder = (VendorCardViewHolder) holder;
            PersonalDetailModel personalDetailModel = vendorDataModelList.get(position).getPersonalDetailModel();
            vendorCardViewHolder.setBinding(personalDetailModel, vendorDataModelList.get(position));

        } else if (holder instanceof HireVendorViewHolder) {
            HireVendorViewHolder viewHolder =(HireVendorViewHolder) holder;
            PersonalDetailModel personalDetailModel = vendorDataModelList.get(position).getPersonalDetailModel();
            viewHolder.setBinding(personalDetailModel);
        }

    }

    @Override
    public int getItemCount() {
        //todo show layout count 1 when no data is present
        return vendorDataModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (vendorDataModelList.get(position) instanceof VendorDataModel) {

            if (currView == VENDOR_APPLICATION_VIEW) {
                return VENDOR_APPLICATION_VIEW;
            } else {
                return VENDOR_HIRE_VIEW;
            }

        } else {
            //todo show empty view
            return EMPTY_VIEW;
        }
    }

    static class EmptyViewHolder extends  RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
