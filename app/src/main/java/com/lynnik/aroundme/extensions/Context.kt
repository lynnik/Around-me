package com.lynnik.aroundme.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.hasNetworkConnection(): Boolean {
    val activeNetworkInfo = getConnectivityManager().activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun Context.getConnectivityManager() =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager