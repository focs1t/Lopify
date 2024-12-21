package ru.focsit.mobile.api.admin

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.User
import ru.focsit.mobile.data.auth.SignUpRequest
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse

interface UserApi {

    // Получить все пользователи
    @GET("/api/admin/users")
    fun getUsers(): Call<List<User>>

    // Получить пользователя по ID
    @GET("/api/admin/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<User>

    // Регистрация нового пользователя
    @POST("/api/admin/users")
    fun registerUser(@Body request: SignUpRequest): Call<JwtAuthenticationResponse>

    // Обновить пользователя по ID
    @PUT("/api/admin/users/{id}")
    fun updateUser(
        @Path("id") id: Long,
        @Body userDetails: User
    ): Call<User>

    // Удалить пользователя по ID
    @DELETE("/api/admin/users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>
}