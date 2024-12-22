package ru.focsit.mobile.fragments.moderator.ModeratorSong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto

/**
 * Адаптер для отображения списка песен в RecyclerView.
 * Позволяет отображать список песен с заголовком и отдельными элементами для каждой песни.
 * Также поддерживает обработку кликов на песню и удаление песни.
 *
 * @param songs Список песен для отображения.
 * @param onSongClick Функция, которая будет вызываться при клике на песню.
 * @param onDeleteSongClick Функция, которая будет вызываться при клике на кнопку удаления песни.
 */
class ModeratorSongAdapter(
    private val songs: List<SongDto>,
    private val onSongClick: (SongDto) -> Unit,
    private val onDeleteSongClick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Константы для типов представлений
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    /**
     * Возвращает тип представления для указанной позиции.
     * Это необходимо для того, чтобы различать заголовок и обычные элементы списка.
     *
     * @param position Позиция элемента в RecyclerView.
     * @return Тип представления для данной позиции.
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    /**
     * Создает ViewHolder для заголовка или элемента списка.
     * Этот метод вызывается для каждого элемента в RecyclerView.
     *
     * @param parent Родительский элемент, в котором будет размещен новый элемент.
     * @param viewType Тип представления, который должен быть создан.
     * @return ViewHolder для соответствующего типа представления.
     */
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

    /**
     * Привязывает данные песни к соответствующему элементу в RecyclerView.
     * Этот метод вызывается для каждого элемента в списке.
     *
     * @param holder ViewHolder, которому нужно привязать данные.
     * @param position Позиция элемента в RecyclerView.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SongViewHolder -> {
                // Мы пропускаем заголовок, поэтому для песен начинаем с позиции 1
                val song = songs[position - 1]
                holder.bind(song)
            }
        }
    }

    /**
     * Возвращает количество элементов в списке.
     * Плюс один элемент для заголовка.
     *
     * @return Количество элементов в списке.
     */
    override fun getItemCount(): Int = songs.size + 1 // Плюс один для заголовка

    /**
     * ViewHolder для заголовка.
     * Используется только для заголовка списка песен.
     *
     * @param view Вид элемента заголовка.
     */
    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    /**
     * ViewHolder для элемента песни.
     * Используется для отображения каждой песни в списке.
     *
     * @param view Вид элемента для песни.
     */
    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val songNameTextView: TextView = view.findViewById(R.id.song_name)
        private val songArtistTextView: TextView = view.findViewById(R.id.song_artist)
        private val deleteImageView: ImageView = view.findViewById(R.id.image_delete_song)

        /**
         * Привязывает данные песни к элементам интерфейса.
         *
         * @param song Песня, которую нужно отобразить.
         */
        fun bind(song: SongDto) {
            songNameTextView.text = song.name
            songArtistTextView.text = song.artist
            itemView.setOnClickListener {
                onSongClick(song)  // Обработка клика по песне
            }
            deleteImageView.setOnClickListener {
                song.id?.let { songId ->
                    onDeleteSongClick(songId)  // Обработка клика по кнопке удаления песни
                }
            }
        }
    }
}