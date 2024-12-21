package ru.focsit.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Artist
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.Track
import ru.focsit.mobile.data.User
import ru.focsit.mobile.data.SearchResults

interface SearchApi {

    @GET("/api/search-page/search")
    fun search(@Query("query") query: String): Call<SearchResults>

    @GET("/api/search-page/albums/by-genre")
    fun getAlbumsByGenres(): Call<Map<String, List<Album>>>

    @GET("/api/search-page/my-profile")
    fun getUserProfile(): Call<User>

    @POST("/api/search-page/logout")
    fun logout(): Call<String>
}