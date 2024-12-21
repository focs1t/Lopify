package ru.focsit.mobile.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.focsit.mobile.data.auth.JwtAuthenticationResponse
import ru.focsit.mobile.data.auth.SignInRequest
import ru.focsit.mobile.data.auth.SignUpRequest

interface AuthApi {
    @POST("/api/auth/sign-up")
    fun signUp(@Body request: SignUpRequest): Call<JwtAuthenticationResponse>

    @POST("/api/auth/sign-in")
    fun signIn(@Body request: SignInRequest): Call<JwtAuthenticationResponse>

    @POST("/api/auth/logout")
    fun logout(): Call<Void>
}