package ru.focsit.mobile.api.user

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.Track

interface PlaylistUserApi {

    @GET("/api/user/playlists/{id}")
    fun getPlaylistById(@Path("id") id: Long): Call<Playlist>

    @GET("/api/user/playlists/{id}/tracks/search")
    fun searchTracksInPlaylist(@Path("id") id: Long, @Query("query") query: String?): Call<List<Track>>

    @Multipart
    @PUT("/api/user/playlists/{id}")
    fun updatePlaylist(
        @Path("id") id: Long,
        @Part file: MultipartBody.Part,
        @Part("playlist") playlist: MultipartBody.Part
    ): Call<Playlist>

    @DELETE("/api/user/playlists/{id}")
    fun deletePlaylist(@Path("id") id: Long): Call<Void>
}