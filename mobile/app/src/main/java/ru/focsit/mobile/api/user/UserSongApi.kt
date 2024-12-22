package ru.focsit.mobile.api.user

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*

/**
 * API для работы с песнями пользователя. Содержит методы для получения всех песен, поиска песен,
 * добавления и удаления песен из избранного, а также работы с комментариями.
 */
interface UserSongApi {

    /**
     * Получение списка всех песен.
     *
     * @return Список песен [List<SongDto>].
     */
    @GET("/api/user/songs")
    fun getAllSongs(): Call<List<SongDto>>

    /**
     * Поиск песен по альбому, исполнителю или названию.
     *
     * @param album (необязательный) Фильтрация по альбому.
     * @param artist (необязательный) Фильтрация по исполнителю.
     * @param name (необязательный) Фильтрация по названию песни.
     * @return Список песен, удовлетворяющих условиям поиска [List<SongDto>].
     */
    @GET("/api/user/songs/search")
    fun searchSongs(
        @Query("album") album: String?,
        @Query("artist") artist: String?,
        @Query("name") name: String?
    ): Call<List<SongDto>>

    /**
     * Добавление песни в избранное.
     *
     * @param songId ID песни, которую нужно добавить в избранное.
     * @return Ответ в виде [Void], подтверждающий успешное добавление.
     */
    @POST("/api/user/songs/favorites/{songId}")
    fun addSongToFavorites(@Path("songId") songId: Long): Call<Void>

    /**
     * Удаление песни из избранного.
     *
     * @param songId ID песни, которую нужно удалить из избранного.
     * @return Ответ в виде [Void], подтверждающий успешное удаление.
     */
    @DELETE("/api/user/songs/favorites/{songId}")
    fun removeSongFromFavorites(@Path("songId") songId: Long): Call<Void>

    /**
     * Получение списка избранных песен пользователя.
     *
     * @return Список избранных песен [List<SongDto>].
     */
    @GET("/api/user/songs/favorites")
    fun getUserFavorites(): Call<List<SongDto>>

    /**
     * Добавление комментария к песне.
     *
     * @param songId ID песни, к которой нужно добавить комментарий.
     * @param content Содержание комментария.
     * @return Объект [CommentDto], который содержит данные добавленного комментария.
     */
    @POST("/api/user/songs/{songId}/comments")
    fun addCommentToSong(
        @Path("songId") songId: Long,
        @Body content: String
    ): Call<CommentDto>

    /**
     * Получение комментариев для конкретной песни.
     *
     * @param songId ID песни, для которой нужно получить комментарии.
     * @return Список комментариев [List<CommentDto>].
     */
    @GET("/api/user/songs/{songId}/comments")
    fun getCommentsForSong(@Path("songId") songId: Long): Call<List<CommentDto>>
}