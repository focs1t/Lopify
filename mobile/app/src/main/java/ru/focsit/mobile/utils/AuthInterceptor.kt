package ru.focsit.mobile.utils

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Интерцептор для добавления токена авторизации в заголовок каждого HTTP-запроса.
 *
 * Этот класс реализует интерфейс [Interceptor] и используется для перехвата и модификации
 * HTTP-запросов, добавляя токен авторизации в заголовок "Authorization" перед отправкой
 * запроса на сервер. Токен передается в виде строки в формате "Bearer <token>".
 *
 * @param token Токен авторизации, который будет добавлен в заголовки запросов.
 */
class AuthInterceptor(private val token: String) : Interceptor {

    /**
     * Перехватывает запрос, добавляет в него заголовок авторизации и выполняет запрос.
     *
     * Этот метод вызывается при каждом запросе. Он добавляет токен авторизации в заголовок
     * запроса и продолжает выполнение запроса с модифицированным заголовком.
     *
     * @param chain Цепочка интерцепторов, через которую проходит запрос.
     * @return Ответ от сервера после выполнения запроса.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        // Добавляем токен в заголовок каждого запроса
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token") // Передаем токен
            .build()

        // Выполняем запрос с новым заголовком
        return chain.proceed(newRequest)
    }
}