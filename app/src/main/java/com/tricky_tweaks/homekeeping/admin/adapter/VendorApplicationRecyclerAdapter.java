package com.tricky_tweaks.homekeeping.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tricky_tweaks.homekeeping.R;
import com.tricky_tweaks.homekeeping.model.PersonalDetailModel;
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_vendors, parent, false);

        holder = new VendorCardViewHolder(view, listener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VendorCardViewHolder vendorCardViewHolder = (VendorCardViewHolder) holder;

        PersonalDetailModel personalDetailModel = vendorDataModelList.get(position).getPersonalDetailModel();

        vendorCardViewHolder.setCardView(
                personalDetailModel.getName(),
                personalDetailModel.getAadharNo(),
                personalDetailModel.getPanNo(),
                personalDetailModel.getGender(),
                personalDetailModel.getDob(),
                personalDetailModel.getFatherName());
    }

    @Override
    public int getItemCount() {
        return vendorDataModelList.size();
    }


    static class VendorCardViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView aadharNoTextView;
        TextView panNoTextView;
        TextView genderTextView;
        TextView dobTextView;
        TextView fatherNameTextView;

        public VendorCardViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name);
            aadharNoTextView = itemView.findViewById(R.id.pd_aadhaar_card_no);
            panNoTextView = itemView.findViewById(R.id.pd_pan_no);
            genderTextView = itemView.findViewById(R.id.pd_gender);
            dobTextView = itemView.findViewById(R.id.pd_dob);
            fatherNameTextView = itemView.findViewById(R.id.pd_f_name);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void setCardView(
                String name,
                String aadharNo,
                String panNo,
                String gender,
                String dob,
                String fatherName
        ) {
            nameTextView.setText(name);
            aadharNoTextView.setText(aadharNo);
            panNoTextView.setText(panNo);
            genderTextView.setText(gender);
            dobTextView.setText(dob);
            fatherNameTextView.setText(fatherName);
        }
    }

}