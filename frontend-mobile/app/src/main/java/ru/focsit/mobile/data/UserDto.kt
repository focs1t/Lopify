package ru.focsit.mobile.data

data class UserDto(
    val userId: Long,
    val username: String,
    val userEmail: String,
    val password: String,
    val userCountryId: Long,
    val userRoleId: Long
)