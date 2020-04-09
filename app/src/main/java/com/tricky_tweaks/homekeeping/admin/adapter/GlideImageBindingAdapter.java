package com.tricky_tweaks.homekeeping.admin.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tricky_tweaks.homekeeping.R;

public class GlideImageBindingAdapter {
    
    @BindingAdapter("imageResourceAdapter")
    public static void setImageResource(ImageView _imageView, String src) {
        Context  context = _imageView.getContext();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(context).load(src).into(_imageView);
    }
    
}
