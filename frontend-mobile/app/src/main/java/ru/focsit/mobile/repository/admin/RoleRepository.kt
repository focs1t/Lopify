package ru.focsit.mobile.repository.admin

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Role

class RoleRepository {
    private val roleApi = RetrofitClient.roleApi

    // Метод для получения всех ролей
    fun getRoles(callback: (List<Role>?) -> Unit) {
        roleApi.getRoles().enqueue(object : Callback<List<Role>> {
            override fun onResponse(call: Call<List<Role>>, response: Response<List<Role>>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("RoleRepository", "Ошибка получения ролей: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<List<Role>>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("RoleRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для создания новой роли
    fun createRole(role: Role, callback: (Role?) -> Unit) {
        roleApi.createRole(role).enqueue(object : Callback<Role> {
            override fun onResponse(call: Call<Role>, response: Response<Role>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("RoleRepository", "Ошибка создания роли: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<Role>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("RoleRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для удаления роли
    fun deleteRole(id: Long, callback: (Boolean) -> Unit) {
        roleApi.deleteRole(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем true в callback
                    callback(true)
                } else {
                    // Логирование ошибки
                    Log.e("RoleRepository", "Ошибка удаления роли: ${response.code()} - ${response.message()}")
                    callback(false) // Передаем false в случае ошибки
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("RoleRepository", "Ошибка сети: ${t.message}")
                callback(false) // Передаем false при сетевой ошибке
            }
        })
    }
}