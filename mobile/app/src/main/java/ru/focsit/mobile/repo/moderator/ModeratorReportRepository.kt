package ru.focsit.mobile.repo.moderator

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.SongDto

class ModeratorReportRepository(private val context: Context) {

    private val api = RetrofitClient.getModeratorReportApi(context)

    /**
     * Метод для получения топ-10 песен по количеству добавлений в избранное.
     *
     * @param callback Функция обратного вызова с результатом.
     */
    fun getTop10Songs(callback: (List<SongDto>?) -> Unit) {
        api.getTop10Songs().enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<SongDto>>, t: Throwable) {
                callback(null)
            }
        })
    }
}