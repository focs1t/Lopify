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

class ModeratorFragment : Fragment(R.layout.fragment_moderator) {

    private val authRepository = AuthRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton: Button = view.findViewById(R.id.btn_logout)

        logoutButton.setOnClickListener {
            // Выполнить выход из аккаунта
            performLogout()
        }
    }

    private fun performLogout() {
        authRepository.logout { success ->
            if (success) {
                // Очистка токена
                PreferencesHelper.clearToken(requireContext())
                PreferencesHelper.clearRole(requireContext())

                // Переход на SignInActivity
                val intent = Intent(requireContext(), SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                // Показываем сообщение об ошибке (можно добавить Toast или Snackbar)
            }
        }
    }
}