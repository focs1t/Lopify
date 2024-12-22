package ru.focsit.mobile.data.auth

/**
 * Запрос на авторизацию пользователя в системе.
 * Этот класс используется для отправки данных пользователя при входе в систему.
 *
 * @property username Имя пользователя, которое будет использоваться для входа в систему.
 * @property password Пароль пользователя для доступа к его учетной записи.
 */
data class SignInRequest(
    val username: String,
    val password: String
)