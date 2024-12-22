package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*

/**
 * API для работы с песнями в модераторской части приложения. Содержит методы для получения, создания,
 * обновления, удаления песен и работы с комментариями к песням.
 */
interface ModeratorSongApi {

    /**
     * Получение всех песен.
     *
     * @return Список всех песен [List<SongDto>].
     */
    @GET("/api/moderator/songs")
    fun getAllSongs(): Call<List<SongDto>>

    /**
     * Поиск песен по альбому, исполнителю или названию.
     *
     * @param album (Необязательный) Альбом для фильтрации.
     * @param artist (Необязательный) Исполнитель для фильтрации.
     * @param name (Необязательное) Название песни для фильтрации.
     * @return Список песен, удовлетворяющих фильтрам [List<SongDto>].
     */
    @GET("/api/moderator/songs/search")
    fun searchSongs(
        @Query("album") album: String?,
        @Query("artist") artist: String?,
        @Query("name") name: String?
    ): Call<List<SongDto>>

    /**
     * Создание новой песни.
     *
     * @param song Песня для создания [SongDto].
     * @return Созданная песня [SongDto].
     */
    @POST("/api/moderator/songs")
    fun createSong(@Body song: SongDto): Call<SongDto>

    /**
     * Обновление существующей песни по ее ID.
     *
     * @param id ID песни, которую нужно обновить.
     * @param song Обновленные данные песни [SongDto].
     * @return Обновленная песня [SongDto].
     */
    @PUT("/api/moderator/songs/{id}")
    fun updateSong(@Path("id") id: Long, @Body song: SongDto): Call<SongDto>

    /**
     * Удаление песни по ID.
     *
     * @param id ID песни, которую нужно удалить.
     * @return Ответ в виде [Void], подтверждающий успешное удаление.
     */
    @DELETE("/api/moderator/songs/{id}")
    fun deleteSong(@Path("id") id: Long): Call<Void>

    /**
     * Получение всех комментариев для песни по ее ID.
     *
     * @param songId ID песни, для которой нужно получить комментарии.
     * @return Список комментариев для песни [List<CommentDto>].
     */
    @GET("/api/moderator/songs/{songId}/comments")
    fun getCommentsBySong(@Path("songId") songId: Long): Call<List<CommentDto>>

    /**
     * Удаление комментария для песни по ID песни и ID комментария.
     *
     * @param songId ID песни, для которой нужно удалить комментарий.
     * @param commentId ID комментария, который нужно удалить.
     * @return Ответ в виде [Void], подтверждающий успешное удаление.
     */
    @DELETE("/api/moderator/songs/{songId}/comments/{commentId}")
    fun deleteComment(@Path("songId") songId: Long, @Path("commentId") commentId: Long): Call<Void>
}