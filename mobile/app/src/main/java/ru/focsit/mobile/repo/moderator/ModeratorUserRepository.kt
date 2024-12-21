package ru.focsit.mobile.repo.moderator

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.moderator.ModeratorUserApi
import ru.focsit.mobile.data.*

class ModeratorUserRepository(context: Context) {
    private val api: ModeratorUserApi = RetrofitClient.getModeratorUserApi(context)

    fun getAllUsers(callback: (List<UserDto>?) -> Unit) {
        api.getAllUsers().enqueue(object : Callback<List<UserDto>> {
            override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getUserById(id: Long, callback: (UserDto?) -> Unit) {
        api.getUserById(id).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getUserFavorites(userId: Long, callback: (List<SongDto>?) -> Unit) {
        api.getUserFavorites(userId).enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getUserComments(userId: Long, callback: (List<CommentDto>?) -> Unit) {
        api.getUserComments(userId).enqueue(object : Callback<List<CommentDto>> {
            override fun onResponse(call: Call<List<CommentDto>>, response: Response<List<CommentDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<CommentDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun deleteUserComment(userId: Long, commentId: Long, callback: (Boolean) -> Unit) {
        api.deleteUserComment(userId, commentId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }
}