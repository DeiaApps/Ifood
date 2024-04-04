package com.andreaaf.loja.data.remote.firebase.repositoy

import android.net.Uri
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.core.UIStatus
import com.andreaaf.loja.utils.FirebaseStorageConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class LojaRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ILojaRepository {

    override suspend fun cadastrar(loja: Loja, uri: Uri, retornoRequisicao: (UIStatus<String>)-> Unit) {
        val urlImagem = uploadImagem(uri, retornoRequisicao)
        if (urlImagem != null) {
            salvarDadosLoja(loja, retornoRequisicao)
        }
    }

    fun salvarDadosLoja(loja: Loja, retornoRequisicao: (UIStatus<String>)-> Unit) {
        loja.imagemPerfil = "url"
        firestore
            .collection(FirebaseStorageConstants.COLECAO_RESTAURANTES)
            .add(loja)
            .addOnSuccessListener {
                retornoRequisicao.invoke(
                    UIStatus.Sucesso("Dados da loja salvos com sucesso!")
                )
            }.addOnFailureListener { erro->
                retornoRequisicao.invoke(
                    UIStatus.Erro("Erro ao salvar dados: ${erro.message}")
                )
            }
    }

    suspend fun uploadImagem(uri: Uri, retornoRequisicao: (UIStatus<String>)-> Unit) : Uri? {
        val idUsuarioLogado = FirebaseAuth.getInstance().currentUser?.uid
        val nomeFoto = UUID.randomUUID().toString()
        if (idUsuarioLogado != null){
            return withContext( Dispatchers.IO){
                storage
                    .getReference(FirebaseStorageConstants.PASTA_PERFIL)
                    .child(idUsuarioLogado)
                    .child( nomeFoto )
                    .putFile( uri )
                    .addOnFailureListener {
                        retornoRequisicao.invoke(
                            UIStatus.Erro("Erro ao fazer Upload da imagem")
                        )
                    }
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }
        }else{
            retornoRequisicao.invoke(
                UIStatus.Erro("Usuário não encontrado")
            )
            return null
        }
    }
}