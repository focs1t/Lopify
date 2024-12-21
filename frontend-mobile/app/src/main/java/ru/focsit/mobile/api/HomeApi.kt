package ru.focsit.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.User

interface HomeApi {

    @GET("/api/home/my-profile")
    fun getUserProfile(): Call<User>

    @GET("/api/home/favourites")
    fun getLikedTracks(): Call<Playlist>

    @POST("/api/home/logout")
    fun logout(): Call<String>
}