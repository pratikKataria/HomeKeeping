package com.tricky_tweaks.homekeeping.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.admin.ViewCompaniesFragment;
import com.tricky_tweaks.homekeeping.databinding.CardViewCompanyInfoBinding;
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
        holder.setBinding(list.get(position),
                new ArrayList<>(list.get(position).getMetadata().getServices().values()));
        NavHostFragment navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.activity_main_nav_host);
        Fragment currFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        holder.binding.setICompanyApplication((ViewCompaniesFragment) currFragment);
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

        void setBinding(CompanyInfoModel companyInfo, List<String> chipItems) {
            binding.setCompanyInfo(companyInfo);
            binding.setChipItems(chipItems);
            binding.executePendingBindings();
        }
    }
}
