package ru.focsit.mobile.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignInRequest
import ru.focsit.mobile.data.auth.SignUpRequest

/**
 * API для аутентификации пользователей. Содержит методы для регистрации, входа и выхода.
 */
interface AuthApi {

    /**
     * Регистрация нового пользователя.
     *
     * @param request Объект [SignUpRequest], содержащий данные для регистрации (имя пользователя, email, пароль и роль).
     * @return Ответ в виде [JwtAuthenticationResponse], который содержит JWT токен и роль пользователя.
     */
    @POST("/api/auth/sign-up")
    fun signUp(@Body request: SignUpRequest): Call<JwtAuthenticationResponse>

    /**
     * Авторизация пользователя.
     *
     * @param request Объект [SignInRequest], содержащий имя пользователя и пароль для входа.
     * @return Ответ в виде [JwtAuthenticationResponse], который содержит JWT токен и роль пользователя.
     */
    @POST("/api/auth/sign-in")
    fun signIn(@Body request: SignInRequest): Call<JwtAuthenticationResponse>

    /**
     * Выход пользователя из системы.
     *
     * @return Ответ в виде [Void], подтверждающий успешный выход.
     */
    @POST("/api/auth/logout")
    fun logout(): Call<Void>
}