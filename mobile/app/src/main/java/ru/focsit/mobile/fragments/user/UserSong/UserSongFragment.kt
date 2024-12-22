package ru.focsit.mobile.fragments.user.UserSong

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.repo.user.UserSongRepository
import androidx.appcompat.app.AlertDialog

/**
 * Фрагмент для отображения списка песен пользователя с возможностью их поиска, добавления в избранное и просмотра комментариев.
 *
 * Этот фрагмент содержит логику для отображения списка песен с возможностью фильтрации и поиска по альбому, артисту или названию. Также пользователи могут добавлять песни в избранное и оставлять комментарии к ним.
 */
class UserSongFragment : Fragment(R.layout.fragment_user_song) {

    private lateinit var repository: UserSongRepository
    private lateinit var songAdapter: SongAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private var selectedSongId: Long? = null

    // Поля для поиска
    private lateinit var spinnerCriteria: Spinner
    private lateinit var searchInput: EditText
    private lateinit var searchButton: ImageView

    /**
     * Метод вызывается при создании представления фрагмента.
     * Инициализирует компоненты UI, настраивает поиск и загружает список песен.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация репозитория
        repository = UserSongRepository(requireContext())

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        songAdapter = SongAdapter { songId -> onSongItemClick(songId) }
        recyclerView.adapter = songAdapter

        commentAdapter = CommentAdapter()

        // Инициализация полей для поиска
        spinnerCriteria = view.findViewById(R.id.spinner_search_criteria)
        searchInput = view.findViewById(R.id.input_search_query)
        searchButton = view.findViewById(R.id.image_search_icon)

        // Загружаем песни
        loadSongs()

        // Обработка нажатия кнопки поиска
        searchButton.setOnClickListener {
            val query = searchInput.text.toString()
            val selectedCriterion = spinnerCriteria.selectedItem.toString()

            if (query.isNotEmpty()) {
                when (selectedCriterion) {
                    "Альбом" -> searchSongs(album = query)
                    "Артист" -> searchSongs(artist = query)
                    "Название" -> searchSongs(name = query)
                }
            } else {
                Toast.makeText(requireContext(), "Введите запрос для поиска", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Загружает все доступные песни с использованием репозитория и отображает их в списке.
     */
    private fun loadSongs() {
        repository.getAllSongs { songs ->
            if (songs != null) {
                songAdapter.submitList(songs)
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить песни", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Выполняет поиск песен по указанным параметрам.
     *
     * @param album Название альбома для поиска.
     * @param artist Имя артиста для поиска.
     * @param name Название песни для поиска.
     */
    private fun searchSongs(album: String? = null, artist: String? = null, name: String? = null) {
        repository.searchSongs(album, artist, name) { songs ->
            if (songs != null) {
                songAdapter.submitList(songs)
            } else {
                Toast.makeText(requireContext(), "Не удалось найти песни", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Обрабатывает нажатие на элемент списка песни.
     *
     * @param songId ID выбранной песни.
     */
    private fun onSongItemClick(songId: Long) {
        selectedSongId = songId
        showSongOptionsMenu(songId)
    }

    /**
     * Отображает меню опций для выбранной песни, позволяя добавить в избранное или просмотреть комментарии.
     *
     * @param songId ID выбранной песни.
     */
    private fun showSongOptionsMenu(songId: Long) {
        repository.getUserFavorites { favorites ->
            val isFavorite = favorites?.any { it.id == songId } == true

            val options = when {
                isFavorite -> arrayOf("Удалить из избранного", "Комментарии")
                else -> arrayOf("Добавить в избранное", "Комментарии")
            }

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Выберите действие")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> {
                            if (isFavorite) handleRemoveFromFavorites(songId)
                            else handleAddToFavorites(songId)
                        }
                        1 -> showComments(songId)
                    }
                }
            builder.create().show()
        }
    }

    /**
     * Добавляет песню в избранное.
     *
     * @param songId ID выбранной песни.
     */
    private fun handleAddToFavorites(songId: Long) {
        repository.addSongToFavorites(songId) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Песня добавлена в избранное", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Не удалось добавить песню в избранное", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Удаляет песню из избранного.
     *
     * @param songId ID выбранной песни.
     */
    private fun handleRemoveFromFavorites(songId: Long) {
        repository.removeSongFromFavorites(songId) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Песня удалена из избранного", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Не удалось удалить песню из избранного", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Отображает комментарии к выбранной песне.
     *
     * @param songId ID выбранной песни.
     */
    private fun showComments(songId: Long) {
        repository.getCommentsForSong(songId) { comments ->
            if (comments != null) {
                val commentRecyclerView = RecyclerView(requireContext())
                commentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                val commentAdapter = CommentAdapter()
                commentRecyclerView.adapter = commentAdapter

                commentAdapter.submitList(comments)

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Комментарии")
                    .setView(commentRecyclerView)
                    .setPositiveButton("Добавить комментарий") { _, _ ->
                        showAddCommentDialog(songId)
                    }
                    .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }

                builder.create().show()
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить комментарии", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Отображает диалог для добавления комментария к песне.
     *
     * @param songId ID выбранной песни.
     */
    private fun showAddCommentDialog(songId: Long) {
        val builder = AlertDialog.Builder(requireContext())
        val input = EditText(requireContext())
        input.hint = "Введите ваш комментарий"

        builder.setTitle("Добавить комментарий")
            .setView(input)
            .setPositiveButton("Отправить") { _, _ ->
                val comment = input.text
                if (comment.isNotEmpty()) {
                    addCommentToSong(songId, comment.toString())
                } else {
                    Toast.makeText(requireContext(), "Комментарий не может быть пустым", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }

    /**
     * Добавляет комментарий к песне.
     *
     * @param songId ID песни.
     * @param comment Текст комментария.
     */
    private fun addCommentToSong(songId: Long, comment: String) {
        repository.addCommentToSong(songId, comment) { newComment ->
            if (newComment != null) {
                Toast.makeText(requireContext(), "Комментарий добавлен", Toast.LENGTH_SHORT).show()
                showComments(songId)
            } else {
                Toast.makeText(requireContext(), "Не удалось добавить комментарий", Toast.LENGTH_SHORT).show()
            }
        }
    }
}