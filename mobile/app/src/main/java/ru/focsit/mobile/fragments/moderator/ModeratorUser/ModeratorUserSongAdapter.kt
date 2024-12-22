package ru.focsit.mobile.fragments.moderator.ModeratorUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto

/**
 * Адаптер для отображения списка песен пользователя в интерфейсе модератора.
 *
 * @param songs Список песен, которые будут отображаться в RecyclerView.
 */
class ModeratorUserSongAdapter(
    private val songs: List<SongDto>
) : RecyclerView.Adapter<ModeratorUserSongAdapter.SongViewHolder>() {

    /**
     * Создаёт новый элемент списка (ViewHolder) для песни.
     *
     * @param parent Родительское представление, в котором будет размещаться новый элемент.
     * @param viewType Тип представления (не используется в данной реализации).
     * @return Новый объект SongViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_song, parent, false)
        return SongViewHolder(view)
    }

    /**
     * Привязывает данные песни к элементу списка.
     *
     * @param holder ViewHolder, в который будут привязаны данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
    }

    /**
     * Возвращает количество элементов в списке песен.
     *
     * @return Количество элементов в списке.
     */
    override fun getItemCount(): Int = songs.size

    /**
     * ViewHolder для элемента списка песни.
     *
     * @param itemView Представление элемента списка песни.
     */
    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val songTitle: TextView = itemView.findViewById(R.id.songTitle)
        private val songArtist: TextView = itemView.findViewById(R.id.songArtist)

        /**
         * Привязывает данные песни к представлениям в элементе списка.
         *
         * @param song Объект песни, данные которого будут отображены.
         */
        fun bind(song: SongDto) {
            songTitle.text = song.name
            songArtist.text = song.artist
        }
    }
}