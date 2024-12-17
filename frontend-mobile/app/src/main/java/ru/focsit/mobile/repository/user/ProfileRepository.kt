package ru.focsit.mobile.repository.user

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.User

class ProfileRepository(private val context: Context) {
    private val profileApi = RetrofitClient.getProfileApi(context)

    // Получить всех пользователей
    fun getAllUsers(callback: (List<User>?) -> Unit) {
        profileApi.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ProfileRepository", "Ошибка при получении списка пользователей: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("ProfileRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Получить пользователя по ID
    fun getUserById(id: Long, callback: (User?) -> Unit) {
        profileApi.getUserById(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ProfileRepository", "Ошибка при получении пользователя: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("ProfileRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }
}