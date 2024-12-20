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

class AdminFragment : Fragment(R.layout.fragment_admin) {

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