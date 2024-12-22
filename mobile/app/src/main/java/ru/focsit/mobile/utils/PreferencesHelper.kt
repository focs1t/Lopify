package ru.focsit.mobile.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Утилита для работы с SharedPreferences, которая используется для хранения токена и роли пользователя.
 */
object PreferencesHelper {
    // Имя файла SharedPreferences
    private const val PREFS_NAME = "user_prefs"

    // Ключ для токена
    private const val KEY_TOKEN = "auth_token"

    // Ключ для роли пользователя
    private const val KEY_ROLE = "user_role"

    /**
     * Получает объект SharedPreferences для данного контекста.
     *
     * @param context Контекст приложения.
     * @return SharedPreferences объект.
     */
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Сохраняет токен в SharedPreferences.
     *
     * @param context Контекст приложения.
     * @param token Токен, который нужно сохранить.
     */
    fun saveToken(context: Context, token: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    /**
     * Получает токен из SharedPreferences.
     *
     * @param context Контекст приложения.
     * @return Токен, сохраненный в SharedPreferences, или null, если токен не найден.
     */
    fun getToken(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_TOKEN, null)
    }

    /**
     * Удаляет токен из SharedPreferences.
     *
     * @param context Контекст приложения.
     */
    fun clearToken(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(KEY_TOKEN).apply()
    }

    /**
     * Сохраняет роль пользователя в SharedPreferences.
     *
     * @param context Контекст приложения.
     * @param role Роль пользователя, которую нужно сохранить.
     */
    fun saveRole(context: Context, role: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_ROLE, role).apply()
    }

    /**
     * Получает роль пользователя из SharedPreferences.
     *
     * @param context Контекст приложения.
     * @return Роль пользователя, сохраненная в SharedPreferences, или null, если роль не найдена.
     */
    fun getRole(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_ROLE, null)
    }

    /**
     * Удаляет роль пользователя из SharedPreferences.
     *
     * @param context Контекст приложения.
     */
    fun clearRole(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(KEY_ROLE).apply()
    }
}