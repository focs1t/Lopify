package ru.focsit.mobile.data

data class Artist(
    val artistId: Long,
    val artistName: String,
    val artistBio: String?,
    val artistCountry: Country,
    val tracks: List<Track>,
    val albums: List<Album>
)