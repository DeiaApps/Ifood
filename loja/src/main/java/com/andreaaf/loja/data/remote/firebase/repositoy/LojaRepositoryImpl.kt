package com.andreaaf.loja.data.remote.firebase.repositoy

import android.net.Uri
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.core.UIStatus
import com.andreaaf.loja.utils.FirebaseStorageConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class LojaRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ILojaRepository {

    override suspend fun cadastrar(loja: Loja, uri: Uri, retornoRequisicao: (UIStatus)-> Unit): Boolean {
        uploadImagem(uri, retornoRequisicao)
        return true
    }

    suspend fun uploadImagem(uri: Uri, retornoRequisicao: (UIStatus)-> Unit): Boolean {
        withContext( Dispatchers.IO){
            /*
            perfil
                + id
                    + foto
            * */
            val idUsuarioLogado = FirebaseAuth.getInstance().currentUser?.uid
            val nomeFoto = UUID.randomUUID().toString()
            if ( idUsuarioLogado != null ){
                storage
                    .getReference(FirebaseStorageConstants.PASTA_PERFIL)
                    .child(idUsuarioLogado)
                    .child( nomeFoto )
                    .putFile( uri )
                    //.downloadUrl
                    .addOnSuccessListener {task -> //uriImagem ->
                        val statusSucesso = UIStatus.Sucesso(true, listOf("andréa", "marco"))
                        retornoRequisicao.invoke(statusSucesso)
                    }.addOnFailureListener {
                        val statusErro = UIStatus.Erro(false)
                        retornoRequisicao.invoke(statusErro)
                    }
            }
        }
        return true
    }

}