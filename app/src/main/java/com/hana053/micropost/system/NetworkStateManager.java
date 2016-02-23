package com.hana053.micropost.system;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NetworkStateManager {

    private final ConnectivityManager connectivityManager;

    public boolean isConnectedOrConnecting() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo.isConnectedOrConnecting();
    }
}
