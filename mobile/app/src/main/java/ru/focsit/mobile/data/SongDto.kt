package ru.focsit.mobile.data

data class SongDto(
    val id: Long? = null,
    val name: String,
    val genre: String,
    val artist: String,
    val album: String,
    val duration: Int
)