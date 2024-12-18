package ru.focsit.mobile.fragments.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.UserDto

class AdminUserAdapter(
    private val users: List<UserDto>,
    private val onDeleteClick: (Long) -> Unit,
    private val onRequestDeleteConfirmation: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Константы для типов представлений
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val user = users[position - 1]  // Мы пропускаем заголовок (позиция 0)
            holder.bind(user)
        }
    }

    override fun getItemCount(): Int = users.size + 1

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userNameTextView: TextView = view.findViewById(R.id.text_username)
        private val userEmailTextView: TextView = view.findViewById(R.id.text_email)
        private val userRoleTextView: TextView = view.findViewById(R.id.text_role)
        private val deleteImageView: ImageView = view.findViewById(R.id.image_delete_user)

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