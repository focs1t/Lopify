package ru.focsit.mobile.api.user

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*

interface UserProfileApi {
    @GET("/api/user/users/me")
    fun getCurrentUser(): Call<UserDto>

    @GET("/api/user/users/me/comments")
    fun getMyComments(): Call<List<CommentDto>>

    @GET("/api/user/users/me/favorites")
    fun getMyFavorites(): Call<List<SongDto>>

    @DELETE("/api/user/users/me/favorites/{songId}")
    fun removeSongFromFavorites(@Path("songId") songId: Long): Call<Void>
}