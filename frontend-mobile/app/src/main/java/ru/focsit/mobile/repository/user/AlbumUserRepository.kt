package ru.focsit.mobile.repository.user

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Comment
import ru.focsit.mobile.data.Track

class AlbumUserRepository(private val context: Context) {
    private val albumUserApi = RetrofitClient.getAlbumUserApi(context)

    // Получить альбом по ID
    fun getAlbumById(id: Long, callback: (Album?) -> Unit) {
        albumUserApi.getAlbumById(id).enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка при получении альбома: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Поиск альбомов
    fun searchAlbums(query: String?, callback: (List<Album>?) -> Unit) {
        albumUserApi.searchAlbums(query).enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка при поиске альбомов: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Создание комментария
    fun createComment(albumId: Long, comment: Comment, callback: (Comment?) -> Unit) {
        albumUserApi.createComment(albumId, comment).enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка при создании комментария: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Удаление комментария
    fun deleteComment(albumId: Long, commentId: Long, callback: (Boolean) -> Unit) {
        albumUserApi.deleteComment(albumId, commentId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети при удалении комментария: ${t.message}")
                callback(false)
            }
        })
    }

    // Поиск треков в альбоме
    fun searchTracks(albumId: Long, query: String?, callback: (List<Track>?) -> Unit) {
        albumUserApi.searchTracks(albumId, query).enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка при поиске треков: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }
}