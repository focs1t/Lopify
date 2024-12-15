package ru.focsit.mobile.repository.moderator

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.User

class UserProfileRepository {

    private val userProfileApi = RetrofitClient.userProfileApi

    // Получить всех пользователей
    fun getAllUsers(callback: (List<User>?) -> Unit) {
        userProfileApi.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("UserRepository", "Ошибка получения пользователей: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Получить пользователя по ID
    fun getUserById(id: Long, callback: (User?) -> Unit) {
        userProfileApi.getUserById(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("UserRepository", "Ошибка получения пользователя: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }

    // Удалить пользователя
    fun deleteUser(id: Long, callback: (Boolean) -> Unit) {
        userProfileApi.deleteUser(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(false)
            }
        })
    }

    // Поиск пользователей
    fun searchUsers(query: String?, callback: (List<User>?) -> Unit) {
        userProfileApi.searchUsers(query).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("UserRepository", "Ошибка поиска пользователей: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(null)
            }
        })
    }
}