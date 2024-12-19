package ru.focsit.mobile.fragments.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto

class UserProfileFavoritesAdapter(
    private val songs: List<SongDto>,
    private val onRemoveClick: (Long) -> Unit
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
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_song_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_song, parent, false)
                SongViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SongViewHolder -> {
                val song = songs[position - 1]  // Мы пропускаем заголовок (позиция 0)
                holder.bind(song)
            }
        }
    }

    override fun getItemCount(): Int = songs.size + 1 // Плюс один для заголовка

    // ViewHolder для заголовка
    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // ViewHolder для элемента списка
    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val songName: TextView = itemView.findViewById(R.id.song_name)
        private val songArtist: TextView = itemView.findViewById(R.id.song_artist)
        private val removeButton: ImageView = itemView.findViewById(R.id.image_delete_song)

        fun bind(song: SongDto) {
            songName.text = song.name
            songArtist.text = song.artist

            // Устанавливаем обработчик для кнопки удаления
            removeButton.setOnClickListener {
                song.id?.let { songId ->
                    onRemoveClick(songId)
                }
            }
        }
    }
}