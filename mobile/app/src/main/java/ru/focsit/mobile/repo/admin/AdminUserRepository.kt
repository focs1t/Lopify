package ru.focsit.mobile.repo.admin

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.api.admin.AdminUserApi
import ru.focsit.mobile.data.*
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignUpRequest

class AdminUserRepository(context: Context) {
    private val api: AdminUserApi = RetrofitClient.getAdminUserApi(context)

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

    fun deleteUser(id: Long, callback: (Boolean) -> Unit) {
        api.deleteUser(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun registerUser(request: SignUpRequest, callback: (JwtAuthenticationResponse?) -> Unit) {
        api.registerUser(request).enqueue(object : Callback<JwtAuthenticationResponse> {
            override fun onResponse(call: Call<JwtAuthenticationResponse>, response: Response<JwtAuthenticationResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<JwtAuthenticationResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}