package ru.focsit.mobile.data

data class SongUpDto(
    val name: String,
    val genre: String,
    val artist: String,
    val album: String,
    val duration: Int
)