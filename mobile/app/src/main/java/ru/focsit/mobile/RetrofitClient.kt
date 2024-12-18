package ru.focsit.mobile

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.focsit.mobile.api.AuthApi
import ru.focsit.mobile.api.admin.AdminUserApi
import ru.focsit.mobile.api.moderator.ModeratorSongApi
import ru.focsit.mobile.api.moderator.ModeratorUserApi
import ru.focsit.mobile.api.user.UserProfileApi
import ru.focsit.mobile.api.user.UserSongApi
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

    private fun createRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAuthApi(): AuthApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AuthApi::class.java)
    }

    fun getAdminUserApi(context: Context): AdminUserApi {
        return createRetrofit(context).create(AdminUserApi::class.java)
    }

    fun getModeratorSongApi(context: Context): ModeratorSongApi {
        return createRetrofit(context).create(ModeratorSongApi::class.java)
    }

    fun getUserSongApi(context: Context): UserSongApi {
        return createRetrofit(context).create(UserSongApi::class.java)
    }

    fun getModeratorUserApi(context: Context): ModeratorUserApi {
        return createRetrofit(context).create(ModeratorUserApi::class.java)
    }

    fun getUserProfileApi(context: Context): UserProfileApi {
        return createRetrofit(context).create(UserProfileApi::class.java)
    }
}