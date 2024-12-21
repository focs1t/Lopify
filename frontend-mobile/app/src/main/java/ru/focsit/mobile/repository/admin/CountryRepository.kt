package ru.focsit.mobile.repository.admin

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.RetrofitClient
import ru.focsit.mobile.data.Country

class CountryRepository {
    private val countryApi = RetrofitClient.countryApi

    // Метод для получения всех стран
    fun getCountries(callback: (List<Country>?) -> Unit) {
        countryApi.getCountries().enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("CountryRepository", "Ошибка получения стран: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("CountryRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для создания новой страны
    fun createCountry(country: Country, callback: (Country?) -> Unit) {
        countryApi.createCountry(country).enqueue(object : Callback<Country> {
            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем данные в callback
                    callback(response.body())
                } else {
                    // Логирование ошибки
                    Log.e("CountryRepository", "Ошибка создания страны: ${response.code()} - ${response.message()}")
                    callback(null) // Передаем null в случае ошибки
                }
            }

            override fun onFailure(call: Call<Country>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("CountryRepository", "Ошибка сети: ${t.message}")
                callback(null) // Передаем null при сетевой ошибке
            }
        })
    }

    // Метод для удаления страны
    fun deleteCountry(id: Long, callback: (Boolean) -> Unit) {
        countryApi.deleteCountry(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Если запрос успешен, передаем true в callback
                    callback(true)
                } else {
                    // Логирование ошибки
                    Log.e("CountryRepository", "Ошибка удаления страны: ${response.code()} - ${response.message()}")
                    callback(false) // Передаем false в случае ошибки
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Логирование ошибки при запросе
                Log.e("CountryRepository", "Ошибка сети: ${t.message}")
                callback(false) // Передаем false при сетевой ошибке
            }
        })
    }
}