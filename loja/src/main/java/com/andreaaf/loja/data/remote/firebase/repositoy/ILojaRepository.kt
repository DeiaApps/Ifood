package com.andreaaf.loja.data.remote.firebase.repositoy

import android.net.Uri
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.core.UIStatus

interface ILojaRepository {
    suspend fun cadastrar( loja: Loja, uri: Uri, retornoRequisicao: (UIStatus<String>)-> Unit)
}

