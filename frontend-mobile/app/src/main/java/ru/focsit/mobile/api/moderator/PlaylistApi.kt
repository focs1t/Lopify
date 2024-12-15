package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.Track

interface PlaylistApi {

    // Получить плейлист по ID
    @GET("/api/moderator/playlists/{id}")
    fun getPlaylistById(@Path("id") id: Long): Call<Playlist>

    // Создать новый плейлист
    @Multipart
    @POST("/api/moderator/playlists")
    fun createPlaylist(
        @Part file: MultipartBody.Part,
        @Part("playlist") playlist: Playlist
    ): Call<Playlist>

    // Обновить плейлист
    @Multipart
    @PUT("/api/moderator/playlists/{id}")
    fun updatePlaylist(
        @Path("id") id: Long,
        @Part file: MultipartBody.Part,
        @Part("playlist") playlist: Playlist
    ): Call<Playlist>

    // Удалить плейлист
    @DELETE("/api/moderator/playlists/{id}")
    fun deletePlaylist(@Path("id") id: Long): Call<Void>

    // Поиск треков в плейлисте
    @GET("/api/moderator/playlists/{id}/tracks/search")
    fun searchTracks(@Path("id") id: Long, @Query("query") query: String?): Call<List<Track>>
}