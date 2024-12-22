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

/**
 * Адаптер для отображения списка песен в RecyclerView.
 *
 * Этот адаптер используется для отображения песен пользователя. Каждая песня отображается с номером трека,
 * названием песни и именем исполнителя. Адаптер поддерживает обновление списка с использованием
 * `ListAdapter` и `DiffUtil` для оптимизации изменения данных.
 *
 * @param onClick Лямбда-функция, которая будет вызвана при клике на песню. В неё передается ID выбранной песни.
 */
class SongAdapter(private val onClick: (Long) -> Unit) : ListAdapter<SongDto, SongAdapter.SongViewHolder>(
    SongDiffCallback()
) {

    /**
     * Создает и возвращает новый объект [SongViewHolder].
     *
     * @param parent Контейнер для создания нового элемента списка.
     * @param viewType Тип вида (в данном случае не используется, так как у нас один тип элемента).
     * @return Новый экземпляр [SongViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song_user, parent, false)
        return SongViewHolder(view)
    }

    /**
     * Привязывает данные песни к представлению в элементе списка.
     *
     * @param holder Объект [SongViewHolder], который будет отображать данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song, position + 1) // Передаём номер трека
    }

    /**
     * ViewHolder для отображения данных песни в одном элементе списка.
     */
    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val songNumber: TextView = itemView.findViewById(R.id.songNumber)
        private val songName: TextView = itemView.findViewById(R.id.songName)
        private val artistName: TextView = itemView.findViewById(R.id.artistName)

        /**
         * Привязывает данные песни к элементам UI.
         *
         * @param song Песня, данные которой нужно отобразить.
         * @param position Позиция песни в списке, используется для отображения номера трека.
         */
        fun bind(song: SongDto, position: Int) {
            songNumber.text = position.toString() // Устанавливаем номер трека
            songName.text = song.name
            artistName.text = song.artist
            itemView.setOnClickListener {
                song.id?.let { onClick(it) } // передаем ID песни при клике
            }
        }
    }

    /**
     * Класс для сравнения старых и новых элементов списка с использованием [DiffUtil].
     * Это позволяет оптимизировать обновление данных в адаптере.
     */
    class SongDiffCallback : DiffUtil.ItemCallback<SongDto>() {

        /**
         * Проверяет, являются ли два элемента одним и тем же.
         *
         * @param oldItem Старый элемент.
         * @param newItem Новый элемент.
         * @return true, если элементы одинаковы (по ID).
         */
        override fun areItemsTheSame(oldItem: SongDto, newItem: SongDto): Boolean {
            return oldItem.id == newItem.id
        }

        /**
         * Проверяет, равны ли содержимое двух элементов.
         *
         * @param oldItem Старый элемент.
         * @param newItem Новый элемент.
         * @return true, если содержимое элементов одинаково.
         */
        override fun areContentsTheSame(oldItem: SongDto, newItem: SongDto): Boolean {
            return oldItem == newItem
        }
    }
}