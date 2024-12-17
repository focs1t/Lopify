package ru.focsit.mobile

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.focsit.mobile.api.AuthApi
import ru.focsit.mobile.api.HomeApi
import ru.focsit.mobile.api.SearchApi
import ru.focsit.mobile.api.YourLibraryApi
import ru.focsit.mobile.api.admin.CountryApi
import ru.focsit.mobile.api.admin.GenreApi
import ru.focsit.mobile.api.admin.RoleApi
import ru.focsit.mobile.api.admin.UserApi
import ru.focsit.mobile.api.moderator.AlbumApi
import ru.focsit.mobile.api.moderator.ArtistApi
import ru.focsit.mobile.api.moderator.CommentApi
import ru.focsit.mobile.api.moderator.PlaylistApi
import ru.focsit.mobile.api.moderator.TrackApi
import ru.focsit.mobile.api.moderator.UserProfileApi
import ru.focsit.mobile.api.user.AlbumUserApi
import ru.focsit.mobile.api.user.ArtistUserApi
import ru.focsit.mobile.api.user.PlaylistUserApi
import ru.focsit.mobile.api.user.ProfileApi
import ru.focsit.mobile.api.user.TrackUserApi
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

    fun getCountryApi(context: Context): CountryApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CountryApi::class.java)
    }

    fun getGenreApi(context: Context): GenreApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GenreApi::class.java)
    }

    fun getRoleApi(context: Context): RoleApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RoleApi::class.java)
    }

    fun getUserApi(context: Context): UserApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(UserApi::class.java)
    }

    fun getAlbumApi(context: Context): AlbumApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AlbumApi::class.java)
    }

    fun getArtistApi(context: Context): ArtistApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ArtistApi::class.java)
    }

    fun getCommentApi(context: Context): CommentApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CommentApi::class.java)
    }

    fun getPlaylistApi(context: Context): PlaylistApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PlaylistApi::class.java)
    }

    fun getTrackApi(context: Context): TrackApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TrackApi::class.java)
    }

    fun getUserProfileApi(context: Context): UserProfileApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(UserProfileApi::class.java)
    }

    fun getAlbumUserApi(context: Context): AlbumUserApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AlbumUserApi::class.java)
    }

    fun getArtistUserApi(context: Context): ArtistUserApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ArtistUserApi::class.java)
    }

    fun getPlaylistUserApi(context: Context): PlaylistUserApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PlaylistUserApi::class.java)
    }

    fun getProfileApi(context: Context): ProfileApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ProfileApi::class.java)
    }

    fun getTrackUserApi(context: Context): TrackUserApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TrackUserApi::class.java)
    }

    fun getHomeApi(context: Context): HomeApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(HomeApi::class.java)
    }

    fun getSearchApi(context: Context): SearchApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SearchApi::class.java)
    }

    fun getYourLibraryApi(context: Context): YourLibraryApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(YourLibraryApi::class.java)
    }
}