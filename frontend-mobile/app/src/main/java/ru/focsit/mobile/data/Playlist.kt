package ru.focsit.mobile.data

import java.time.LocalTime

data class Playlist(
    val playlistId: Long,
    val playlistName: String,
    val playlistDescription: String,
    val playlistDuration: LocalTime?,
    val playlistImageUrl: String,
    val playlistUser: User,
    val tracks: List<Track>
)