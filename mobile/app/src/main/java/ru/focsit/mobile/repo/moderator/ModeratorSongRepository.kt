package ru.focsit.mobile.repo.moderator

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.moderator.ModeratorSongApi
import ru.focsit.mobile.data.*

/**
 * Репозиторий для работы с песнями в роли модератора.
 * Включает методы для получения песен, их поиска, создания, обновления и удаления,
 * а также для работы с комментариями.
 */
class ModeratorSongRepository(context: Context) {
    // Инициализация API для работы с песнями
    private val api: ModeratorSongApi = RetrofitClient.getModeratorSongApi(context)

    /**
     * Метод для получения всех песен.
     * Выполняет запрос на сервер для получения списка всех песен.
     *
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список песен [List<SongDto>], иначе - null.
     */
    fun getAllSongs(callback: (List<SongDto>?) -> Unit) {
        api.getAllSongs().enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для поиска песен по различным параметрам (альбом, артист, название).
     * Выполняет запрос на сервер для поиска песен по данным фильтрам.
     *
     * @param album Название альбома (можно передать null).
     * @param artist Имя артиста (можно передать null).
     * @param name Название песни (можно передать null).
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список найденных песен [List<SongDto>], иначе - null.
     */
    fun searchSongs(album: String?, artist: String?, name: String?, callback: (List<SongDto>?) -> Unit) {
        api.searchSongs(album, artist, name).enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для создания новой песни.
     * Выполняет запрос на сервер для создания новой песни.
     *
     * @param song Объект песни [SongDto], содержащий данные новой песни.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает объект [SongDto] с данными созданной песни.
     */
    fun createSong(song: SongDto, callback: (SongDto?) -> Unit) {
        api.createSong(song).enqueue(object : Callback<SongDto> {
            override fun onResponse(call: Call<SongDto>, response: Response<SongDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<SongDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для обновления данных песни.
     * Выполняет запрос на сервер для обновления данных существующей песни.
     *
     * @param id Идентификатор песни, которую необходимо обновить.
     * @param song Объект песни [SongDto] с новыми данными.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает обновленный объект [SongDto].
     */
    fun updateSong(id: Long, song: SongDto, callback: (SongDto?) -> Unit) {
        api.updateSong(id, song).enqueue(object : Callback<SongDto> {
            override fun onResponse(call: Call<SongDto>, response: Response<SongDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<SongDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для удаления песни.
     * Выполняет запрос на сервер для удаления песни по ее ID.
     *
     * @param id Идентификатор песни, которую необходимо удалить.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает `true`, иначе — `false`.
     */
    fun deleteSong(id: Long, callback: (Boolean) -> Unit) {
        api.deleteSong(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    /**
     * Метод для получения комментариев к песне.
     * Выполняет запрос на сервер для получения списка комментариев к указанной песне.
     *
     * @param songId Идентификатор песни, к которой нужно получить комментарии.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список комментариев [List<CommentDto>], иначе - null.
     */
    fun getCommentsBySong(songId: Long, callback: (List<CommentDto>?) -> Unit) {
        api.getCommentsBySong(songId).enqueue(object : Callback<List<CommentDto>> {
            override fun onResponse(call: Call<List<CommentDto>>, response: Response<List<CommentDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<CommentDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для удаления комментария к песне.
     * Выполняет запрос на сервер для удаления комментария к песне по ее ID и ID комментария.
     *
     * @param songId Идентификатор песни.
     * @param commentId Идентификатор комментария, который необходимо удалить.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает `true`, иначе — `false`.
     */
    fun deleteComment(songId: Long, commentId: Long, callback: (Boolean) -> Unit) {
        api.deleteComment(songId, commentId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }
}