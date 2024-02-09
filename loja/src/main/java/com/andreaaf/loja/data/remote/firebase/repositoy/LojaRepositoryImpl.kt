package com.andreaaf.loja.data.remote.firebase.repositoy

import android.net.Uri
import com.andreaaf.appifood.domain.model.Loja
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

    override suspend fun cadastrar(loja: Loja, uri: Uri): Boolean {
        uploadImagem(uri)
        return true
    }

    suspend fun uploadImagem(uri: Uri): Boolean {
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
                    .getReference("perfil")
                    .child(idUsuarioLogado)
                    .child( nomeFoto )
                    .putFile( uri )
                    //.downloadUrl
                    .addOnSuccessListener {uriImagem ->

                    }
            }
        }
        return true
    }

}