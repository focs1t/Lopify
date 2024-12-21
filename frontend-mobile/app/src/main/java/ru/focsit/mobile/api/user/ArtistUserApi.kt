package ru.focsit.mobile.api.user

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.focsit.mobile.data.Artist
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Track

interface ArtistUserApi {

    @GET("/api/user/artists/{id}")
    fun getArtistById(@Path("id") id: Long): Call<Artist>

    @GET("/api/user/artists/{id}/albums")
    fun getAlbumsByArtist(@Path("id") id: Long): Call<List<Album>>

    @GET("/api/user/artists/{id}/tracks")
    fun getTracksByArtist(@Path("id") id: Long): Call<List<Track>>
}