package ru.focsit.mobile.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_TOKEN = "auth_token"

    // Получаем объект SharedPreferences
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

    // Удаляем токен (например, при выходе из системы)
    fun clearToken(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(KEY_TOKEN).apply()
    }
}