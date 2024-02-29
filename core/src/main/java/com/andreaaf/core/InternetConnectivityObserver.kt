package com.andreaaf.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

object InternetConnectivityObserver {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            internetConnectivity?.onConnected()
        }
        
        override fun onLost(network: Network) {
            super.onLost(network)
            internetConnectivity?.onDisconnected()
        }
    }

    private var internetConnectivity: InternetConnectivity? = null
    private var connectivityManager: ConnectivityManager? = null

    fun instance(context: Context): InternetConnectivityObserver {
        connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        return InternetConnectivityObserver
    }

    fun setCallback(internetConnectivity: InternetConnectivity): InternetConnectivityObserver {
        this.internetConnectivity = internetConnectivity
        return InternetConnectivityObserver
    }

    fun build() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager?.requestNetwork(networkRequest, networkCallback)
    }
}