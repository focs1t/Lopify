package ru.focsit.mobile.data

import java.time.LocalDate
import java.time.LocalTime

data class Album(
    val albumId: Long,
    val albumName: String,
    val albumDescription: String,
    val albumImageUrl: String,
    val albumReleaseDate: LocalDate,
    val albumDuration: LocalTime?,
    val albumArtist: Artist,
    val albumGenre: Genre,
    val comments: List<Comment>,
    val tracks: List<Track>
)