package ru.focsit.mobile.api.moderator

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Track

interface TrackApi {

    // Получить все треки
    @GET("/api/moderator/tracks")
    fun getAllTracks(): Call<List<Track>>

    // Получить трек по ID
    @GET("/api/moderator/tracks/{id}")
    fun getTrackById(@Path("id") id: Long): Call<Track>

    // Создать новый трек
    @Multipart
    @POST("/api/moderator/tracks")
    fun createTrack(
        @Part file: MultipartBody.Part,
        @Part("track") track: Track
    ): Call<Track>

    // Удалить трек
    @DELETE("/api/moderator/tracks/{id}")
    fun deleteTrack(@Path("id") id: Long): Call<Void>

    // Поиск треков
    @GET("/api/moderator/tracks/search")
    fun searchTracks(@Query("query") query: String?): Call<List<Track>>
}