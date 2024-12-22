package ru.focsit.mobile.repo.user

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.user.UserProfileApi
import ru.focsit.mobile.data.*

/**
 * Репозиторий для работы с профилем пользователя.
 * Включает методы для получения информации о текущем пользователе, комментариях, избранных песнях и удаления песен из избранного.
 */
class UserProfileRepository(context: Context) {
    // Инициализация API для работы с профилем пользователя
    private val api: UserProfileApi = RetrofitClient.getUserProfileApi(context)

    /**
     * Метод для получения информации о текущем пользователе.
     * Выполняет запрос на сервер для получения данных о текущем пользователе.
     *
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает объект [UserDto] с данными пользователя, иначе - null.
     */
    fun getCurrentUser(callback: (UserDto?) -> Unit) {
        api.getCurrentUser().enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для получения комментариев текущего пользователя.
     * Выполняет запрос на сервер для получения списка комментариев пользователя.
     *
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список комментариев [List<CommentDto>], иначе - null.
     */
    fun getMyComments(callback: (List<CommentDto>?) -> Unit) {
        api.getMyComments().enqueue(object : Callback<List<CommentDto>> {
            override fun onResponse(call: Call<List<CommentDto>>, response: Response<List<CommentDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<CommentDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для получения избранных песен текущего пользователя.
     * Выполняет запрос на сервер для получения списка избранных песен пользователя.
     *
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список избранных песен [List<SongDto>], иначе - null.
     */
    fun getMyFavorites(callback: (List<SongDto>?) -> Unit) {
        api.getMyFavorites().enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для удаления песни из избранного текущего пользователя.
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
}