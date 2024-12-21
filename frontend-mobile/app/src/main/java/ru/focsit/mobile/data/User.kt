package ru.focsit.mobile.data

import java.util.List

data class User(
    val userId: Long,
    val username: String,
    val password: String,
    val userEmail: String,
    val userRole: Role,
    val userCountry: Country,
    val playlists: List<Playlist>,
    val comments: List<Comment>
)