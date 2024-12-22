package ru.focsit.mobile.data.auth

/**
 * Ответ на запрос аутентификации с использованием JWT (JSON Web Token).
 * Этот класс представляет собой ответ, содержащий токен и роль пользователя после успешной авторизации.
 *
 * @property token JWT токен, который используется для дальнейшей аутентификации пользователя в системе.
 * @property role Роль пользователя в системе ("USER", "ADMIN", "MODERATOR").
 */
data class JwtAuthenticationResponse(
        val token: String,
        val role: String
)