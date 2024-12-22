package ru.focsit.mobile.data

/**
 * Данные комментария, связанного с песней.
 * Используется для хранения информации о комментарии пользователя на песню.
 *
 * @property id Идентификатор комментария.
 * @property content Содержимое комментария.
 * @property userId Идентификатор пользователя, который оставил комментарий.
 * @property username Имя пользователя, который оставил комментарий.
 * @property songId Идентификатор песни, к которой оставлен комментарий.
 * @property songName Название песни, к которой оставлен комментарий.
 */
data class CommentDto(
    val id: Long,
    val content: String,
    val userId: Long,
    val username: String,
    val songId: Long,
    val songName: String
)