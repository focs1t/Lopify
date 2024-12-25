package ru.focsit.mobile.api.moderator

import retrofit2.Call
import retrofit2.http.GET
import ru.focsit.mobile.data.SongDto

/**
 * API для получения отчетов модератора.
 */
interface ModeratorReportApi {
    /**
     * Получает топ-10 песен по количеству добавлений в избранное.
     *
     * @return Список [SongDto].
     */
    @GET("/moderator/reports/top10-songs")
    fun getTop10Songs(): Call<List<SongDto>>
}