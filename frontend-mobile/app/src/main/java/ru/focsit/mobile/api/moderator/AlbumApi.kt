package ru.focsit.mobile.api.moderator

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Comment
import ru.focsit.mobile.data.Track

interface AlbumApi {

    // Получить все альбомы
    @GET("/api/moderator/albums")
    fun getAlbums(): Call<List<Album>>

    // Получить альбом по ID
    @GET("/api/moderator/albums/{id}")
    fun getAlbumById(@Path("id") id: Long): Call<Album>

    // Создать новый альбом с файлом
    @Multipart
    @POST("/api/moderator/albums")
    fun createAlbum(
        @Part file: MultipartBody.Part,
        @Part("album") album: Album
    ): Call<Album>

    // Удалить альбом
    @DELETE("/api/moderator/albums/{id}")
    fun deleteAlbum(@Path("id") id: Long): Call<Void>

    // Поиск альбомов
    @GET("/api/moderator/albums/search")
    fun searchAlbums(@Query("query") query: String?): Call<List<Album>>

    // Добавить комментарий к альбому
    @POST("/api/moderator/albums/{id}/comments")
    fun createComment(
        @Path("id") albumId: Long,
        @Body comment: Comment
    ): Call<Comment>

    // Удалить комментарий из альбома
    @DELETE("/api/moderator/albums/{id}/comments/{commentId}")
    fun deleteComment(
        @Path("id") albumId: Long,
        @Path("commentId") commentId: Long
    ): Call<Void>

    // Поиск треков в альбоме
    @GET("/api/moderator/albums/{id}/tracks/search")
    fun searchTracks(
        @Path("id") albumId: Long,
        @Query("query") query: String?
    ): Call<List<Track>>
}