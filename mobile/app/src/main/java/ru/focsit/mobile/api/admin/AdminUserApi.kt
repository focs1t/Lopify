package ru.focsit.mobile.api.admin

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.*
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignUpRequest

interface AdminUserApi {
    @GET("/api/admin/users")
    fun getAllUsers(): Call<List<UserDto>>

    @DELETE("/api/admin/users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>

    @POST("/api/admin/users")
    fun registerUser(@Body request: SignUpRequest): Call<JwtAuthenticationResponse>
}