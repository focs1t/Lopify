package ru.focsit.mobile.fragments.user.UserSong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto

class SongAdapter(private val onClick: (Long) -> Unit) : ListAdapter<SongDto, SongAdapter.SongViewHolder>(
    SongDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song_user, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song, position + 1) // Передаём номер трека
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val songNumber: TextView = itemView.findViewById(R.id.songNumber)
        private val songName: TextView = itemView.findViewById(R.id.songName)
        private val artistName: TextView = itemView.findViewById(R.id.artistName)

        fun bind(song: SongDto, position: Int) {
            songNumber.text = position.toString() // Устанавливаем номер трека
            songName.text = song.name
            artistName.text = song.artist
            itemView.setOnClickListener {
                song.id?.let { onClick(it) } // передаем ID песни при клике
            }
        }
    }

    class SongDiffCallback : DiffUtil.ItemCallback<SongDto>() {
        override fun areItemsTheSame(oldItem: SongDto, newItem: SongDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SongDto, newItem: SongDto): Boolean {
            return oldItem == newItem
        }
    }
}