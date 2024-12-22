package ru.focsit.mobile.fragments.moderator

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.focsit.mobile.R
import ru.focsit.mobile.SignInActivity
import ru.focsit.mobile.repo.AuthRepository
import ru.focsit.mobile.utils.PreferencesHelper

/**
 * Фрагмент для представления интерфейса модератора.
 * Позволяет пользователю выйти из своего аккаунта.
 */
class ModeratorFragment : Fragment(R.layout.fragment_moderator) {

    private val authRepository = AuthRepository()

    /**
     * Этот метод вызывается, когда фрагмент был привязан к виду.
     * Настраивает кнопку выхода, чтобы инициировать процесс выхода из аккаунта.
     *
     * @param view Представление фрагмента.
     * @param savedInstanceState Сохранённое состояние (не используется).
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton: Button = view.findViewById(R.id.btn_logout)

        // Устанавливаем обработчик для кнопки выхода
        logoutButton.setOnClickListener {
            // Выполнить выход из аккаунта
            performLogout()
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
                // Очистка данных о токене и роли пользователя из хранилища
                PreferencesHelper.clearToken(requireContext())
                PreferencesHelper.clearRole(requireContext())

                // Переход на экран входа в приложение (SignInActivity)
                val intent = Intent(requireContext(), SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}