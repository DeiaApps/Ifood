package com.andreaaf.appifood.domain.usecase

import com.andreaaf.appifood.data.remote.firebase.repository.IAutenticacaoRepository
import com.andreaaf.appifood.domain.model.Usuario
import com.google.common.truth.Truth.assertThat
//import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith( MockitoJUnitRunner::class)
class AutenticacaoUseCaseTest {

    @Mock
    lateinit var repository: IAutenticacaoRepository
    private lateinit var autenticacaoUseCase: AutenticacaoUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        autenticacaoUseCase = AutenticacaoUseCase( repository )
    }

    @Test
    fun validarCadastroUsuario_validarDadosPreenchidosCorretamente_retorna_verdadeiro() {
        val usuario = Usuario("Jamilton", "jamilton@gmail.com", "ja123456", "11965521545")

        val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)

        assertThat(resultadoAutenticacao.sucessoCadastro).isTrue()
    }

    @Test
    fun validarCadastroUsuario_validaNomeInvalido_retorna_falso() {
        val usuario = Usuario("", "jamilton@gmail.com", "ja123456","11965521545")

        val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)

        assertThat(resultadoAutenticacao.sucessoCadastro).isFalse()
    }

    @Test
    fun validarCadastroUsuario_validaEmailInvalido_retorna_falso() {
        val usuario = Usuario("Jamilton", "", "ja123456", "11965521545")

        val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)

        assertThat(resultadoAutenticacao.sucessoCadastro).isFalse()
    }

    @Test
    fun validarCadastroUsuario_validaSenhaInvalido_retorna_falso() {
        val usuario = Usuario("Jamilton", "jamilton@gmail.com", "", "11965521545")

        val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)

        assertThat(resultadoAutenticacao.sucessoCadastro).isFalse()
    }

    @Test
    fun validarCadastroUsuario_validaSenhaInvalidoMenorSeisCaracteres_retorna_falso() {
        val usuario = Usuario("Jamilton", "jamilton@gmail.com", "123", "11965521545")

        val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)

        assertThat(resultadoAutenticacao.sucessoCadastro).isFalse()
    }

    @Test
    fun validarCadastroUsuario_validaTelefoneInvalido_retorna_falso() {
        val usuario = Usuario("Jamilton", "jamilton@gmail.com", "123ja12","")

        val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)

        assertThat(resultadoAutenticacao.sucessoCadastro).isFalse()
    }

    @After
    fun tearDown() {
    }
}