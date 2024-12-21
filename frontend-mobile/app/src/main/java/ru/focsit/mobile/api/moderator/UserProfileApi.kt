package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.User

interface UserProfileApi {

    // Получить всех пользователей
    @GET("/api/moderator/users")
    fun getAllUsers(): Call<List<User>>

    // Получить пользователя по ID
    @GET("/api/moderator/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<User>

    // Удалить пользователя
    @DELETE("/api/moderator/users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>

    // Поиск пользователей
    @GET("/api/moderator/users/search")
    fun searchUsers(@Query("query") query: String?): Call<List<User>>
}