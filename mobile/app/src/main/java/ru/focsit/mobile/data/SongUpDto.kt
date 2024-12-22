package ru.focsit.mobile.data

/**
 * Данные для добавления новой песни.
 * Используется для отправки информации о новой песне, которую необходимо добавить в систему.
 *
 * @property name Название песни.
 * @property genre Жанр песни.
 * @property artist Исполнитель песни.
 * @property album Альбом, в который входит песня.
 * @property duration Продолжительность песни в секундах.
 */
data class SongUpDto(
    val name: String,
    val genre: String,
    val artist: String,
    val album: String,
    val duration: Int
)