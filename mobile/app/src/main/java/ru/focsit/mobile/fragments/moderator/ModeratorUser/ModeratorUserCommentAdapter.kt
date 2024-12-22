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

/**
 * Адаптер для отображения комментариев пользователей в интерфейсе модератора.
 *
 * @param comments Список комментариев, которые будут отображаться.
 * @param onDeleteComment Функция обратного вызова для удаления комментария. Принимает идентификатор комментария.
 */
class ModeratorUserCommentAdapter(
    private val comments: List<CommentDto>,
    private val onDeleteComment: (Long) -> Unit
) : RecyclerView.Adapter<ModeratorUserCommentAdapter.CommentViewHolder>() {

    /**
     * ViewHolder для комментария.
     * Содержит элементы пользовательского интерфейса для отображения текста комментария и кнопки удаления.
     */
    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentText: TextView = itemView.findViewById(R.id.commentText)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteCommentButton)

        /**
         * Связывает данные комментария с представлением.
         *
         * @param comment Объект комментария, который нужно отобразить.
         * @param position Позиция комментария в списке.
         */
        fun bind(comment: CommentDto, position: Int) {
            // Устанавливаем текст комментария
            commentText.text = comment.content

            // Устанавливаем цвета фона для четных и нечетных позиций
            val backgroundColor = if (position % 2 == 0) {
                ContextCompat.getColor(itemView.context, R.color.comment_even)
            } else {
                ContextCompat.getColor(itemView.context, R.color.comment_odd)
            }
            itemView.setBackgroundColor(backgroundColor)

            // Настроим обработчик нажатия на кнопку удаления
            deleteButton.setOnClickListener {
                comment.id?.let { id ->
                    onDeleteComment(id)
                }
            }
        }
    }

    /**
     * Создает и возвращает новый ViewHolder для комментария.
     *
     * @param parent Родительский контейнер для элемента списка.
     * @param viewType Тип представления, который будет использоваться.
     * @return Новый объект [CommentViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_comment, parent, false)
        return CommentViewHolder(view)
    }

    /**
     * Привязывает данные к элементу списка на основе позиции.
     *
     * @param holder ViewHolder, который будет заполняться данными.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment, position)
    }

    /**
     * Возвращает количество элементов в списке комментариев.
     *
     * @return Число комментариев в списке.
     */
    override fun getItemCount(): Int = comments.size
}