package ru.focsit.mobile.fragments.moderator.ModeratorUser

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.repo.moderator.ModeratorUserRepository

/**
 * Фрагмент для отображения списка пользователей и их деталей в интерфейсе модератора.
 * Предоставляет возможность просматривать комментарии и избранные песни пользователей,
 * а также удалять комментарии.
 */
class ModeratorUserFragment : Fragment(R.layout.fragment_moderator_user) {

    private lateinit var moderatorUserRepository: ModeratorUserRepository
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var moderatorUserAdapter: ModeratorUserAdapter

    /**
     * Инициализация фрагмента и загрузка списка пользователей.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moderatorUserRepository = ModeratorUserRepository(requireContext())
        userRecyclerView = view.findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)

        // Загрузка всех пользователей
        loadAllUsers()
    }

    /**
     * Загружает список всех пользователей и отображает их в RecyclerView.
     */
    private fun loadAllUsers() {
        moderatorUserRepository.getAllUsers { users ->
            if (users != null) {
                moderatorUserAdapter = ModeratorUserAdapter(users) { user ->
                    showUserDetails(user.id)
                }
                userRecyclerView.adapter = moderatorUserAdapter
            } else {
                showToast("Ошибка загрузки пользователей")
            }
        }
    }

    /**
     * Показывает диалог с деталями пользователя: его комментариями и избранными песнями.
     *
     * @param userId Идентификатор пользователя, чьи детали нужно показать.
     */
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

                // Загружаем избранные песни пользователя
                moderatorUserRepository.getUserFavorites(userId) { songs ->
                    if (songs != null && songs.isNotEmpty()) {
                        val favoritesAdapter = ModeratorUserSongAdapter(songs)
                        favoritesRecyclerView.adapter = favoritesAdapter
                        emptyFavoritesTextView.visibility = View.GONE
                        favoritesRecyclerView.visibility = View.VISIBLE
                    } else {
                        emptyFavoritesTextView.visibility = View.VISIBLE
                        favoritesRecyclerView.visibility = View.GONE
                    }
                }

                // Загружаем комментарии пользователя
                moderatorUserRepository.getUserComments(userId) { comments ->
                    if (comments != null && comments.isNotEmpty()) {
                        val commentsAdapter = ModeratorUserCommentAdapter(comments) { commentId ->
                            deleteUserComment(userId, commentId)
                            alertDialog?.dismiss()
                        }
                        commentsRecyclerView.adapter = commentsAdapter
                        emptyCommentsTextView.visibility = View.GONE
                        commentsRecyclerView.visibility = View.VISIBLE
                    } else {
                        emptyCommentsTextView.visibility = View.VISIBLE
                        commentsRecyclerView.visibility = View.GONE
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

    /**
     * Удаляет комментарий пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param commentId Идентификатор комментария.
     */
    private fun deleteUserComment(userId: Long, commentId: Long) {
        moderatorUserRepository.deleteUserComment(userId, commentId) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Комментарий удалён", Toast.LENGTH_SHORT).show()
                showUserDetails(userId)  // Перезагружаем данные пользователя
            } else {
                Toast.makeText(requireContext(), "Ошибка удаления комментария", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Показывает сообщение в виде Toast.
     *
     * @param message Текст сообщения.
     */
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}