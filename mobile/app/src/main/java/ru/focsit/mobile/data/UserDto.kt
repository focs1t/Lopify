package ru.focsit.mobile.data

/**
 * Данные пользователя системы.
 * Используется для представления информации о пользователе, такой как его ID, имя пользователя, email и роль.
 *
 * @property id Уникальный идентификатор пользователя.
 * @property username Имя пользователя.
 * @property email Адрес электронной почты пользователя.
 * @property role Роль пользователя в системе (например, "админ", "модератор", "пользователь").
 */
data class UserDto(
    val id: Long,
    val username: String,
    val email: String,
    val role: String
)