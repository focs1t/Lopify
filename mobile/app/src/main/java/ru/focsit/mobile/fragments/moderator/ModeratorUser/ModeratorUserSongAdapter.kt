package ru.focsit.mobile.fragments.moderator.ModeratorUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto

class ModeratorUserSongAdapter(
    private val songs: List<SongDto>
) : RecyclerView.Adapter<ModeratorUserSongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
    }

    override fun getItemCount(): Int = songs.size

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val songTitle: TextView = itemView.findViewById(R.id.songTitle)
        private val songArtist: TextView = itemView.findViewById(R.id.songArtist)

        fun bind(song: SongDto) {
            songTitle.text = song.name
            songArtist.text = song.artist
        }
    }
}