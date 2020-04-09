package com.tricky_tweaks.homekeeping;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Homekeeping extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
