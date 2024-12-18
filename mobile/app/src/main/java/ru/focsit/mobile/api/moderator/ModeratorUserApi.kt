package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*

interface ModeratorUserApi {
    @GET("/api/moderator/users")
    fun getAllUsers(): Call<List<UserDto>>

    @GET("/api/moderator/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<UserDto>

    @GET("/api/moderator/users/{userId}/favorites")
    fun getUserFavorites(@Path("userId") userId: Long): Call<List<SongDto>>

    @GET("/api/moderator/users/{userId}/comments")
    fun getUserComments(@Path("userId") userId: Long): Call<List<CommentDto>>

    @DELETE("/api/moderator/users/{userId}/comments/{commentId}")
    fun deleteUserComment(
        @Path("userId") userId: Long,
        @Path("commentId") commentId: Long
    ): Call<Void>
}