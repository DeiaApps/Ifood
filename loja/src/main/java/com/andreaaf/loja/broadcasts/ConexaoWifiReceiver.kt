package com.andreaaf.loja.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log

class ConexaoWifiReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if ( intent.action == WifiManager.WIFI_STATE_CHANGED_ACTION){

            //val servicoConexao = context.getSystemService( ConnectivityManager::class.java )
            val statusWifi = intent.getIntExtra( WifiManager.EXTRA_WIFI_STATE, 4 )
            if (statusWifi == WifiManager.WIFI_STATE_ENABLED){
                Log.i("broadcast_android", "wifi habilitada")
            }else if (statusWifi == WifiManager.WIFI_STATE_DISABLED){
                Log.i("broadcast_android", "wifi desabilitada")
            }
        }
    }
}
