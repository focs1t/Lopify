package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*

/**
 * API для работы с пользователями в модераторской части приложения. Содержит методы для получения
 * информации о пользователях, их комментариях и избранных песнях, а также для удаления комментариев.
 */
interface ModeratorUserApi {

    /**
     * Получение всех пользователей.
     *
     * @return Список всех пользователей [List<UserDto>].
     */
    @GET("/api/moderator/users")
    fun getAllUsers(): Call<List<UserDto>>

    /**
     * Получение пользователя по ID.
     *
     * @param id ID пользователя.
     * @return Пользователь с указанным ID [UserDto].
     */
    @GET("/api/moderator/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<UserDto>

    /**
     * Получение всех избранных песен пользователя по его ID.
     *
     * @param userId ID пользователя.
     * @return Список избранных песен пользователя [List<SongDto>].
     */
    @GET("/api/moderator/users/{userId}/favorites")
    fun getUserFavorites(@Path("userId") userId: Long): Call<List<SongDto>>

    /**
     * Получение всех комментариев пользователя по его ID.
     *
     * @param userId ID пользователя.
     * @return Список комментариев пользователя [List<CommentDto>].
     */
    @GET("/api/moderator/users/{userId}/comments")
    fun getUserComments(@Path("userId") userId: Long): Call<List<CommentDto>>

    /**
     * Удаление комментария пользователя.
     *
     * @param userId ID пользователя, чей комментарий нужно удалить.
     * @param commentId ID комментария, который нужно удалить.
     * @return Ответ в виде [Void], подтверждающий успешное удаление.
     */
    @DELETE("/api/moderator/users/{userId}/comments/{commentId}")
    fun deleteUserComment(
        @Path("userId") userId: Long,
        @Path("commentId") commentId: Long
    ): Call<Void>
}