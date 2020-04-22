package com.tricky_tweaks.homekeeping.binding;

import android.view.LayoutInflater;

import androidx.databinding.BindingAdapter;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.tricky_tweaks.homekeeping.R;

import java.util.List;

public class ChipBindingAdapter {
    @BindingAdapter("chipItemsList")
    public static void chipGroupBinding(ChipGroup chipGroup, List<String> listItems) {
        if (listItems == null) {
            return;
        }
        for (String string : listItems) {
            Chip chip = (Chip) LayoutInflater.from(chipGroup.getContext()).inflate(R.layout.chip_layout, null);
            chip.setText(string);
            chipGroup.addView(chip);
        }
    }
}
