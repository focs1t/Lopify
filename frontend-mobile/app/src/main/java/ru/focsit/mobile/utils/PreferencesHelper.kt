package ru.focsit.mobile.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_ROLE = "user_role"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Сохраняем токен
    fun saveToken(context: Context, token: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    // Получаем токен
    fun getToken(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_TOKEN, null)
    }

    // Удаляем токен
    fun clearToken(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(KEY_TOKEN).apply()
    }

    // Сохраняем роль
    fun saveRole(context: Context, role: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_ROLE, role).apply()
    }

    // Получаем роль
    fun getRole(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_ROLE, null)
    }

    // Удаляем роль
    fun clearRole(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(KEY_ROLE).apply()
    }

    // Проверка ролей
    fun isAdmin(context: Context): Boolean {
        val role = getRole(context)
        return role == "ROLE_ADMIN"
    }

    fun isModerator(context: Context): Boolean {
        val role = getRole(context)
        return role == "ROLE_MODERATOR"
    }

    fun isUser(context: Context): Boolean {
        val role = getRole(context)
        return role == "ROLE_USER"
    }
}