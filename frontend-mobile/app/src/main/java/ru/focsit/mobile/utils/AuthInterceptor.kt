package ru.focsit.mobile.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Добавляем токен в заголовок каждого запроса
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token") // Передаем токен
            .build()

        return chain.proceed(newRequest) // Выполняем запрос с новым заголовком
    }
}