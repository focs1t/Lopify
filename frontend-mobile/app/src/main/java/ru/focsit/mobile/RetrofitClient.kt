package ru.focsit.mobile

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

object RetrofitClient {
    private const val BASE_URL = "http://192.168.2.103:8080"

    val authApi: AuthApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AuthApi::class.java)
    }

    val countryApi: CountryApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(CountryApi::class.java)
    }

    val genreApi: GenreApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GenreApi::class.java)
    }

    val roleApi: RoleApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RoleApi::class.java)
    }

    val userApi: UserApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserApi::class.java)
    }

    val albumApi: AlbumApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AlbumApi::class.java)
    }

    val artistApi: ArtistApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ArtistApi::class.java)
    }

    val commentApi: CommentApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(CommentApi::class.java)
    }

    val playlistApi: PlaylistApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(PlaylistApi::class.java)
    }

    val trackApi: TrackApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TrackApi::class.java)
    }

    val userProfileApi: UserProfileApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserProfileApi::class.java)
    }

    val albumUserApi: AlbumUserApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AlbumUserApi::class.java)
    }

    val artistUserApi: ArtistUserApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ArtistUserApi::class.java)
    }

    val playlistUserApi: PlaylistUserApi by lazy {
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(PlaylistUserApi::class.java)
    }

    val profileApi: ProfileApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ProfileApi::class.java)
    }

    val trackUserApi: TrackUserApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TrackUserApi::class.java)
    }

    val homeApi: HomeApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HomeApi::class.java)
    }

    val searchApi: SearchApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SearchApi::class.java)
    }

    val yourLibraryApi: YourLibraryApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(YourLibraryApi::class.java)
    }
}