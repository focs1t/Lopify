package ru.focsit.mobile.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Album
import ru.focsit.mobile.data.Artist
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.Track
import ru.focsit.mobile.data.User
import ru.focsit.mobile.data.SearchResults

class SearchRepository {

    private val searchApi = RetrofitClient.searchApi

    // Method to search across different entities (albums, artists, playlists, etc.)
    fun search(query: String, callback: (SearchResults?) -> Unit) {
        searchApi.search(query).enqueue(object : Callback<SearchResults> {
            override fun onResponse(call: Call<SearchResults>, response: Response<SearchResults>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the search results
                } else {
                    Log.e("SearchRepository", "Error during search: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<SearchResults>, t: Throwable) {
                Log.e("SearchRepository", "Network error during search: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to get albums grouped by genre
    fun getAlbumsByGenres(callback: (Map<String, List<Album>>?) -> Unit) {
        searchApi.getAlbumsByGenres().enqueue(object : Callback<Map<String, List<Album>>> {
            override fun onResponse(call: Call<Map<String, List<Album>>>, response: Response<Map<String, List<Album>>>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the albums by genre
                } else {
                    Log.e("SearchRepository", "Error fetching albums by genre: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Map<String, List<Album>>>, t: Throwable) {
                Log.e("SearchRepository", "Network error fetching albums by genre: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to get the current user profile
    fun getUserProfile(callback: (User?) -> Unit) {
        searchApi.getUserProfile().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the user profile
                } else {
                    Log.e("SearchRepository", "Error fetching user profile: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("SearchRepository", "Network error fetching user profile: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to log out the user
    fun logout(callback: (String?) -> Unit) {
        searchApi.logout().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the logout success message
                } else {
                    Log.e("SearchRepository", "Error during logout: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("SearchRepository", "Network error during logout: ${t.message}")
                callback(null)
            }
        })
    }
}