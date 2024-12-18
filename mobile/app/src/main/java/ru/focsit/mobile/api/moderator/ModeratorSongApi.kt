package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*

interface ModeratorSongApi {
    @GET("/api/moderator/songs")
    fun getAllSongs(): Call<List<SongDto>>

    @GET("/api/moderator/songs/search")
    fun searchSongs(
        @Query("album") album: String?,
        @Query("artist") artist: String?,
        @Query("name") name: String?
    ): Call<List<SongDto>>

    @POST("/api/moderator/songs")
    fun createSong(@Body song: SongDto): Call<SongDto>

    @PUT("/api/moderator/songs/{id}")
    fun updateSong(@Path("id") id: Long, @Body song: SongDto): Call<SongDto>

    @DELETE("/api/moderator/songs/{id}")
    fun deleteSong(@Path("id") id: Long): Call<Void>

    @GET("/api/moderator/songs/{songId}/comments")
    fun getCommentsBySong(@Path("songId") songId: Long): Call<List<CommentDto>>

    @DELETE("/api/moderator/songs/{songId}/comments/{commentId}")
    fun deleteComment(@Path("songId") songId: Long, @Path("commentId") commentId: Long): Call<Void>
}