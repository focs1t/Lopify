package ru.focsit.mobile.fragments.moderator.ModeratorSong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.CommentDto

class ModeratorSongCommentsAdapter(
    private val comments: List<CommentDto>,
    private val onDeleteCommentClick: (Long) -> Unit
) : RecyclerView.Adapter<ModeratorSongCommentsAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameText: TextView = itemView.findViewById(R.id.text_username)
        val commentText: TextView = itemView.findViewById(R.id.text_comment)
        val deleteButton: ImageView = itemView.findViewById(R.id.button_delete_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

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

    override fun getItemCount(): Int = comments.size
}