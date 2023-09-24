package com.andreaaf.appifood.helper


import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.andreaaf.appifood.R

class AlertaMensagem(
    private val activity: Activity
) {
    var alertDialog: AlertDialog? = null

    //Converter xml para view
    val carregandoView = activity.layoutInflater.inflate(
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
        alertDialog?.dismiss()
    }
}