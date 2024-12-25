package ru.focsit.mobile.fragments.moderator

import android.content.Intent
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.focsit.mobile.R
import ru.focsit.mobile.SignInActivity
import ru.focsit.mobile.data.SongDto
import ru.focsit.mobile.repo.AuthRepository
import ru.focsit.mobile.repo.moderator.ModeratorReportRepository
import ru.focsit.mobile.utils.PreferencesHelper
import java.io.File
import java.io.FileOutputStream

/**
 * Фрагмент для представления интерфейса модератора.
 * Позволяет пользователю выйти из своего аккаунта и генерировать отчет.
 */
class ModeratorFragment : Fragment(R.layout.fragment_moderator) {

    private lateinit var authRepository: AuthRepository
    private lateinit var reportRepository: ModeratorReportRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authRepository = AuthRepository()
        reportRepository = ModeratorReportRepository(requireContext())  // Передаем контекст в репозиторий

        val logoutButton: Button = view.findViewById(R.id.btn_logout)
        val generateReportButton: Button = view.findViewById(R.id.btn_generate_report)

        // Устанавливаем обработчик для кнопки выхода
        logoutButton.setOnClickListener {
            performLogout()
        }

        // Устанавливаем обработчик для кнопки генерации отчета
        generateReportButton.setOnClickListener {
            fetchTop10Songs()
        }
    }

    /**
     * Метод для выполнения выхода из аккаунта.
     * Очистит токен и роль пользователя, а затем перенаправит его на экран входа.
     */
    private fun performLogout() {
        // Выполняем логаут через репозиторий
        authRepository.logout { success ->
            if (success) {
                PreferencesHelper.clearToken(requireContext())
                PreferencesHelper.clearRole(requireContext())

                // Переход на экран входа в приложение (SignInActivity)
                val intent = Intent(requireContext(), SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Ошибка при выходе", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Метод для получения и отображения топ-10 песен.
     */
    private fun fetchTop10Songs() {
        reportRepository.getTop10Songs { songs ->
            if (songs != null) {
                // Генерируем PDF отчет
                generatePDFReport(songs)
            } else {
                Toast.makeText(requireContext(), "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Генерирует PDF отчет с топ-10 песнями.
     */
    private fun generatePDFReport(songs: List<SongDto>) {
        try {
            val pdfDocument = PdfDocument()

            // Создаем страницу PDF
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 размер
            val page = pdfDocument.startPage(pageInfo)

            val canvas = page.canvas
            val paint = android.graphics.Paint()

            paint.color = Color.BLACK
            paint.textSize = 16f

            // Добавляем заголовок
            canvas.drawText("Top 10 Songs Report", 200f, 30f, paint)

            // Добавляем данные песен с нумерацией
            var yPosition = 50f
            var trackNumber = 1
            for (song in songs) {
                // Нумерация песен
                canvas.drawText("Track #$trackNumber", 50f, yPosition, paint)
                canvas.drawText("Song: ${song.name}", 50f, yPosition + 20, paint)
                canvas.drawText("Artist: ${song.artist}", 50f, yPosition + 40, paint)
                canvas.drawText("Genre: ${song.genre}", 50f, yPosition + 60, paint)

                yPosition += 80f // Увеличиваем позицию для следующего трека
                trackNumber++ // Увеличиваем номер трека
            }

            pdfDocument.finishPage(page)

            // Сохраняем PDF файл
            val file = File(requireContext().getExternalFilesDir(null), "top_10_songs_report.pdf")
            pdfDocument.writeTo(FileOutputStream(file))

            // Закрываем документ
            pdfDocument.close()

            // Сообщаем пользователю о сохранении
            Toast.makeText(requireContext(), "PDF отчет сохранен: ${file.absolutePath}", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка при создании PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}