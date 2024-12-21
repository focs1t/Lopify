package ru.focsit.mobile.repo.moderator

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.moderator.ModeratorSongApi
import ru.focsit.mobile.data.*

class ModeratorSongRepository(context: Context) {
    private val api: ModeratorSongApi = RetrofitClient.getModeratorSongApi(context)

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

    fun createSong(song: SongDto, callback: (SongDto?) -> Unit) {
        api.createSong(song).enqueue(object : Callback<SongDto> {
            override fun onResponse(call: Call<SongDto>, response: Response<SongDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<SongDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun updateSong(id: Long, song: SongDto, callback: (SongDto?) -> Unit) {
        api.updateSong(id, song).enqueue(object : Callback<SongDto> {
            override fun onResponse(call: Call<SongDto>, response: Response<SongDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<SongDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun deleteSong(id: Long, callback: (Boolean) -> Unit) {
        api.deleteSong(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getCommentsBySong(songId: Long, callback: (List<CommentDto>?) -> Unit) {
        api.getCommentsBySong(songId).enqueue(object : Callback<List<CommentDto>> {
            override fun onResponse(call: Call<List<CommentDto>>, response: Response<List<CommentDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<CommentDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun deleteComment(songId: Long, commentId: Long, callback: (Boolean) -> Unit) {
        api.deleteComment(songId, commentId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }
}