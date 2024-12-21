package ru.focsit.mobile.api.user

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.focsit.mobile.data.Artist
import ru.focsit.mobile.data.Track

interface TrackUserApi {

    // Получить трек по ID
    @GET("/api/user/tracks/{id}")
    fun getTrackById(@Path("id") id: Long): Call<Track>
}