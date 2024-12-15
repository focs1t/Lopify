package ru.focsit.mobile.api.admin

import retrofit2.Call
import retrofit2.http.*
import ru.focsit.mobile.data.Role

interface RoleApi {

    // Получить все роли
    @GET("/api/admin/roles")
    fun getRoles(): Call<List<Role>>

    // Создать новую роль
    @POST("/api/admin/roles")
    fun createRole(@Body role: Role): Call<Role>

    // Удалить роль по ID
    @DELETE("/api/admin/roles/{id}")
    fun deleteRole(@Path("id") id: Long): Call<Void>
}