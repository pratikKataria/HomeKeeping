package com.tricky_tweaks.homekeeping.binding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tricky_tweaks.homekeeping.admin.adapter.CompanyInfoRecyclerAdapter;
import com.tricky_tweaks.homekeeping.admin.adapter.VendorApplicationRecyclerAdapter;
import com.tricky_tweaks.homekeeping.model.company.CompanyInfoModel;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewBindingAdapter {

    @BindingAdapter("vendorApplicationList")
    public static void setList(RecyclerView recyclerView , List<VendorDataModel> list) {

        if (list == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false));
        }
        VendorApplicationRecyclerAdapter adapter = (VendorApplicationRecyclerAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new VendorApplicationRecyclerAdapter(recyclerView.getContext(), (ArrayList<VendorDataModel>) list);
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("branchList")
    public static void setBranchList(RecyclerView recyclerView, List<CompanyInfoModel> list) {
        if (list == null) {
            return;
        }

        CompanyInfoRecyclerAdapter adapter = (CompanyInfoRecyclerAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new CompanyInfoRecyclerAdapter(recyclerView.getContext(), (ArrayList<CompanyInfoModel>) list);
            recyclerView.setAdapter(adapter);
        }
    }

}
