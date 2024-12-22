package ru.focsit.mobile.fragments.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.focsit.mobile.R
import ru.focsit.mobile.SignInActivity
import ru.focsit.mobile.repo.AuthRepository
import ru.focsit.mobile.utils.PreferencesHelper

/**
 * Фрагмент для административной панели, где пользователь может выйти из своей учетной записи.
 * Этот фрагмент отображает кнопку для выхода из системы и выполняет действия по выходу.
 */
class AdminFragment : Fragment(R.layout.fragment_admin) {

    private val authRepository = AuthRepository()

    /**
     * Метод вызывается при создании представления фрагмента.
     * Он находит кнопку для выхода и устанавливает обработчик нажатия.
     *
     * @param view Представление фрагмента.
     * @param savedInstanceState Сохраненное состояние фрагмента, если оно имеется.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton: Button = view.findViewById(R.id.btn_logout)

        // Устанавливаем обработчик нажатия на кнопку "Выйти"
        logoutButton.setOnClickListener {
            performLogout()
        }
    }

    /**
     * Метод для выполнения выхода из аккаунта:
     * - Очистка токена пользователя и роли.
     * - Переход на экран входа.
     */
    private fun performLogout() {
        authRepository.logout { success ->
            if (success) {
                // Очистка токена и роли из хранилища
                PreferencesHelper.clearToken(requireContext())
                PreferencesHelper.clearRole(requireContext())

                // Переход на экран входа (SignInActivity)
                val intent = Intent(requireContext(), SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}