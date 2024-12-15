package ru.focsit.mobile.api.admin

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Country

interface CountryApi {

    // Получить все страны
    @GET("/api/admin/countries")
    fun getCountries(): Call<List<Country>>

    // Создать страну
    @POST("/api/admin/countries")
    fun createCountry(@Body country: Country): Call<Country>

    // Удалить страну по ID
    @DELETE("/api/admin/countries/{id}")
    fun deleteCountry(@Path("id") id: Long): Call<Void>
}