package ru.focsit.mobile.repository.moderator

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.Track

class PlaylistRepository {

    private val playlistApi = RetrofitClient.playlistApi

    // Получить плейлист по ID
    fun getPlaylistById(id: Long, callback: (Playlist?) -> Unit) {
        playlistApi.getPlaylistById(id).enqueue(object : Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("PlaylistRepository", "Ошибка получения плейлиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                Log.e("PlaylistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Создать новый плейлист
    fun createPlaylist(file: MultipartBody.Part, playlist: Playlist, callback: (Playlist?) -> Unit) {
        playlistApi.createPlaylist(file, playlist).enqueue(object : Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("PlaylistRepository", "Ошибка создания плейлиста: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                Log.e("PlaylistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Обновить плейлист
    fun updatePlaylist(id: Long, file: MultipartBody.Part, playlist: Playlist, callback: (Playlist?) -> Unit) {
        playlistApi.updatePlaylist(id, file, playlist).enqueue(object : Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("PlaylistRepository", "Ошибка обновления плейлиста: ${response.code()} - ${response.message()}")
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
        playlistApi.deletePlaylist(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("PlaylistRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }

    // Поиск треков в плейлисте
    fun searchTracks(id: Long, query: String?, callback: (List<Track>?) -> Unit) {
        playlistApi.searchTracks(id, query).enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("PlaylistRepository", "Ошибка поиска треков: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                Log.e("PlaylistRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }
}