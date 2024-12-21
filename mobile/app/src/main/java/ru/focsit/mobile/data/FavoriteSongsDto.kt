package ru.focsit.mobile.data

data class FavoriteSongsDto(
    val userId: Long,
    val username: String,
    val favoriteSongs: Set<SongDto>
)