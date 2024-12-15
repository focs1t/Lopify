package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Comment

interface CommentApi {

    // Получить все комментарии
    @GET("/api/moderator/comments")
    fun getComments(): Call<List<Comment>>

    // Удалить комментарий по ID
    @DELETE("/api/moderator/comments/{id}")
    fun deleteComment(@Path("id") id: Long): Call<Void>
}