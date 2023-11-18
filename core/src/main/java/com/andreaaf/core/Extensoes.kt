package com.andreaaf.core

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

//ViewExtension (ViewExtensoes, EditTextExtensoes) pode ter diversas extensões
//pode criar de acordo com a necessidade
fun View.esconderTeclado(){

    val inputMethodManager =
    context.getSystemService( Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if ( inputMethodManager.isAcceptingText )//Se o teclado está na tela (está aceitando digitação)
        inputMethodManager.hideSoftInputFromWindow( windowToken, 0 )
}

fun View.exibirTecladoEFoco(){

    if( requestFocus() ){
        val inputMethodManager =
            context.getSystemService( Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput( this, 0)
    }
}

fun Activity.exibirMensagem(textoMensagem: String ){
    Toast.makeText(this, textoMensagem, Toast.LENGTH_SHORT).show()
}
