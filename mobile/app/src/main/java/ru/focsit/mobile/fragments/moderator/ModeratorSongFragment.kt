package ru.focsit.mobile.fragments.moderator

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto
import ru.focsit.mobile.data.SongUpDto
import ru.focsit.mobile.repo.moderator.ModeratorSongRepository

class ModeratorSongFragment : Fragment(R.layout.fragment_moderator_song) {
    private lateinit var repository: ModeratorSongRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ModeratorSongAdapter // Адаптер для списка песен

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

        // Поиск по выбранному критерию
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

        // Добавление новой песни
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
                            loadAllSongs() // Перезагружаем список песен
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
        // Открываем диалог редактирования песни
        val editSongDialog = EditSongDialogFragment.newInstance(song) { updatedSong ->
            // Обновление песни
            song.id?.let {
                repository.updateSong(it, updatedSong) { updated ->
                    if (updated != null) {
                        Toast.makeText(requireContext(), "Песня обновлена", Toast.LENGTH_SHORT).show()
                        loadAllSongs() // Перезагружаем список песен
                    } else {
                        Toast.makeText(requireContext(), "Ошибка обновления песни", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        editSongDialog.show(parentFragmentManager, "editSongDialog")
    }

    private fun onDeleteSongClick(songId: Long) {
        // Показываем диалог подтверждения удаления
        showDeleteConfirmationDialog(songId)
    }

    private fun showDeleteConfirmationDialog(songId: Long) {
        // Создаем диалог подтверждения
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Подтверждение удаления")
            .setMessage("Вы уверены, что хотите удалить эту песню?")
            .setPositiveButton("Да") { _, _ ->
                deleteSongById(songId) // Удаляем песню после подтверждения
            }
            .setNegativeButton("Отмена", null)
            .create()
        dialog.show()
    }

    private fun deleteSongById(songId: Long) {
        repository.deleteSong(songId) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Песня удалена", Toast.LENGTH_SHORT).show()
                loadAllSongs() // Перезагружаем список песен
            } else {
                Toast.makeText(requireContext(), "Ошибка удаления песни", Toast.LENGTH_SHORT).show()
            }
        }
    }
}