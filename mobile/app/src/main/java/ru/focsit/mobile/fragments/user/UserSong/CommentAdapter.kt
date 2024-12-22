package ru.focsit.mobile.fragments.user.UserSong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.CommentDto

/**
 * Адаптер для отображения списка комментариев в RecyclerView.
 *
 * Этот адаптер используется для отображения комментариев, связанных с песней. Каждый комментарий отображается с содержимым
 * и именем автора. Адаптер обновляет данные при помощи функции `submitList`, а также использует стандартную реализацию
 * `RecyclerView.Adapter` для обработки элементов.
 */
class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private val comments = mutableListOf<CommentDto>()

    /**
     * Обновляет список комментариев.
     *
     * Очищает текущий список и добавляет новые комментарии. После этого вызывается уведомление об изменениях в адаптере,
     * чтобы обновить отображаемые данные.
     *
     * @param newComments Новый список комментариев для отображения.
     */
    fun submitList(newComments: List<CommentDto>) {
        comments.clear()
        comments.addAll(newComments)
        notifyDataSetChanged()
    }

    /**
     * Создает новый ViewHolder для комментария.
     *
     * Этот метод вызывается при необходимости создать новый элемент для отображения в списке.
     *
     * @param parent Контейнер для создания нового элемента списка.
     * @param viewType Тип элемента (в данном случае не используется, так как у нас один тип элемента).
     * @return Новый объект [CommentViewHolder], который будет использоваться для отображения комментария.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_user, parent, false)
        return CommentViewHolder(view)
    }

    /**
     * Привязывает данные комментария к элементу списка.
     *
     * Этот метод вызывается для каждого элемента в списке, чтобы отобразить содержимое комментария.
     *
     * @param holder Объект [CommentViewHolder], который будет отображать данные комментария.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    /**
     * Возвращает количество комментариев в списке.
     *
     * @return Число элементов в списке.
     */
    override fun getItemCount(): Int = comments.size

    /**
     * ViewHolder для отображения одного комментария в списке.
     *
     * Этот класс связывает элементы UI с данными комментария, такими как текст комментария и имя автора.
     */
    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contentTextView: TextView = itemView.findViewById(R.id.commentContent)
        private val authorTextView: TextView = itemView.findViewById(R.id.commentAuthor)

        /**
         * Привязывает данные комментария к UI элементам.
         *
         * Этот метод заполняет текстовые поля содержимым комментария и именем его автора.
         *
         * @param comment Комментарий, данные которого нужно отобразить.
         */
        fun bind(comment: CommentDto) {
            contentTextView.text = comment.content
            authorTextView.text = comment.username
        }
    }
}