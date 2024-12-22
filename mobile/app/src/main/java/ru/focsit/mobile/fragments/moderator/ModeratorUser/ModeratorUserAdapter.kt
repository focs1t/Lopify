package ru.focsit.mobile.fragments.moderator.ModeratorUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.UserDto

/**
 * Адаптер для отображения списка пользователей в интерфейсе модератора.
 *
 * @param users Список пользователей, которые будут отображаться.
 * @param onUserClick Функция обратного вызова для обработки кликов по пользователю. Принимает объект [UserDto].
 */
class ModeratorUserAdapter(private val users: List<UserDto>, private val onUserClick: (UserDto) -> Unit) :
    RecyclerView.Adapter<ModeratorUserAdapter.UserViewHolder>() {

    /**
     * Создает новый [UserViewHolder], который будет использоваться для отображения информации о пользователе.
     *
     * @param parent Родительский контейнер для элемента списка.
     * @param viewType Тип представления, который будет использоваться.
     * @return Новый объект [UserViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_info, parent, false)
        return UserViewHolder(view)
    }

    /**
     * Привязывает данные пользователя к элементу списка на основе позиции.
     *
     * @param holder [UserViewHolder], который будет заполняться данными.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, position)
    }

    /**
     * Возвращает количество элементов в списке пользователей.
     *
     * @return Число пользователей в списке.
     */
    override fun getItemCount(): Int = users.size

    /**
     * ViewHolder для отображения информации о пользователе.
     * Содержит элементы пользовательского интерфейса для отображения имени пользователя и его электронной почты.
     */
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)

        init {
            // Обработчик нажатия на элемент
            itemView.setOnClickListener {
                val user = users[adapterPosition]
                onUserClick(user)  // Вызываем переданную функцию для обработки клика
            }
        }

        /**
         * Привязывает данные пользователя к представлению.
         *
         * @param user Объект [UserDto], который нужно отобразить.
         * @param position Позиция пользователя в списке.
         */
        fun bind(user: UserDto, position: Int) {
            userNameTextView.text = user.username
            emailTextView.text = user.email

            // Устанавливаем разный фон для четных и нечетных элементов
            val backgroundColor = if (position % 2 == 0) {
                itemView.context.getColor(R.color.user_item_even)
            } else {
                itemView.context.getColor(R.color.user_item_odd)
            }
            itemView.setBackgroundColor(backgroundColor)
        }
    }
}