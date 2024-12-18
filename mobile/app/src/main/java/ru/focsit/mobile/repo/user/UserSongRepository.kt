package ru.focsit.mobile.repo.user

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.user.UserSongApi
import ru.focsit.mobile.data.*

class UserSongRepository(context: Context) {
    private val api: UserSongApi = RetrofitClient.getUserSongApi(context)

    fun getAllSongs(callback: (List<SongDto>?) -> Unit) {
        api.getAllSongs().enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun searchSongs(album: String?, artist: String?, name: String?, callback: (List<SongDto>?) -> Unit) {
        api.searchSongs(album, artist, name).enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun addSongToFavorites(songId: Long, callback: (Boolean) -> Unit) {
        api.addSongToFavorites(songId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun removeSongFromFavorites(songId: Long, callback: (Boolean) -> Unit) {
        api.removeSongFromFavorites(songId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getUserFavorites(callback: (List<SongDto>?) -> Unit) {
        api.getUserFavorites().enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun addCommentToSong(songId: Long, content: String, callback: (CommentDto?) -> Unit) {
        api.addCommentToSong(songId, content).enqueue(object : Callback<CommentDto> {
            override fun onResponse(call: Call<CommentDto>, response: Response<CommentDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<CommentDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getCommentsForSong(songId: Long, callback: (List<CommentDto>?) -> Unit) {
        api.getCommentsForSong(songId).enqueue(object : Callback<List<CommentDto>> {
            override fun onResponse(call: Call<List<CommentDto>>, response: Response<List<CommentDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<CommentDto>>, t: Throwable) {
                callback(null)
            }
        })
    }
}