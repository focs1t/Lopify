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

/**
 * Объект для создания и управления экземплярами Retrofit для различных API.
 */
object RetrofitClient {
    private const val BASE_URL = "http://192.168.2.103:8080"

    /**
     * Получает токен авторизации из SharedPreferences.
     *
     * @param context Контекст, используемый для доступа к SharedPreferences.
     * @return Токен в виде строки или `null`, если токен отсутствует.
     */
    private fun getTokenFromPreferences(context: Context): String? {
        return PreferencesHelper.getToken(context)
    }

    /**
     * Создает экземпляр [OkHttpClient] с интерцептором авторизации.
     *
     * @param context Контекст, используемый для получения токена.
     * @return Настроенный экземпляр [OkHttpClient].
     */
    private fun createOkHttpClient(context: Context): OkHttpClient {
        val token = getTokenFromPreferences(context) ?: ""
        val authInterceptor = AuthInterceptor(token)

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    /**
     * Создает экземпляр [Retrofit] с указанным [context].
     *
     * @param context Контекст, используемый для настройки клиента.
     * @return Настроенный экземпляр [Retrofit].
     */
    private fun createRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Возвращает реализацию API для авторизации ([AuthApi]).
     *
     * @return Экземпляр [AuthApi].
     */
    fun getAuthApi(): AuthApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AuthApi::class.java)
    }

    /**
     * Возвращает реализацию API для управления пользователями администратора ([AdminUserApi]).
     *
     * @param context Контекст, используемый для настройки.
     * @return Экземпляр [AdminUserApi].
     */
    fun getAdminUserApi(context: Context): AdminUserApi {
        return createRetrofit(context).create(AdminUserApi::class.java)
    }

    /**
     * Возвращает реализацию API для работы с песнями модератора ([ModeratorSongApi]).
     *
     * @param context Контекст, используемый для настройки.
     * @return Экземпляр [ModeratorSongApi].
     */
    fun getModeratorSongApi(context: Context): ModeratorSongApi {
        return createRetrofit(context).create(ModeratorSongApi::class.java)
    }

    /**
     * Возвращает реализацию API для работы с песнями пользователя ([UserSongApi]).
     *
     * @param context Контекст, используемый для настройки.
     * @return Экземпляр [UserSongApi].
     */
    fun getUserSongApi(context: Context): UserSongApi {
        return createRetrofit(context).create(UserSongApi::class.java)
    }

    /**
     * Возвращает реализацию API для управления пользователями модератора ([ModeratorUserApi]).
     *
     * @param context Контекст, используемый для настройки.
     * @return Экземпляр [ModeratorUserApi].
     */
    fun getModeratorUserApi(context: Context): ModeratorUserApi {
        return createRetrofit(context).create(ModeratorUserApi::class.java)
    }

    /**
     * Возвращает реализацию API для профиля пользователя ([UserProfileApi]).
     *
     * @param context Контекст, используемый для настройки.
     * @return Экземпляр [UserProfileApi].
     */
    fun getUserProfileApi(context: Context): UserProfileApi {
        return createRetrofit(context).create(UserProfileApi::class.java)
    }
}