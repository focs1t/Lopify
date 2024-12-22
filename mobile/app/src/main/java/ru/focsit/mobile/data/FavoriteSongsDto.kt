package ru.focsit.mobile.data

/**
 * Данные о любимых песнях пользователя.
 * Используется для хранения информации о песнях, добавленных в избранное.
 *
 * @property userId Идентификатор пользователя, чьи любимые песни представлены.
 * @property username Имя пользователя, чьи любимые песни представлены.
 * @property favoriteSongs Множество песен, добавленных в избранное.
 */
data class FavoriteSongsDto(
    val userId: Long,
    val username: String,
    val favoriteSongs: Set<SongDto>
)