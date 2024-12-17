package ru.focsit.mobile.repository

import android.content.Context
import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Playlist
import ru.focsit.mobile.data.User
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignUpRequest

class YourLibraryRepository(private val context: Context) {
    private val yourLibraryApi = RetrofitClient.getYourLibraryApi(context)

    // Method to log out the user
    fun logout(callback: (String?) -> Unit) {
        yourLibraryApi.logout().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the success message
                } else {
                    Log.e("YourLibraryRepository", "Error during logout: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("YourLibraryRepository", "Network error during logout: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to get the user profile
    fun getUserProfile(callback: (User?) -> Unit) {
        yourLibraryApi.getUserProfile().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Pass the user profile
                } else {
                    Log.e("YourLibraryRepository", "Error fetching user profile: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("YourLibraryRepository", "Network error fetching user profile: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to create a playlist
    fun createPlaylist(file: MultipartBody.Part, playlist: Playlist, callback: (Playlist?) -> Unit) {
        yourLibraryApi.createPlaylist(file, playlist).enqueue(object : Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Return the created playlist
                } else {
                    Log.e("YourLibraryRepository", "Error creating playlist: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                Log.e("YourLibraryRepository", "Network error creating playlist: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to get the user's playlists
    fun getMyPlaylists(callback: (List<Playlist>?) -> Unit) {
        yourLibraryApi.getMyPlaylists().enqueue(object : Callback<List<Playlist>> {
            override fun onResponse(call: Call<List<Playlist>>, response: Response<List<Playlist>>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Return the user's playlists
                } else {
                    Log.e("YourLibraryRepository", "Error fetching playlists: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Playlist>>, t: Throwable) {
                Log.e("YourLibraryRepository", "Network error fetching playlists: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to search for playlists
    fun searchMyPlaylists(query: String, callback: (List<Playlist>?) -> Unit) {
        yourLibraryApi.searchMyPlaylists(query).enqueue(object : Callback<List<Playlist>> {
            override fun onResponse(call: Call<List<Playlist>>, response: Response<List<Playlist>>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Return the search results
                } else {
                    Log.e("YourLibraryRepository", "Error searching playlists: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Playlist>>, t: Throwable) {
                Log.e("YourLibraryRepository", "Network error searching playlists: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to register a new user
    fun registerUser(request: SignUpRequest, callback: (JwtAuthenticationResponse?) -> Unit) {
        yourLibraryApi.registerUser(request).enqueue(object : Callback<JwtAuthenticationResponse> {
            override fun onResponse(call: Call<JwtAuthenticationResponse>, response: Response<JwtAuthenticationResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Return the authentication response
                } else {
                    Log.e("YourLibraryRepository", "Error registering user: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<JwtAuthenticationResponse>, t: Throwable) {
                Log.e("YourLibraryRepository", "Network error during registration: ${t.message}")
                callback(null)
            }
        })
    }

    // Method to get all users (for admin)
    fun getUsers(callback: (List<User>?) -> Unit) {
        yourLibraryApi.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Return the list of users
                } else {
                    Log.e("YourLibraryRepository", "Error fetching users: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("YourLibraryRepository", "Network error fetching users: ${t.message}")
                callback(null)
            }
        })
    }
}