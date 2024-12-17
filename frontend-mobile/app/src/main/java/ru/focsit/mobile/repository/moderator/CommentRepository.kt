package ru.focsit.mobile.repository.moderator

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Comment

class CommentRepository(private val context: Context) {
    private val commentApi = RetrofitClient.getCommentApi(context)

    // Получение всех комментариев
    fun getComments(callback: (List<Comment>?) -> Unit) {
        commentApi.getComments().enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("CommentRepository", "Ошибка получения комментариев: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.e("CommentRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Удаление комментария
    fun deleteComment(id: Long, callback: (Boolean) -> Unit) {
        commentApi.deleteComment(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("CommentRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }
}