package ru.focsit.mobile.data

data class CommentDto(
    val id: Long,
    val content: String,
    val userId: Long,
    val username: String,
    val songId: Long,
    val songName: String
)