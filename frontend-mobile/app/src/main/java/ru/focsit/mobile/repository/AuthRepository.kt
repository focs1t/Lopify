package ru.focsit.mobile.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignInRequest
import ru.focsit.mobile.data.auth.SignUpRequest

class AuthRepository {
    private val authApi = RetrofitClient.authApi

    // Метод для регистрации
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

    // Метод для авторизации
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
}