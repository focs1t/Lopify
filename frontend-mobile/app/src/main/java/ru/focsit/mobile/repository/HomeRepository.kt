package ru.focsit.mobile.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.User
import ru.focsit.mobile.data.Playlist

class HomeRepository {
    private val homeApi = RetrofitClient.homeApi

    // Method for getting the user profile
    fun getUserProfile(callback: (User?) -> Unit) {
        homeApi.getUserProfile().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the user object
                } else {
                    Log.e("HomeRepository", "Error fetching profile: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("HomeRepository", "Network error: ${t.message}")
                callback(null)
            }
        })
    }

    // Method for getting the liked tracks (favorites)
    fun getLikedTracks(callback: (Playlist?) -> Unit) {
        homeApi.getLikedTracks().enqueue(object : Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the playlist object
                } else {
                    Log.e("HomeRepository", "Error fetching favorites: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                Log.e("HomeRepository", "Network error: ${t.message}")
                callback(null)
            }
        })
    }

    // Method for logging out
    fun logout(callback: (String?) -> Unit) {
        homeApi.logout().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the logout success message
                } else {
                    Log.e("HomeRepository", "Error during logout: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("HomeRepository", "Network error during logout: ${t.message}")
                callback(null)
            }
        })
    }
}