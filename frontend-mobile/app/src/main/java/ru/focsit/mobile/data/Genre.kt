package ru.focsit.mobile.data

data class Genre(
    val genreId: Long,
    val genreName: String,
    val tracks: List<Track>,
    val albums: List<Album>
)