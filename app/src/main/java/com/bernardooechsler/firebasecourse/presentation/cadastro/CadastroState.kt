package com.bernardooechsler.firebasecourse.presentation.cadastro

data class CadastroState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    var isCadastroClicked: Boolean = false,
    var isLoading: Boolean = false,
    var isError: String? = "",
    var isSuccess: Boolean = false
)
