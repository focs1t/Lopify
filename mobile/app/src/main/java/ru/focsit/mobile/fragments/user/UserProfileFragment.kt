package ru.focsit.mobile.fragments.user

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.SignInActivity
import ru.focsit.mobile.data.CommentDto
import ru.focsit.mobile.data.SongDto
import ru.focsit.mobile.repo.AuthRepository
import ru.focsit.mobile.repo.user.UserProfileRepository
import ru.focsit.mobile.utils.PreferencesHelper

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val authRepository = AuthRepository()
    private lateinit var userProfileRepository: UserProfileRepository

    private lateinit var userNameTextView: TextView
    private lateinit var userProfileLayout: LinearLayout  // Контейнер для имени и иконки

    private var favoriteSongs = mutableListOf<SongDto>()
    private var userComments = mutableListOf<CommentDto>()

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        userProfileRepository = UserProfileRepository(context)  // Инициализируем репозиторий в onAttach
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userNameTextView = view.findViewById(R.id.userNameTextView)
        userProfileLayout = view.findViewById(R.id.userProfileLayout)

        // Получаем данные пользователя, комментариев и избранных песен
        loadUserData()

        // При нажатии на область с именем пользователя и иконкой, показываем диалог с выбором действия
        userProfileLayout.setOnClickListener {
            showActionDialog()
        }
    }

    private fun loadUserData() {
        // Проверяем, прикреплен ли фрагмент
        activity?.let { activity ->
            userProfileRepository.getCurrentUser { userDto ->
                if (userDto != null) {
                    // Отображаем имя пользователя
                    userNameTextView.text = userDto.username
                } else {
                    // Обработка ошибки получения данных о пользователе
                }
            }

            // Загружаем комментарии
            userProfileRepository.getMyComments { comments ->
                comments?.let {
                    userComments.clear()
                    userComments.addAll(it)
                }
            }

            // Загружаем избранные песни
            userProfileRepository.getMyFavorites { songs ->
                songs?.let {
                    favoriteSongs.clear()
                    favoriteSongs.addAll(it)
                }
            }
        }
    }

    private fun showActionDialog() {
        // Проверяем, прикреплен ли фрагмент
        activity?.let { activity ->
            AlertDialog.Builder(activity)
                .setTitle("Выберите действие")
                .setItems(arrayOf("Показать мои комментарии", "Показать мое любимое", "Выйти из аккаунта")) { _, which ->
                    when (which) {
                        0 -> showCommentsDialog() // Показать комментарии
                        1 -> showFavoritesDialog() // Показать избранные песни
                        2 -> performLogout() // Выйти из аккаунта
                    }
                }
                .setCancelable(true)
                .create()
                .show()
        }
    }

    private fun showCommentsDialog() {
        // Проверяем, прикреплен ли фрагмент
        activity?.let { activity ->
            // Показываем список комментариев
            val dialogView = layoutInflater.inflate(R.layout.dialog_comments, null)
            val commentsRecyclerView = dialogView.findViewById<RecyclerView>(R.id.recycler_comments)
            commentsRecyclerView.layoutManager = LinearLayoutManager(activity)

            val commentsAdapter = UserProfileCommentsAdapter(userComments)
            commentsRecyclerView.adapter = commentsAdapter

            AlertDialog.Builder(activity)
                .setTitle("Комментарии")
                .setView(dialogView)
                .setPositiveButton("Закрыть", null)
                .create()
                .show()
        }
    }

    private fun showFavoritesDialog() {
        // Проверяем, прикреплен ли фрагмент
        activity?.let { activity ->
            // Показываем список избранных песен
            val dialogView = layoutInflater.inflate(R.layout.dialog_favorites, null)
            val favoritesRecyclerView = dialogView.findViewById<RecyclerView>(R.id.recycler_favorites)
            favoritesRecyclerView.layoutManager = LinearLayoutManager(activity)

            val favoritesAdapter = UserProfileFavoritesAdapter(favoriteSongs) { songId ->
                removeSongFromFavorites(songId)
            }
            favoritesRecyclerView.adapter = favoritesAdapter

            AlertDialog.Builder(activity)
                .setTitle("Избранные песни")
                .setView(dialogView)
                .setPositiveButton("Закрыть", null)
                .create()
                .show()
        }
    }

    private fun removeSongFromFavorites(songId: Long) {
        // Проверяем, прикреплен ли фрагмент
        activity?.let { activity ->
            userProfileRepository.removeSongFromFavorites(songId) { success ->
                if (success) {
                    // Удаляем песню из списка избранных
                    favoriteSongs.removeAll { it.id == songId }
                } else {
                    // Обработка ошибки удаления песни
                }
            }
        }
    }

    private fun performLogout() {
        // Проверяем, прикреплен ли фрагмент
        activity?.let { activity ->
            authRepository.logout { success ->
                if (success) {
                    // Очистка токена
                    PreferencesHelper.clearToken(activity)
                    PreferencesHelper.clearRole(activity)

                    // Переход на SignInActivity
                    val intent = Intent(activity, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    // Показываем сообщение об ошибке (можно добавить Toast или Snackbar)
                }
            }
        }
    }
}