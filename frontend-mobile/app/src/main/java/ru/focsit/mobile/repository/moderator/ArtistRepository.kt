package ru.focsit.mobile.repository.moderator

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Artist

class ArtistRepository(private val context: Context) {
    private val artistApi = RetrofitClient.getArtistApi(context)

    // Получение всех артистов
    fun getArtists(callback: (List<Artist>?) -> Unit) {
        artistApi.getArtists().enqueue(object : Callback<List<Artist>> {
            override fun onResponse(call: Call<List<Artist>>, response: Response<List<Artist>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ArtistRepository", "Ошибка получения артистов: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Artist>>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Получение артиста по ID
    fun getArtistById(id: Long, callback: (Artist?) -> Unit) {
        artistApi.getArtistById(id).enqueue(object : Callback<Artist> {
            override fun onResponse(call: Call<Artist>, response: Response<Artist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ArtistRepository", "Ошибка получения артиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Artist>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Создание нового артиста
    fun createArtist(artist: Artist, callback: (Artist?) -> Unit) {
        artistApi.createArtist(artist).enqueue(object : Callback<Artist> {
            override fun onResponse(call: Call<Artist>, response: Response<Artist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ArtistRepository", "Ошибка создания артиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Artist>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Обновление данных артиста
    fun updateArtist(id: Long, artist: Artist, callback: (Artist?) -> Unit) {
        artistApi.updateArtist(id, artist).enqueue(object : Callback<Artist> {
            override fun onResponse(call: Call<Artist>, response: Response<Artist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ArtistRepository", "Ошибка обновления артиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Artist>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Удаление артиста
    fun deleteArtist(id: Long, callback: (Boolean) -> Unit) {
        artistApi.deleteArtist(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }

    // Поиск артистов
    fun searchArtists(query: String?, callback: (List<Artist>?) -> Unit) {
        artistApi.searchArtists(query).enqueue(object : Callback<List<Artist>> {
            override fun onResponse(call: Call<List<Artist>>, response: Response<List<Artist>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ArtistRepository", "Ошибка поиска артистов: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Artist>>, t: Throwable) {
                Log.e("ArtistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }
}