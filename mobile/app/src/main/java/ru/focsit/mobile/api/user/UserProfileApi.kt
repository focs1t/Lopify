package ru.focsit.mobile.api.user

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*

/**
 * API для работы с профилем пользователя. Содержит методы для получения информации о текущем пользователе,
 * его комментариях, избранных песнях и удалении песен из избранного.
 */
interface UserProfileApi {

    /**
     * Получение текущего пользователя.
     *
     * @return Данные текущего пользователя [UserDto].
     */
    @GET("/api/user/users/me")
    fun getCurrentUser(): Call<UserDto>

    /**
     * Получение всех комментариев текущего пользователя.
     *
     * @return Список комментариев текущего пользователя [List<CommentDto>].
     */
    @GET("/api/user/users/me/comments")
    fun getMyComments(): Call<List<CommentDto>>

    /**
     * Получение всех избранных песен текущего пользователя.
     *
     * @return Список избранных песен [List<SongDto>].
     */
    @GET("/api/user/users/me/favorites")
    fun getMyFavorites(): Call<List<SongDto>>

    /**
     * Удаление песни из избранного текущего пользователя.
     *
     * @param songId ID песни, которую нужно удалить из избранного.
     * @return Ответ в виде [Void], подтверждающий успешное удаление.
     */
    @DELETE("/api/user/users/me/favorites/{songId}")
    fun removeSongFromFavorites(@Path("songId") songId: Long): Call<Void>
}