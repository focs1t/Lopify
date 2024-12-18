package ru.focsit.mobile.fragments.moderator

import ModeratorUserCommentAdapter
import ModeratorUserSongAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.UserDto
import ru.focsit.mobile.repo.moderator.ModeratorUserRepository

class ModeratorUserFragment : Fragment(R.layout.fragment_moderator_user) {

    private lateinit var moderatorUserRepository: ModeratorUserRepository
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moderatorUserRepository = ModeratorUserRepository(requireContext())
        userRecyclerView = view.findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)

        loadAllUsers()
    }

    private fun loadAllUsers() {
        moderatorUserRepository.getAllUsers { users ->
            if (users != null) {
                userAdapter = UserAdapter(users) { user ->
                    showUserDetails(user.id)
                }
                userRecyclerView.adapter = userAdapter
            } else {
                showToast("Ошибка загрузки пользователей")
            }
        }
    }

    private fun showUserDetails(userId: Long) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_user_details, null)

        val userNameTextView = dialogView.findViewById<TextView>(R.id.userNameTextView)
        val favoritesRecyclerView = dialogView.findViewById<RecyclerView>(R.id.userFavoritesRecyclerView)
        val commentsRecyclerView = dialogView.findViewById<RecyclerView>(R.id.userCommentsRecyclerView)
        val emptyFavoritesTextView = dialogView.findViewById<TextView>(R.id.emptyFavoritesTextView)
        val emptyCommentsTextView = dialogView.findViewById<TextView>(R.id.emptyCommentsTextView)

        favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        commentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        var alertDialog: AlertDialog? = null

        moderatorUserRepository.getUserById(userId) { user ->
            if (user != null) {
                userNameTextView.text = user.username

                moderatorUserRepository.getUserFavorites(userId) { songs ->
                    if (songs != null && songs.isNotEmpty()) {
                        val favoritesAdapter = ModeratorUserSongAdapter(songs)
                        favoritesRecyclerView.adapter = favoritesAdapter
                        emptyFavoritesTextView.visibility = View.GONE  // Скрываем сообщение о пустом списке
                        favoritesRecyclerView.visibility = View.VISIBLE  // Показываем RecyclerView
                    } else {
                        emptyFavoritesTextView.visibility = View.VISIBLE  // Показываем сообщение о пустом списке
                        favoritesRecyclerView.visibility = View.GONE  // Скрываем RecyclerView
                    }
                }

                moderatorUserRepository.getUserComments(userId) { comments ->
                    if (comments != null && comments.isNotEmpty()) {
                        val commentsAdapter = ModeratorUserCommentAdapter(comments) { commentId ->
                            deleteUserComment(userId, commentId)
                            alertDialog?.dismiss()
                        }
                        commentsRecyclerView.adapter = commentsAdapter
                        emptyCommentsTextView.visibility = View.GONE  // Скрываем сообщение о пустом списке
                        commentsRecyclerView.visibility = View.VISIBLE  // Показываем RecyclerView
                    } else {
                        emptyCommentsTextView.visibility = View.VISIBLE  // Показываем сообщение о пустом списке
                        commentsRecyclerView.visibility = View.GONE  // Скрываем RecyclerView
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Ошибка загрузки данных пользователя", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Информация о пользователе")
            .setView(dialogView)
            .setPositiveButton("Закрыть", null)
            .create()

        alertDialog.show()
    }

    private fun deleteUserComment(userId: Long, commentId: Long) {
        moderatorUserRepository.deleteUserComment(userId, commentId) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Комментарий удалён", Toast.LENGTH_SHORT).show()
                showUserDetails(userId) // Перезагрузим данные пользователя
            } else {
                Toast.makeText(requireContext(), "Ошибка удаления комментария", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}