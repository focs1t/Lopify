package ru.focsit.mobile.api.user

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*

interface UserSongApi {
    @GET("/api/user/songs")
    fun getAllSongs(): Call<List<SongDto>>

    @GET("/api/user/songs/search")
    fun searchSongs(
        @Query("album") album: String?,
        @Query("artist") artist: String?,
        @Query("name") name: String?
    ): Call<List<SongDto>>

    @POST("/api/user/songs/favorites/{songId}")
    fun addSongToFavorites(@Path("songId") songId: Long): Call<Void>

    @DELETE("/api/user/songs/favorites/{songId}")
    fun removeSongFromFavorites(@Path("songId") songId: Long): Call<Void>

    @GET("/api/user/songs/favorites")
    fun getUserFavorites(): Call<List<SongDto>>

    @POST("/api/user/songs/{songId}/comments")
    fun addCommentToSong(
        @Path("songId") songId: Long,
        @Body content: String
    ): Call<CommentDto>

    // New: Get comments for a song
    @GET("/api/user/songs/{songId}/comments")
    fun getCommentsForSong(@Path("songId") songId: Long): Call<List<CommentDto>>
}