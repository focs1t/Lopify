package ru.focsit.mobile.repository.admin

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.User
import ru.focsit.mobile.data.auth.SignUpRequest
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse

class UserRepository {
    private val userApi = RetrofitClient.userApi

    // Метод для получения всех пользователей
    fun getUsers(callback: (List<User>?) -> Unit) {
        userApi.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("UserRepository", "Ошибка получения пользователей: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для получения пользователя по ID
    fun getUserById(id: Long, callback: (User?) -> Unit) {
        userApi.getUserById(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("UserRepository", "Ошибка получения пользователя: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для регистрации нового пользователя
    fun registerUser(request: SignUpRequest, callback: (JwtAuthenticationResponse?) -> Unit) {
        userApi.registerUser(request).enqueue(object : Callback<JwtAuthenticationResponse> {
            override fun onResponse(call: Call<JwtAuthenticationResponse>, response: Response<JwtAuthenticationResponse>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("UserRepository", "Ошибка регистрации: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<JwtAuthenticationResponse>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для обновления пользователя
    fun updateUser(id: Long, userDetails: User, callback: (User?) -> Unit) {
        userApi.updateUser(id, userDetails).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("UserRepository", "Ошибка обновления пользователя: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для удаления пользователя
    fun deleteUser(id: Long, callback: (Boolean) -> Unit) {
        userApi.deleteUser(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем true в callback
                    callback(true)
                } else {
                    // Логирование ошибки
                    Log.e("UserRepository", "Ошибка удаления пользователя: ${response.code()} - ${response.message()}")
                    callback(false) // Передаем false в случае ошибки
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("UserRepository", "Ошибка сети: ${t.message}")
                callback(false) // Передаем false при сетевой ошибке
            }
        })
    }
}