package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Artist
import ru.focsit.mobile.data.Track

interface ArtistApi {

    // Получить всех артистов
    @GET("/api/moderator/artists")
    fun getArtists(): Call<List<Artist>>

    // Получить артиста по ID
    @GET("/api/moderator/artists/{id}")
    fun getArtistById(@Path("id") id: Long): Call<Artist>

    // Создать нового артиста
    @POST("/api/moderator/artists")
    fun createArtist(@Body artist: Artist): Call<Artist>

    // Обновить данные артиста
    @PUT("/api/moderator/artists/{id}")
    fun updateArtist(@Path("id") id: Long, @Body artist: Artist): Call<Artist>

    // Удалить артиста
    @DELETE("/api/moderator/artists/{id}")
    fun deleteArtist(@Path("id") id: Long): Call<Void>

    // Поиск артистов
    @GET("/api/moderator/artists/search")
    fun searchArtists(@Query("query") query: String?): Call<List<Artist>>
}