package ru.focsit.mobile.data

import java.time.LocalDate
import java.time.LocalTime

data class Track(
    val trackId: Long,
    val trackName: String,
    val trackDate: LocalDate,
    val trackImageUrl: String,
    val trackDuration: LocalTime?,
    val trackAlbum: Album,
    val trackGenre: Genre,
    val artists: List<Artist>,
    val playlists: List<Playlist>
)