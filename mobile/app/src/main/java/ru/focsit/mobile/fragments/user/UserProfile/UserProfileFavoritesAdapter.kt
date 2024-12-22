package ru.focsit.mobile.fragments.user.UserProfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.SongDto

/**
 * Адаптер для отображения списка избранных песен пользователя в профиле.
 *
 * В адаптере используются два типа представлений:
 * 1. Заголовок списка.
 * 2. Элементы списка с песнями.
 *
 * @param songs Список избранных песен.
 * @param onRemoveClick Функция, вызываемая при удалении песни из избранных.
 */
class UserProfileFavoritesAdapter(
    private val songs: List<SongDto>,
    private val onRemoveClick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Константы для типов представлений
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    /**
     * Определяет тип представления для текущей позиции.
     *
     * @param position Позиция элемента в списке.
     * @return Тип представления (заголовок или элемент списка).
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    /**
     * Создаёт ViewHolder в зависимости от типа представления.
     *
     * @param parent Родительский контейнер для представлений.
     * @param viewType Тип представления.
     * @return Соответствующий ViewHolder.
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
     * Привязывает данные к соответствующему ViewHolder.
     *
     * @param holder ViewHolder для текущей позиции.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SongViewHolder -> {
                val song = songs[position - 1]  // Мы пропускаем заголовок (позиция 0)
                holder.bind(song)
            }
        }
    }

    /**
     * Возвращает количество элементов в списке, включая заголовок.
     *
     * @return Количество элементов в списке.
     */
    override fun getItemCount(): Int = songs.size + 1 // Плюс один для заголовка

    /**
     * ViewHolder для заголовка списка.
     */
    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    /**
     * ViewHolder для элемента списка (песни).
     *
     * @param view Представление элемента списка (песни).
     */
    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val songName: TextView = itemView.findViewById(R.id.song_name)
        private val songArtist: TextView = itemView.findViewById(R.id.song_artist)
        private val removeButton: ImageView = itemView.findViewById(R.id.image_delete_song)

        /**
         * Привязывает данные песни к представлению.
         *
         * @param song Песня, которую нужно отобразить.
         */
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