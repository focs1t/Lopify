package ru.focsit.mobile.fragments.moderator.ModeratorUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.CommentDto

class ModeratorUserCommentAdapter(
    private val comments: List<CommentDto>,
    private val onDeleteComment: (Long) -> Unit
) : RecyclerView.Adapter<ModeratorUserCommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentText: TextView = itemView.findViewById(R.id.commentText)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteCommentButton)

        fun bind(comment: CommentDto, position: Int) {
            // Устанавливаем текст комментария
            commentText.text = comment.content

            // Устанавливаем цвета для четных и нечетных позиций
            val backgroundColor = if (position % 2 == 0) {
                ContextCompat.getColor(itemView.context, R.color.comment_even)
            } else {
                ContextCompat.getColor(itemView.context, R.color.comment_odd)
            }
            itemView.setBackgroundColor(backgroundColor)

            // Настраиваем кнопку удаления
            deleteButton.setOnClickListener {
                comment.id?.let { id ->
                    onDeleteComment(id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment, position)
    }

    override fun getItemCount(): Int = comments.size
}