package ru.focsit.mobile.fragments.moderator.ModeratorUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.UserDto

class ModeratorUserAdapter(private val users: List<UserDto>, private val onUserClick: (UserDto) -> Unit) :
    RecyclerView.Adapter<ModeratorUserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_info, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, position)
    }

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)

        init {
            itemView.setOnClickListener {
                val user = users[adapterPosition]
                onUserClick(user)
            }
        }

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