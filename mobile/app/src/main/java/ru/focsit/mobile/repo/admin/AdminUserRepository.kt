package ru.focsit.mobile.repo.admin

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.admin.AdminUserApi
import ru.focsit.mobile.data.*
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignUpRequest

/**
 * Репозиторий для работы с пользователями в административной панели.
 * Включает методы для получения пользователей, регистрации новых пользователей
 * и удаления существующих пользователей.
 */
class AdminUserRepository(context: Context) {
    // Инициализация API для работы с пользователями в административной панели
    private val api: AdminUserApi = RetrofitClient.getAdminUserApi(context)

    /**
     * Метод для получения всех пользователей.
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
     * Метод для удаления пользователя.
     * Выполняет запрос на сервер для удаления пользователя по его ID.
     *
     * @param id Идентификатор пользователя, которого необходимо удалить.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает `true`, иначе - `false`.
     */
    fun deleteUser(id: Long, callback: (Boolean) -> Unit) {
        api.deleteUser(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    /**
     * Метод для регистрации нового пользователя.
     * Выполняет запрос на сервер для создания нового пользователя.
     *
     * @param request Объект запроса на регистрацию [SignUpRequest], содержащий необходимые данные для регистрации.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если запрос успешен, возвращает объект [JwtAuthenticationResponse] с токеном.
     */
    fun registerUser(request: SignUpRequest, callback: (JwtAuthenticationResponse?) -> Unit) {
        api.registerUser(request).enqueue(object : Callback<JwtAuthenticationResponse> {
            override fun onResponse(call: Call<JwtAuthenticationResponse>, response: Response<JwtAuthenticationResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<JwtAuthenticationResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}