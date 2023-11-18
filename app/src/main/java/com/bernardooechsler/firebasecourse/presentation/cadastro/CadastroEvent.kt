package com.bernardooechsler.firebasecourse.presentation.cadastro

sealed class CadastroEvent {
    data class EmailChanged(val email: String) : CadastroEvent()
    data class PasswordChanged(val password: String) : CadastroEvent()
    data class NameChanged(val name: String) : CadastroEvent()
    object CadastroClick : CadastroEvent()
}
