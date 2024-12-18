package ru.focsit.mobile.fragments.moderator

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto

class EditSongDialogFragment : DialogFragment() {

    private var song: SongDto? = null

    companion object {
        fun newInstance(song: SongDto, onUpdate: (SongDto) -> Unit): EditSongDialogFragment {
            val fragment = EditSongDialogFragment()
            fragment.song = song
            fragment.onUpdate = onUpdate
            return fragment
        }
    }

    private lateinit var onUpdate: (SongDto) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialogView = inflater.inflate(R.layout.dialog_create_song, container, false)

        val nameInput = dialogView.findViewById<EditText>(R.id.input_name)
        val genreInput = dialogView.findViewById<EditText>(R.id.input_genre)
        val artistInput = dialogView.findViewById<EditText>(R.id.input_artist)
        val albumInput = dialogView.findViewById<EditText>(R.id.input_album)
        val durationInput = dialogView.findViewById<EditText>(R.id.input_duration)
        val saveButton = dialogView.findViewById<Button>(R.id.button_create_song)

        // Заполнение полей текущими значениями
        nameInput.setText(song?.name)
        genreInput.setText(song?.genre)
        artistInput.setText(song?.artist)
        albumInput.setText(song?.album)
        durationInput.setText(song?.duration.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Редактировать песню")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        saveButton.setOnClickListener {
            val updatedName = nameInput.text.toString()
            val updatedGenre = genreInput.text.toString()
            val updatedArtist = artistInput.text.toString()
            val updatedAlbum = albumInput.text.toString()
            val updatedDurationStr = durationInput.text.toString()

            if (updatedName.isNotEmpty() && updatedGenre.isNotEmpty() && updatedArtist.isNotEmpty() && updatedAlbum.isNotEmpty() && updatedDurationStr.isNotEmpty()) {
                val updatedDuration = updatedDurationStr.toIntOrNull()

                if (updatedDuration != null) {
                    val updatedSong = song?.copy(
                        name = updatedName,
                        genre = updatedGenre,
                        artist = updatedArtist,
                        album = updatedAlbum,
                        duration = updatedDuration
                    )

                    if (updatedSong != null) {
                        onUpdate(updatedSong)
                        dialog.dismiss()
                    } else {
                        Toast.makeText(requireContext(), "Ошибка при обновлении данных", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Введите корректную продолжительность", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        return dialogView
    }
}