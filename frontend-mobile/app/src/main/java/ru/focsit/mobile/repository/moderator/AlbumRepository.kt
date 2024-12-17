package ru.focsit.mobile.repository.moderator

import android.content.Context
import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Comment
import ru.focsit.mobile.data.Track

class AlbumRepository(private val context: Context) {
    private val albumApi = RetrofitClient.getAlbumApi(context)

    // Получение всех альбомов
    fun getAlbums(callback: (List<Album>?) -> Unit) {
        albumApi.getAlbums().enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка получения альбомов: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Получение альбома по ID
    fun getAlbumById(id: Long, callback: (Album?) -> Unit) {
        albumApi.getAlbumById(id).enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка получения альбома: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Создание нового альбома
    fun createAlbum(file: MultipartBody.Part, album: Album, callback: (Album?) -> Unit) {
        albumApi.createAlbum(file, album).enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка создания альбома: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Удаление альбома
    fun deleteAlbum(id: Long, callback: (Boolean) -> Unit) {
        albumApi.deleteAlbum(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }

    // Поиск альбомов
    fun searchAlbums(query: String?, callback: (List<Album>?) -> Unit) {
        albumApi.searchAlbums(query).enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка поиска альбомов: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Добавление комментария
    fun createComment(albumId: Long, comment: Comment, callback: (Comment?) -> Unit) {
        albumApi.createComment(albumId, comment).enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка добавления комментария: ${response.code()} - ${response.message()}")
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
        albumApi.deleteComment(albumId, commentId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("AlbumRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }

    // Поиск треков в альбоме
    fun searchTracks(albumId: Long, query: String?, callback: (List<Track>?) -> Unit) {
        albumApi.searchTracks(albumId, query).enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("AlbumRepository", "Ошибка поиска треков: ${response.code()} - ${response.message()}")
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