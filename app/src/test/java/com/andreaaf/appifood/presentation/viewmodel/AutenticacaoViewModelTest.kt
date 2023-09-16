package com.andreaaf.appifood.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.andreaaf.appifood.domain.model.Usuario
import com.andreaaf.appifood.domain.usecase.AutenticacaoUseCase
import com.andreaaf.appifood.domain.usecase.ResultadoAutenticacao
import com.andreaaf.appifood.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
//import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith( MockitoJUnitRunner::class )
class AutenticacaoViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var autenticacaoViewModel: AutenticacaoViewModel

    @Mock
    lateinit var autenticacaoUseCase: AutenticacaoUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        autenticacaoViewModel = AutenticacaoViewModel(autenticacaoUseCase)
    }

    @Test
    fun cadastrarUsuario_cadastrarUsuarioFirebase_retorna_verdadeiro() = runTest {

        val usuario = Usuario("Jamilton", "jamilton@gmail.com", "ja123456")
        Mockito.`when`(autenticacaoUseCase.validarCadastroUsuario(usuario)).thenReturn(
            ResultadoAutenticacao()
        )

        Mockito.`when`( autenticacaoUseCase.cadastrarUsuario( usuario )).thenReturn(
            true
        )

        autenticacaoViewModel.cadastrarUsuario(usuario)

        //assert
        val retorno = autenticacaoViewModel.sucesso.getOrAwaitValue()
        assertThat( retorno ).isTrue() //testou integração do cadastro com livedata
        //assertThat( retorno ).isFalse()//dá erro e testa o validar usuário
    }

    @Test
    fun logarUsuario_logarUsuarioFirebase_retorna_verdadeiro() = runTest {

        val usuario = Usuario("Jamilton", "jamilton@gmail.com", "ja123456")
        Mockito.`when`(autenticacaoUseCase.validarLoginUsuario(usuario)).thenReturn(
            ResultadoAutenticacao()
        )

        Mockito.`when`( autenticacaoUseCase.logarUsuario( usuario )).thenReturn(
            true
        )

        autenticacaoViewModel.logarUsuario(usuario)

        //assert
        val retorno = autenticacaoViewModel.sucesso.getOrAwaitValue()
        assertThat( retorno ).isTrue()
    }

    @After
    fun tearDown() {
    }
}