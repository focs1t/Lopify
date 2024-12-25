package ru.focsit.mobile.repo.user

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.user.UserSongApi
import ru.focsit.mobile.data.*

/**
 * Репозиторий для управления операциями, связанными с песнями для пользователя.
 * Включает методы для получения всех песен, поиска, добавления/удаления песен в/из избранного и работы с комментариями.
 */
class UserSongRepository(context: Context) {
    // Инициализация API для работы с песнями
    private val api: UserSongApi = RetrofitClient.getUserSongApi(context)

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
     * Метод для поиска песен по различным параметрам.
     * Выполняет запрос на сервер для поиска песен по альбому, исполнителю или названию.
     *
     * @param album Название альбома для поиска.
     * @param artist Исполнитель для поиска.
     * @param name Название песни для поиска.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список песен [List<SongDto>], иначе - null.
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
     * Метод для добавления песни в избранное.
     * Выполняет запрос на сервер для добавления песни в избранное пользователя.
     *
     * @param songId Идентификатор песни.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если добавление успешное, возвращает true, иначе - false.
     */
    fun addSongToFavorites(songId: Long, callback: (Boolean) -> Unit) {
        api.addSongToFavorites(songId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    /**
     * Метод для удаления песни из избранного.
     * Выполняет запрос на сервер для удаления песни из избранного пользователя.
     *
     * @param songId Идентификатор песни.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если удаление успешное, возвращает true, иначе - false.
     */
    fun removeSongFromFavorites(songId: Long, callback: (Boolean) -> Unit) {
        api.removeSongFromFavorites(songId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    /**
     * Метод для получения избранных песен пользователя.
     * Выполняет запрос на сервер для получения списка избранных песен пользователя.
     *
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список избранных песен [List<SongDto>], иначе - null.
     */
    fun getUserFavorites(callback: (List<SongDto>?) -> Unit) {
        api.getUserFavorites().enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для добавления комментария к песне.
     * Выполняет запрос на сервер для добавления комментария к песне.
     *
     * @param songId Идентификатор песни.
     * @param content Содержимое комментария.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает объект [CommentDto] с данным комментарием, иначе - null.
     */
    fun addCommentToSong(songId: Long, content: String, callback: (CommentDto?) -> Unit) {
        api.addCommentToSong(songId, content).enqueue(object : Callback<CommentDto> {
            override fun onResponse(call: Call<CommentDto>, response: Response<CommentDto>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("API Error", "Failed to add comment: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<CommentDto>, t: Throwable) {
                Log.e("API Error", "Request failed: ${t.message}")
                callback(null)
            }
        })
    }

    /**
     * Метод для получения комментариев к песне.
     * Выполняет запрос на сервер для получения списка комментариев к песне.
     *
     * @param songId Идентификатор песни.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список комментариев [List<CommentDto>], иначе - null.
     */
    fun getCommentsForSong(songId: Long, callback: (List<CommentDto>?) -> Unit) {
        api.getCommentsForSong(songId).enqueue(object : Callback<List<CommentDto>> {
            override fun onResponse(call: Call<List<CommentDto>>, response: Response<List<CommentDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<CommentDto>>, t: Throwable) {
                callback(null)
            }
        })
    }
}