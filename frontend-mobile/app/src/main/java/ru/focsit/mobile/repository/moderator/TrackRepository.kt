package ru.focsit.mobile.repository.moderator

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Track

class TrackRepository {

    private val trackApi = RetrofitClient.trackApi

    // Получить все треки
    fun getAllTracks(callback: (List<Track>?) -> Unit) {
        trackApi.getAllTracks().enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("TrackRepository", "Ошибка получения треков: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                Log.e("TrackRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Получить трек по ID
    fun getTrackById(id: Long, callback: (Track?) -> Unit) {
        trackApi.getTrackById(id).enqueue(object : Callback<Track> {
            override fun onResponse(call: Call<Track>, response: Response<Track>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("TrackRepository", "Ошибка получения трека: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Track>, t: Throwable) {
                Log.e("TrackRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Создать новый трек
    fun createTrack(file: MultipartBody.Part, track: Track, callback: (Track?) -> Unit) {
        trackApi.createTrack(file, track).enqueue(object : Callback<Track> {
            override fun onResponse(call: Call<Track>, response: Response<Track>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("TrackRepository", "Ошибка создания трека: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Track>, t: Throwable) {
                Log.e("TrackRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Удалить трек
    fun deleteTrack(id: Long, callback: (Boolean) -> Unit) {
        trackApi.deleteTrack(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("TrackRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }

    // Поиск треков
    fun searchTracks(query: String?, callback: (List<Track>?) -> Unit) {
        trackApi.searchTracks(query).enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("TrackRepository", "Ошибка поиска треков: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                Log.e("TrackRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }
}