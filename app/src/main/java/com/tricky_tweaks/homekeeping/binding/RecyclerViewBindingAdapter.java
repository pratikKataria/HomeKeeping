package com.tricky_tweaks.homekeeping.binding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tricky_tweaks.homekeeping.admin.adapter.CompanyInfoRecyclerAdapter;
import com.tricky_tweaks.homekeeping.admin.adapter.VendorRecyclerAdapter;
import com.tricky_tweaks.homekeeping.model.company.CompanyInfoModel;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewBindingAdapter {

    public static final  int VENDOR_APPLICATION_VIEW = 1923;
    public static final int  VENDOR_HIRE_VIEW = 1932;

    @BindingAdapter("vendorApplicationList")
    public static void setList(RecyclerView recyclerView , List<VendorDataModel> list) {

        if (list == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false));
        }
        VendorRecyclerAdapter adapter = (VendorRecyclerAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new VendorRecyclerAdapter(recyclerView.getContext(), (ArrayList<VendorDataModel>) list, VENDOR_APPLICATION_VIEW);
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

    @BindingAdapter("vendorsList")
    public static void setVendorsList(RecyclerView recyclerView, List<VendorDataModel> list) {
        if (list == null) {
            return;
        }

        VendorRecyclerAdapter adapter = (VendorRecyclerAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new VendorRecyclerAdapter(recyclerView.getContext(), (ArrayList<VendorDataModel>) list, VENDOR_HIRE_VIEW);
            recyclerView.setAdapter(adapter);
        }

    }

}
