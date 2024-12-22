package ru.focsit.mobile.repo

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignInRequest
import ru.focsit.mobile.data.auth.SignUpRequest

/**
 * Репозиторий для управления операциями авторизации и регистрации через API.
 * Включает методы для регистрации, авторизации и выхода из системы.
 */
class AuthRepository {

    // Инициализация API для авторизации
    private val authApi = RetrofitClient.getAuthApi()

    /**
     * Метод для регистрации нового пользователя.
     * Выполняет запрос на сервер для создания нового пользователя и получения токена.
     *
     * @param request Объект [SignUpRequest], содержащий данные для регистрации.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * В случае успешной регистрации передается объект [JwtAuthenticationResponse], в случае ошибки - null.
     */
    fun signUp(request: SignUpRequest, callback: (JwtAuthenticationResponse?) -> Unit) {
        authApi.signUp(request).enqueue(object : Callback<JwtAuthenticationResponse> {
            override fun onResponse(call: Call<JwtAuthenticationResponse>, response: Response<JwtAuthenticationResponse>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем тело ответа в callback
                    callback(response.body())
                } else {
                    // Логирование кода ошибки
                    Log.e("AuthRepository", "Ошибка регистрации: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<JwtAuthenticationResponse>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("AuthRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    /**
     * Метод для авторизации пользователя.
     * Выполняет запрос на сервер для получения токена пользователя.
     *
     * @param request Объект [SignInRequest], содержащий данные для авторизации.
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * В случае успешной авторизации передается объект [JwtAuthenticationResponse], в случае ошибки - null.
     */
    fun signIn(request: SignInRequest, callback: (JwtAuthenticationResponse?) -> Unit) {
        authApi.signIn(request).enqueue(object : Callback<JwtAuthenticationResponse> {
            override fun onResponse(call: Call<JwtAuthenticationResponse>, response: Response<JwtAuthenticationResponse>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем тело ответа в callback
                    callback(response.body())
                } else {
                    // Логирование кода ошибки
                    Log.e("AuthRepository", "Ошибка авторизации: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<JwtAuthenticationResponse>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("AuthRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    /**
     * Метод для выхода из системы.
     * Выполняет запрос на сервер для выхода пользователя из системы.
     *
     * @param callback Функция обратного вызова для обработки ответа от сервера.
     * Если выход из системы успешен, передается значение true, иначе - false.
     */
    fun logout(callback: (Boolean) -> Unit) {
        authApi.logout().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    Log.e("AuthRepository", "Ошибка при выходе: ${response.code()} - ${response.message()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("AuthRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }
}