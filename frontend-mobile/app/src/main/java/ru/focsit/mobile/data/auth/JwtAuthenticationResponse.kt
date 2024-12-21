package ru.focsit.mobile.data.auth;

data class JwtAuthenticationResponse(
        val token: String,
        val role: String
)
