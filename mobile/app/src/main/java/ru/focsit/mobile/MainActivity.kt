package ru.focsit.mobile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.focsit.mobile.fragments.admin.AdminFragment
import ru.focsit.mobile.fragments.admin.AdminUser.AdminUserFragment
import ru.focsit.mobile.fragments.moderator.ModeratorFragment
import ru.focsit.mobile.fragments.moderator.ModeratorSong.ModeratorSongFragment
import ru.focsit.mobile.fragments.moderator.ModeratorUser.ModeratorUserFragment
import ru.focsit.mobile.fragments.user.UserProfile.UserProfileFragment
import ru.focsit.mobile.fragments.user.UserSong.UserSongFragment
import ru.focsit.mobile.utils.PreferencesHelper

/**
 * Главная активность приложения. Управляет отображением фрагментов и навигацией в зависимости от роли пользователя.
 */
class MainActivity : AppCompatActivity() {

    // Нижняя панель навигации
    private lateinit var bottomNavView: BottomNavigationView

    /**
     * Вызывается при создании активности. Устанавливает начальную конфигурацию.
     *
     * @param savedInstanceState Состояние активности, сохраненное ранее.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottom_nav_view)

        // Получение роли пользователя из SharedPreferences
        val role = PreferencesHelper.getRole(this)

        if (role != null) {
            setupNavigation(role)
        } else {
            // Если роль не определена, отображаем уведомление
            Toast.makeText(this, "Роль пользователя не определена", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Настраивает нижнюю панель навигации в зависимости от роли пользователя.
     *
     * @param role Роль пользователя (ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR).
     */
    private fun setupNavigation(role: String) {
        when (role) {
            "ROLE_MODERATOR" -> setupModeratorNavigation()
            "ROLE_ADMIN" -> setupAdminNavigation()
            "ROLE_USER" -> setupUserNavigation()
        }
    }

    /**
     * Настройка навигации для администратора.
     */
    private fun setupAdminNavigation() {
        bottomNavView.menu.clear()
        bottomNavView.inflateMenu(R.menu.menu_admin)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_admin -> switchFragment(AdminFragment())
                R.id.nav_user_management -> switchFragment(AdminUserFragment())
                else -> false
            }
        }

        // Отображаем фрагмент по умолчанию
        switchFragment(AdminFragment())
    }

    /**
     * Настройка навигации для модератора.
     */
    private fun setupModeratorNavigation() {
        bottomNavView.menu.clear()
        bottomNavView.inflateMenu(R.menu.menu_moderator)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_moderator -> switchFragment(ModeratorFragment())
                R.id.nav_song_management -> switchFragment(ModeratorSongFragment())
                R.id.nav_user_management -> switchFragment(ModeratorUserFragment())
                else -> false
            }
        }

        // Отображаем фрагмент по умолчанию
        switchFragment(ModeratorFragment())
    }

    /**
     * Настройка навигации для пользователя.
     */
    private fun setupUserNavigation() {
        bottomNavView.menu.clear()
        bottomNavView.inflateMenu(R.menu.menu_user)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_user_profile -> switchFragment(UserProfileFragment())
                R.id.nav_user_songs -> switchFragment(UserSongFragment())
                else -> false
            }
        }

        // Отображаем фрагмент по умолчанию
        switchFragment(UserProfileFragment())
    }

    /**
     * Переключение фрагментов.
     *
     * @param fragment Фрагмент, который нужно отобразить.
     * @return true, если фрагмент был переключен успешно.
     */
    private fun switchFragment(fragment: Fragment): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
        return true
    }
}