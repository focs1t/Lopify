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
import ru.focsit.mobile.fragments.moderator.ModeratorUserFragment
import ru.focsit.mobile.fragments.user.UserProfileFragment
import ru.focsit.mobile.fragments.user.UserSongFragment
import ru.focsit.mobile.utils.PreferencesHelper

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottom_nav_view)

        // Получаем роль пользователя из SharedPreferences
        val role = PreferencesHelper.getRole(this)

        if (role != null) {
            setupNavigation(role)
        } else {
            // Если роль не определена, показываем ошибку
            Toast.makeText(this, "User role is undefined", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupNavigation(role: String) {
        // В зависимости от роли, настраиваем вкладки навбара
        when (role) {
            "ROLE_MODERATOR" -> setupModeratorNavigation()
            "ROLE_ADMIN" -> setupAdminNavigation()
            "ROLE_USER" -> setupUserNavigation()
        }
    }

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

        switchFragment(AdminFragment())
    }

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

        switchFragment(ModeratorFragment())
    }

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

        switchFragment(UserProfileFragment())
    }

    // Переключаем фрагменты
    private fun switchFragment(fragment: Fragment): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
        return true
    }
}