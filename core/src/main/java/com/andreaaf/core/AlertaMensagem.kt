package com.andreaaf.core

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog

class AlertaMensagem(
    private val activity: Activity
) {
    var alertDialog: AlertDialog? = null

    //Converter xml para view
    val carregandoView: View = activity.layoutInflater.inflate(
        R.layout.layout_carregando, null
    )

    fun exibir( textoMensagem: String ){
        val alertBuider = AlertDialog
            .Builder( activity )
            .setTitle( textoMensagem )
            .setView( carregandoView )
            .setCancelable(false)

        alertDialog = alertBuider.create()
        alertDialog?.show()
    }

    fun fechar(){
        alertDialog?.setOnDismissListener {
            val viewGroup = carregandoView.parent as ViewGroup
            viewGroup.removeView( carregandoView )
        }
        alertDialog?.dismiss()
    }
}