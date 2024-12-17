package ru.focsit.mobile.repository.user

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Artist
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Track

class ArtistUserRepository(private val context: Context) {
    private val artistUserApi = RetrofitClient.getArtistUserApi(context)

    // Получить артиста по ID
    fun getArtistById(id: Long, callback: (Artist?) -> Unit) {
        artistUserApi.getArtistById(id).enqueue(object : Callback<Artist> {
            override fun onResponse(call: Call<Artist>, response: Response<Artist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ArtistRepository", "Ошибка при получении артиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Artist>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Получить альбомы артиста
    fun getAlbumsByArtist(id: Long, callback: (List<Album>?) -> Unit) {
        artistUserApi.getAlbumsByArtist(id).enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ArtistRepository", "Ошибка при получении альбомов артиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Получить треки артиста
    fun getTracksByArtist(id: Long, callback: (List<Track>?) -> Unit) {
        artistUserApi.getTracksByArtist(id).enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ArtistRepository", "Ошибка при получении треков артиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }
}