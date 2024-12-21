package ru.focsit.mobile.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.User
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignUpRequest

interface YourLibraryApi {

    @POST("/api/your-library/logout")
    fun logout(): Call<String>

    @GET("/api/your-library/my-profile")
    @Headers("Authorization: Bearer {access_token}")
    fun getUserProfile(): Call<User>

    @POST("/api/your-library/create-playlist")
    @Multipart
    fun createPlaylist(
        @Part file: MultipartBody.Part,
        @Part("playlist") playlist: Playlist
    ): Call<Playlist>

    @GET("/api/your-library/my-playlists")
    fun getMyPlaylists(): Call<List<Playlist>>

    @GET("/api/your-library/search-my-playlists")
    fun searchMyPlaylists(@Query("query") query: String): Call<List<Playlist>>

    @POST("/api/your-library/create-lopify-playlist")
    @Multipart
    fun createStandardPlaylist(
        @Part file: MultipartBody.Part,
        @Part("playlist") playlist: Playlist
    ): Call<Playlist>

    @GET("/api/your-library/lopify-playlists")
    fun getStandardPlaylists(): Call<List<Playlist>>

    @GET("/api/your-library/search-lopify-playlists")
    fun searchStandardPlaylists(@Query("query") query: String): Call<List<Playlist>>

    @POST("/api/your-library/create-user")
    fun registerUser(@Body request: SignUpRequest): Call<JwtAuthenticationResponse>

    @GET("/api/your-library/users")
    fun getUsers(): Call<List<User>>
}