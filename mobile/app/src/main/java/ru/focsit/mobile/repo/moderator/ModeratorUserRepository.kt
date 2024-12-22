package ru.focsit.mobile.repo.moderator

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.moderator.ModeratorUserApi
import ru.focsit.mobile.data.*

/**
 * Репозиторий для работы с пользователями в роли модератора.
 * Включает методы для получения пользователей, их комментариев и избранных песен, а также для удаления комментариев.
 */
class ModeratorUserRepository(context: Context) {
    // Инициализация API для работы с пользователями
    private val api: ModeratorUserApi = RetrofitClient.getModeratorUserApi(context)

    /**
     * Метод для получения списка всех пользователей.
     * Выполняет запрос на сервер для получения списка всех пользователей.
     *
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список пользователей [List<UserDto>], иначе - null.
     */
    fun getAllUsers(callback: (List<UserDto>?) -> Unit) {
        api.getAllUsers().enqueue(object : Callback<List<UserDto>> {
            override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для получения пользователя по его ID.
     * Выполняет запрос на сервер для получения информации о пользователе по его ID.
     *
     * @param id Идентификатор пользователя.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает объект [UserDto] с данными пользователя, иначе - null.
     */
    fun getUserById(id: Long, callback: (UserDto?) -> Unit) {
        api.getUserById(id).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для получения избранных песен пользователя.
     * Выполняет запрос на сервер для получения списка избранных песен пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список избранных песен [List<SongDto>], иначе - null.
     */
    fun getUserFavorites(userId: Long, callback: (List<SongDto>?) -> Unit) {
        api.getUserFavorites(userId).enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для получения комментариев пользователя.
     * Выполняет запрос на сервер для получения списка комментариев пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает список комментариев [List<CommentDto>], иначе - null.
     */
    fun getUserComments(userId: Long, callback: (List<CommentDto>?) -> Unit) {
        api.getUserComments(userId).enqueue(object : Callback<List<CommentDto>> {
            override fun onResponse(call: Call<List<CommentDto>>, response: Response<List<CommentDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<CommentDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Метод для удаления комментария пользователя.
     * Выполняет запрос на сервер для удаления комментария пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param commentId Идентификатор комментария.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает true, иначе - false.
     */
    fun deleteUserComment(userId: Long, commentId: Long, callback: (Boolean) -> Unit) {
        api.deleteUserComment(userId, commentId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }
}