package ru.focsit.mobile.data

import java.time.LocalDateTime

data class Comment(
    val commentId: Long,
    val commentUser: User,
    val commentAlbum: Album,
    val commentText: String,
    val commentDate: LocalDateTime
)