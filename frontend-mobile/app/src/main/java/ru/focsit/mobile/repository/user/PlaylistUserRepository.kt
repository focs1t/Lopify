package ru.focsit.mobile.repository.user

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.Track

class PlaylistUserRepository {

    private val playlistUserApi = RetrofitClient.playlistUserApi

    // Получить плейлист по ID
    fun getPlaylistById(id: Long, callback: (Playlist?) -> Unit) {
        playlistUserApi.getPlaylistById(id).enqueue(object : Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("PlaylistRepository", "Ошибка при получении плейлиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                Log.e("PlaylistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Поиск треков в плейлисте
    fun searchTracksInPlaylist(id: Long, query: String?, callback: (List<Track>?) -> Unit) {
        playlistUserApi.searchTracksInPlaylist(id, query).enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("PlaylistRepository", "Ошибка при поиске треков: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                Log.e("PlaylistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Обновить плейлист
    fun updatePlaylist(id: Long, file: MultipartBody.Part, playlist: MultipartBody.Part, callback: (Playlist?) -> Unit) {
        playlistUserApi.updatePlaylist(id, file, playlist).enqueue(object : Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("PlaylistRepository", "Ошибка при обновлении плейлиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                Log.e("PlaylistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Удалить плейлист
    fun deletePlaylist(id: Long, callback: (Boolean) -> Unit) {
        playlistUserApi.deletePlaylist(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("PlaylistRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }
}