package ru.focsit.mobile

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.focsit.mobile.api.AuthApi
import ru.focsit.mobile.utils.AuthInterceptor
import ru.focsit.mobile.utils.PreferencesHelper

object RetrofitClient {
    private const val BASE_URL = "http://192.168.2.103:8080"

    // Получаем токен из SharedPreferences
    private fun getTokenFromPreferences(context: Context): String? {
        return PreferencesHelper.getToken(context)
    }

    // Создаем OkHttpClient с интерцептором
    private fun createOkHttpClient(context: Context): OkHttpClient {
        val token = getTokenFromPreferences(context) ?: ""
        val authInterceptor = AuthInterceptor(token)

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)  // Добавляем интерцептор
            .build()
    }

    fun getAuthApi(): AuthApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AuthApi::class.java)
    }
}