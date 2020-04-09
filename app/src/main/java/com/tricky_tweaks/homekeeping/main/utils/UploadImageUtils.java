package com.tricky_tweaks.homekeeping.main.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class UploadImageUtils {

    Bitmap compressedImageFile;
    private OnImageUploadListener uploadListener;

    private String storagePath;
    private Context context;
    private ArrayList<String> uriList;

    public UploadImageUtils(String storagePath, Context context) {
        this.storagePath = storagePath;
        this.context = context;
        uriList = new ArrayList<>();
    }


    public interface OnImageUploadListener {
        void onFileNotFoundException(IOException xe);
        void onSuccessListener(String uri, int successCode);
        void onFailedListener(String message);
    }

    public void setOnUploadListener(OnImageUploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    public void uploadImage(File file, int code) {

        try {
            compressedImageFile = new Compressor(context)
                    .setQuality(30).compressToBitmap(file);

        } catch (IOException e) {
            uploadListener.onFileNotFoundException(e);
        }

        StorageReference storage = FirebaseStorage.getInstance().getReference(storagePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] thumbData = baos.toByteArray();

        UploadTask uploadTask = storage.child( getSaltString() + ".jpeg").putBytes(thumbData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                result.addOnSuccessListener(uri -> {
                    String thumbImageDownloadUri = uri.toString();
                    uploadListener.onSuccessListener(thumbImageDownloadUri, code);
                });
            }
        }).addOnFailureListener(e -> {
            uploadListener.onFailedListener(e.getMessage());
        });
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
