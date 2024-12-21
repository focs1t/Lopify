package ru.focsit.mobile.api.user

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.User

interface ProfileApi {

    // Получить список всех пользователей
    @GET("/api/user/users")
    fun getAllUsers(): Call<List<User>>

    // Получить пользователя по ID с его плейлистами
    @GET("/api/user/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<User>
}