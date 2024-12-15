package ru.focsit.mobile.data.auth

data class SignUpRequest(
    val username: String,
    val userEmail: String,
    val password: String,
    val userCountryId: Long,
    val userRoleId: Long
)