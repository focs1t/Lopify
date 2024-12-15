package ru.focsit.mobile.data

data class Role(
    val roleId: Long,
    val roleName: String,
    val users: List<User>
)