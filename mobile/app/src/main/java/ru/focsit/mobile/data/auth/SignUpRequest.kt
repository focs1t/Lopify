package ru.focsit.mobile.data.auth

/**
 * Запрос на регистрацию пользователя в системе.
 * Этот класс используется для отправки данных о пользователе при регистрации в системе.
 *
 * @property username Имя пользователя, которое будет использоваться для входа в систему.
 * @property email Электронная почта пользователя, которая будет использована для связи и восстановления пароля.
 * @property password Пароль пользователя для доступа к учетной записи.
 * @property role Роль пользователя в системе (например, "пользователь", "админ").
 */
data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String
)