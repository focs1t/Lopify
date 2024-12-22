package ru.focsit.mobile.fragments.user.UserProfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.CommentDto

/**
 * Адаптер для отображения списка комментариев пользователя в профиле.
 *
 * @param comments Список комментариев пользователя.
 */
class UserProfileCommentsAdapter(private val comments: List<CommentDto>) :
    RecyclerView.Adapter<UserProfileCommentsAdapter.CommentViewHolder>() {

    /**
     * Создаёт ViewHolder для отображения комментария.
     *
     * @param parent Родительский контейнер для представлений.
     * @param viewType Тип представления (не используется в данном адаптере).
     * @return Созданный ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_comment_p, parent, false)
        return CommentViewHolder(view)
    }

    /**
     * Привязывает данные к ViewHolder для отображения комментария.
     *
     * @param holder ViewHolder для текущей позиции.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    /**
     * Возвращает количество элементов в списке комментариев.
     *
     * @return Количество комментариев.
     */
    override fun getItemCount(): Int = comments.size

    /**
     * ViewHolder для комментария пользователя.
     *
     * @param itemView Представление элемента (комментария).
     */
    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentText: TextView = itemView.findViewById(R.id.commentText)

        /**
         * Привязывает данные комментария к представлению.
         *
         * @param comment Комментарий, который нужно отобразить.
         */
        fun bind(comment: CommentDto) {
            commentText.text = comment.content
        }
    }
}