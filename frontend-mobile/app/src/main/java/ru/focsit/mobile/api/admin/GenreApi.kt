package ru.focsit.mobile.api.admin

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Genre

interface GenreApi {

    // Получить все жанры
    @GET("/api/admin/genres")
    fun getGenres(): Call<List<Genre>>

    // Создать новый жанр
    @POST("/api/admin/genres")
    fun createGenre(@Body genre: Genre): Call<Genre>

    // Удалить жанр по ID
    @DELETE("/api/admin/genres/{id}")
    fun deleteGenre(@Path("id") id: Long): Call<Void>
}