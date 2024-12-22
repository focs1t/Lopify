package ru.focsit.mobile.fragments.admin.AdminUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.UserDto

/**
 * Адаптер для отображения списка пользователей в административной панели.
 * Этот адаптер управляет отображением пользователей в RecyclerView, а также обработкой
 * событий на удаление пользователя и подтверждение удаления.
 *
 * @param users Список пользователей, который будет отображаться в RecyclerView.
 * @param onDeleteClick Функция обратного вызова для обработки клика на иконку удаления.
 * @param onRequestDeleteConfirmation Функция обратного вызова для отображения диалога
 * для подтверждения удаления.
 */
class AdminUserAdapter(
    private val users: List<UserDto>,
    private val onDeleteClick: (Long) -> Unit,
    private val onRequestDeleteConfirmation: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Константы для типов представлений
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    /**
     * Возвращает тип представления для позиции в списке.
     * Тип представления определяет, должен ли элемент быть заголовком или обычным элементом.
     *
     * @param position Позиция элемента в списке.
     * @return Тип представления (заголовок или элемент).
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    /**
     * Создает и возвращает новый ViewHolder для соответствующего типа представления.
     * В зависимости от типа, будет создан либо заголовок, либо элемент списка.
     *
     * @param parent Родительский ViewGroup.
     * @param viewType Тип представления (заголовок или элемент).
     * @return Новый ViewHolder для указанного типа.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
                UserViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    /**
     * Привязывает данные к соответствующему ViewHolder.
     * Для заголовка данные не привязываются, так как он статичен.
     * Для обычных пользователей заполняются соответствующие поля.
     *
     * @param holder ViewHolder, в который нужно привязать данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val user = users[position - 1]  // Мы пропускаем заголовок (позиция 0)
            holder.bind(user)
        }
    }

    /**
     * Возвращает общее количество элементов в списке, включая заголовок.
     *
     * @return Общее количество элементов (пользователей + 1 для заголовка).
     */
    override fun getItemCount(): Int = users.size + 1

    /**
     * ViewHolder для заголовка списка пользователей.
     */
    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    /**
     * ViewHolder для элемента списка пользователя.
     */
    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userNameTextView: TextView = view.findViewById(R.id.text_username)
        private val userEmailTextView: TextView = view.findViewById(R.id.text_email)
        private val userRoleTextView: TextView = view.findViewById(R.id.text_role)
        private val deleteImageView: ImageView = view.findViewById(R.id.image_delete_user)

        /**
         * Привязывает данные пользователя к соответствующим представлениям в элементе списка.
         *
         * @param user Данные пользователя, которые нужно отобразить.
         */
        fun bind(user: UserDto) {
            userNameTextView.text = user.username
            userEmailTextView.text = user.email
            userRoleTextView.text = user.role
            deleteImageView.setOnClickListener {
                onRequestDeleteConfirmation(user.id) // Передаем ID для подтверждения удаления
            }
        }
    }
}