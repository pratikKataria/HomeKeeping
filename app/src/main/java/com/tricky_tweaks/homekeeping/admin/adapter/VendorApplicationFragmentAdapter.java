package com.tricky_tweaks.homekeeping.admin.adapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tricky_tweaks.homekeeping.model.VendorDataModel;

import java.util.ArrayList;
import java.util.List;

public class VendorApplicationFragmentAdapter {

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

}
