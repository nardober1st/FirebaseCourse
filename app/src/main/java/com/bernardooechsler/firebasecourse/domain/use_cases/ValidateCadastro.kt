package com.bernardooechsler.firebasecourse.domain.use_cases

class ValidateCadastro {

    fun login(email: String, password: String): String {
        var message = ""
        if (password.isEmpty() || email.isEmpty()) {
            message = "Preencha todos os campos"
        }
        return message
    }

    private fun passwordValid(password: String): Boolean {
        val hasUpperCase = password.any {
            it.isUpperCase()
        }
        val hasLowerCase = password.any {
            it.isLowerCase()
        }
        val hasDigit = password.any {
            it.isDigit()

        }

        val minLenght = password.length >= 8

        return hasUpperCase && hasLowerCase && hasDigit && minLenght
    }
}