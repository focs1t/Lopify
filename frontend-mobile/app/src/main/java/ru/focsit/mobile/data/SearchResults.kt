package ru.focsit.mobile.data

data class SearchResults(
    val albums: List<Album>,
    val artists: List<Artist>,
    val playlists: List<Playlist>,
    val tracks: List<Track>,
    val users: List<User>
)