package ru.focsit.mobile.repository.admin

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Genre

class GenreRepository(private val context: Context) {
    private val genreApi = RetrofitClient.getGenreApi(context)

    // Метод для получения всех жанров
    fun getGenres(callback: (List<Genre>?) -> Unit) {
        genreApi.getGenres().enqueue(object : Callback<List<Genre>> {
            override fun onResponse(call: Call<List<Genre>>, response: Response<List<Genre>>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("GenreRepository", "Ошибка получения жанров: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<List<Genre>>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("GenreRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для создания нового жанра
    fun createGenre(genre: Genre, callback: (Genre?) -> Unit) {
        genreApi.createGenre(genre).enqueue(object : Callback<Genre> {
            override fun onResponse(call: Call<Genre>, response: Response<Genre>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("GenreRepository", "Ошибка создания жанра: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<Genre>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("GenreRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для удаления жанра
    fun deleteGenre(id: Long, callback: (Boolean) -> Unit) {
        genreApi.deleteGenre(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем true в callback
                    callback(true)
                } else {
                    // Логирование ошибки
                    Log.e("GenreRepository", "Ошибка удаления жанра: ${response.code()} - ${response.message()}")
                    callback(false) // Передаем false в случае ошибки
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("GenreRepository", "Ошибка сети: ${t.message}")
                callback(false) // Передаем false при сетевой ошибке
            }
        })
    }
}