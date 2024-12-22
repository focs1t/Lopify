package ru.focsit.mobile.api.admin

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignUpRequest

/**
 * API для работы с пользователями в административной части приложения. Содержит методы для получения
 * списка пользователей, удаления пользователя и регистрации нового пользователя.
 */
interface AdminUserApi {

    /**
     * Получение всех пользователей.
     *
     * @return Список всех пользователей [List<UserDto>].
     */
    @GET("/api/admin/users")
    fun getAllUsers(): Call<List<UserDto>>

    /**
     * Удаление пользователя по его ID.
     *
     * @param id ID пользователя, которого нужно удалить.
     * @return Ответ в виде [Void], подтверждающий успешное удаление.
     */
    @DELETE("/api/admin/users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>

    /**
     * Регистрация нового пользователя.
     *
     * @param request Объект запроса для регистрации пользователя [SignUpRequest].
     * @return Ответ с информацией о созданном пользователе и токеном аутентификации [JwtAuthenticationResponse].
     */
    @POST("/api/admin/users")
    fun registerUser(@Body request: SignUpRequest): Call<JwtAuthenticationResponse>
}