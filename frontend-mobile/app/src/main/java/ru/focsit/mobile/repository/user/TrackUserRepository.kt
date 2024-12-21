package ru.focsit.mobile.repository.user

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Track

class TrackUserRepository {

    private val trackUserApi = RetrofitClient.trackUserApi

    // Получить трек по ID
    fun getTrackById(id: Long, callback: (Track?) -> Unit) {
        trackUserApi.getTrackById(id).enqueue(object : Callback<Track> {
            override fun onResponse(call: Call<Track>, response: Response<Track>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("TrackRepository", "Ошибка при получении трека: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Track>, t: Throwable) {
                Log.e("TrackRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }
}