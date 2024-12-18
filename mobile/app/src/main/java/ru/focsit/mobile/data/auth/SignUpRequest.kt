package ru.focsit.mobile.data.auth

data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String
)