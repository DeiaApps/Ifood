package com.andreaaf.loja.data.remote.firebase.repositoy

import android.net.Uri
import com.andreaaf.appifood.domain.model.Loja

interface ILojaRepository {
    suspend fun cadastrar( loja: Loja, uri: Uri) : Boolean
}