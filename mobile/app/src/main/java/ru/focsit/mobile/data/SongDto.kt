package ru.focsit.mobile.data

/**
 * Данные о песне.
 * Используется для представления информации о песне, включая её название, жанр, исполнителя и продолжительность.
 *
 * @property id Уникальный идентификатор песни.
 * @property name Название песни.
 * @property genre Жанр песни.
 * @property artist Исполнитель песни.
 * @property album Альбом, в который входит песня.
 * @property duration Продолжительность песни в секундах.
 */
data class SongDto(
    val id: Long? = null,
    val name: String,
    val genre: String,
    val artist: String,
    val album: String,
    val duration: Int
)