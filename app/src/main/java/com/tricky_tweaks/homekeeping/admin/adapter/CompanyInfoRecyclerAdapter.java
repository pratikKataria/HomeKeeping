package com.tricky_tweaks.homekeeping.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.tricky_tweaks.homekeeping.databinding.CardViewCompanyInfoBinding;
import com.tricky_tweaks.homekeeping.model.company.Branch;
import com.tricky_tweaks.homekeeping.model.company.Company;
import com.tricky_tweaks.homekeeping.model.company.CompanyInfoModel;

import java.util.ArrayList;
import java.util.List;

public class CompanyInfoRecyclerAdapter extends RecyclerView.Adapter<CompanyInfoRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CompanyInfoModel> list;

    public CompanyInfoRecyclerAdapter(Context context, ArrayList<CompanyInfoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardViewCompanyInfoBinding binding =
                CardViewCompanyInfoBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setBinding(list.get(position).getBranch(),
                          list.get(position).getCompany(),
                new ArrayList<>(list.get(position).getMetadata().getServices().values()));
//        Log.e("adapter", list.get(position).getBranch().getBranchName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardViewCompanyInfoBinding binding;
        ChipGroup group;

        public ViewHolder(@NonNull CardViewCompanyInfoBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            group = itemView.chipGroup;
        }

        void setBinding(Branch model, Company company, List<String> chipItems) {
            binding.setBranch(model);
            binding.setCompany(company);
            binding.setChipItems(chipItems);
            binding.executePendingBindings();
        }
    }
}
