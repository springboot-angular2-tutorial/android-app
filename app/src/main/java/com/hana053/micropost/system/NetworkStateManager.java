package com.hana053.micropost.system;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class NetworkStateManager {

    private final ConnectivityManager connectivityManager;

    NetworkStateManager(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    public boolean isConnectedOrConnecting() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo.isConnectedOrConnecting();
    }
}
