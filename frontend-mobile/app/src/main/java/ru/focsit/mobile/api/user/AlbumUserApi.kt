package ru.focsit.mobile.api.user

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Comment
import ru.focsit.mobile.data.Track

interface AlbumUserApi {

    @GET("/api/user/albums/{id}")
    fun getAlbumById(@Path("id") id: Long): Call<Album>

    @GET("/api/user/albums/search")
    fun searchAlbums(@Query("query") query: String?): Call<List<Album>>

    @POST("/api/user/albums/{id}/comments")
    fun createComment(@Path("id") id: Long, @Body comment: Comment): Call<Comment>

    @DELETE("/api/user/albums/{id}/comments/{commentId}")
    fun deleteComment(@Path("id") id: Long, @Path("commentId") commentId: Long): Call<Void>

    @GET("/api/user/albums/{id}/tracks/search")
    fun searchTracks(@Path("id") id: Long, @Query("query") query: String?): Call<List<Track>>
}