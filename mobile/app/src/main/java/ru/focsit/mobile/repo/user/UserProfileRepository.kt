package ru.focsit.mobile.repo.user

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.user.UserProfileApi
import ru.focsit.mobile.data.*

class UserProfileRepository(context: Context) {
    private val api: UserProfileApi = RetrofitClient.getUserProfileApi(context)

    fun getCurrentUser(callback: (UserDto?) -> Unit) {
        api.getCurrentUser().enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getMyComments(callback: (List<CommentDto>?) -> Unit) {
        api.getMyComments().enqueue(object : Callback<List<CommentDto>> {
            override fun onResponse(call: Call<List<CommentDto>>, response: Response<List<CommentDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<CommentDto>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getMyFavorites(callback: (List<SongDto>?) -> Unit) {
        api.getMyFavorites().enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
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
}