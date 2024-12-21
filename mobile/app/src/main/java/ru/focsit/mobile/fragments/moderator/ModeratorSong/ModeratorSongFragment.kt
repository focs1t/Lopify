package ru.focsit.mobile.fragments.moderator.ModeratorSong

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto
import ru.focsit.mobile.repo.moderator.ModeratorSongRepository

class ModeratorSongFragment : Fragment(R.layout.fragment_moderator_song) {
    private lateinit var repository: ModeratorSongRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ModeratorSongAdapter
    private lateinit var commentsAdapter: ModeratorSongCommentsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = ModeratorSongRepository(requireContext())

        val spinnerCriteria = view.findViewById<Spinner>(R.id.spinner_search_criteria)
        val searchInput = view.findViewById<EditText>(R.id.input_search_query)
        val searchButton = view.findViewById<ImageView>(R.id.image_search_icon)
        val addButton = view.findViewById<Button>(R.id.button_add_song)
        recyclerView = view.findViewById(R.id.recycler_songs)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadAllSongs()

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

        addButton.setOnClickListener {
            showCreateSongDialog()
        }
    }

    private fun loadAllSongs() {
        repository.getAllSongs { songs ->
            if (songs != null) {
                adapter = ModeratorSongAdapter(songs, ::onSongClick, ::onDeleteSongClick)
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(requireContext(), "Ошибка загрузки песен", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchSongs(album: String? = null, artist: String? = null, name: String? = null) {
        repository.searchSongs(album, artist, name) { songs ->
            if (songs != null) {
                adapter = ModeratorSongAdapter(songs, ::onSongClick, ::onDeleteSongClick)
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(requireContext(), "Ошибка поиска песен", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showCreateSongDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_song, null)

        val nameInput = dialogView.findViewById<EditText>(R.id.input_name)
        val genreInput = dialogView.findViewById<EditText>(R.id.input_genre)
        val artistInput = dialogView.findViewById<EditText>(R.id.input_artist)
        val albumInput = dialogView.findViewById<EditText>(R.id.input_album)
        val durationInput = dialogView.findViewById<EditText>(R.id.input_duration)
        val createButton = dialogView.findViewById<Button>(R.id.button_create_song)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Добавить песню")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        createButton.setOnClickListener {
            val name = nameInput.text.toString()
            val genre = genreInput.text.toString()
            val artist = artistInput.text.toString()
            val album = albumInput.text.toString()
            val durationStr = durationInput.text.toString()

            if (name.isNotEmpty() && genre.isNotEmpty() && artist.isNotEmpty() && album.isNotEmpty() && durationStr.isNotEmpty()) {
                val duration = durationStr.toIntOrNull()

                if (duration != null) {
                    val newSong = SongDto(
                        name = name,
                        genre = genre,
                        artist = artist,
                        album = album,
                        duration = duration
                    )

                    repository.createSong(newSong) { song ->
                        if (song != null) {
                            Toast.makeText(requireContext(), "Песня добавлена", Toast.LENGTH_SHORT).show()
                            loadAllSongs()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Ошибка добавления песни", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Введите корректную продолжительность", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun onSongClick(song: SongDto) {
        showActionDialog(song)
    }

    private fun showActionDialog(song: SongDto) {
        AlertDialog.Builder(requireContext())
            .setTitle("Выберите действие")
            .setItems(arrayOf("Редактировать песню", "Показать комментарии")) { _, which ->
                when (which) {
                    0 -> showEditSongDialog(song)
                    1 -> showCommentsDialog(song.id)
                }
            }
            .setCancelable(true)
            .create()
            .show()
    }

    private fun showEditSongDialog(song: SongDto) {
        val editSongDialog = EditSongDialogFragment.newInstance(song) { updatedSong ->
            song.id?.let {
                repository.updateSong(it, updatedSong) { updated ->
                    if (updated != null) {
                        Toast.makeText(requireContext(), "Песня обновлена", Toast.LENGTH_SHORT)
                            .show()
                        loadAllSongs()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Ошибка обновления песни",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        editSongDialog.show(parentFragmentManager, "editSongDialog")
    }

    private fun showCommentsDialog(songId: Long?) {
        if (songId == null) {
            Toast.makeText(requireContext(), "ID песни не найден", Toast.LENGTH_SHORT).show()
            return
        }

        val dialogView = layoutInflater.inflate(R.layout.dialog_comments, null)
        val commentsRecyclerView = dialogView.findViewById<RecyclerView>(R.id.recycler_comments)
        commentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        var alertDialog: AlertDialog? = null // Сохранение ссылки на диалог

        repository.getCommentsBySong(songId) { comments ->
            if (comments != null) {
                commentsAdapter = ModeratorSongCommentsAdapter(comments) { commentId ->
                    onDeleteCommentClick(songId, commentId)
                    alertDialog?.dismiss() // Закрытие диалога после удаления
                }
                commentsRecyclerView.adapter = commentsAdapter
            } else {
                Toast.makeText(requireContext(), "Ошибка загрузки комментариев", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Комментарии")
            .setView(dialogView)
            .setPositiveButton("Закрыть", null)
            .create()

        alertDialog.show()
    }

    private fun onDeleteCommentClick(songId: Long, commentId: Long) {
        repository.deleteComment(songId, commentId) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Комментарий удален", Toast.LENGTH_SHORT).show()
                showCommentsDialog(songId) // Перезагружаем список комментариев
            } else {
                Toast.makeText(requireContext(), "Ошибка удаления комментария", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onDeleteSongClick(songId: Long) {
        showDeleteConfirmationDialog(songId)
    }

    private fun showDeleteConfirmationDialog(songId: Long) {
        AlertDialog.Builder(requireContext())
            .setTitle("Подтверждение удаления")
            .setMessage("Вы уверены, что хотите удалить эту песню?")
            .setPositiveButton("Да") { _, _ ->
                repository.deleteSong(songId) { success ->
                    if (success) {
                        Toast.makeText(requireContext(), "Песня удалена", Toast.LENGTH_SHORT).show()
                        loadAllSongs()
                    } else {
                        Toast.makeText(requireContext(), "Ошибка удаления песни", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .create()
            .show()
    }
}