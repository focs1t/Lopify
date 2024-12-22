package ru.focsit.mobile.fragments.moderator.ModeratorSong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.CommentDto

/**
 * Адаптер для отображения списка комментариев к песне в RecyclerView.
 * Позволяет показывать имя пользователя, текст комментария и кнопку для удаления комментария.
 *
 * @param comments Список комментариев для отображения.
 * @param onDeleteCommentClick Функция, которая будет вызываться при нажатии на кнопку удаления комментария.
 */
class ModeratorSongCommentsAdapter(
    private val comments: List<CommentDto>,
    private val onDeleteCommentClick: (Long) -> Unit
) : RecyclerView.Adapter<ModeratorSongCommentsAdapter.CommentViewHolder>() {

    /**
     * ViewHolder для каждого элемента комментария.
     * Содержит ссылки на текстовые поля и кнопку для удаления комментария.
     *
     * @param itemView Вид элемента в RecyclerView.
     */
    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameText: TextView = itemView.findViewById(R.id.text_username)
        val commentText: TextView = itemView.findViewById(R.id.text_comment)
        val deleteButton: ImageView = itemView.findViewById(R.id.button_delete_comment)
    }

    /**
     * Создает новый ViewHolder для комментария.
     * Этот метод вызывается при необходимости создать новый элемент для отображения в RecyclerView.
     *
     * @param parent Родительский элемент, в котором будет размещен новый элемент.
     * @param viewType Тип представления (не используется в этом случае).
     * @return Новый ViewHolder для комментария.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    /**
     * Привязывает данные комментария к элементу в RecyclerView.
     * Этот метод вызывается для каждого элемента в списке, чтобы обновить его содержимое.
     *
     * @param holder ViewHolder для элемента, в котором нужно отобразить данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.usernameText.text = comment.username
        holder.commentText.text = comment.content
        holder.deleteButton.setOnClickListener {
            comment.id?.let { commentId ->
                onDeleteCommentClick(commentId)
            }
        }
    }

    /**
     * Возвращает количество комментариев в списке.
     *
     * @return Размер списка комментариев.
     */
    override fun getItemCount(): Int = comments.size
}