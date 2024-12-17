package ru.focsit.mobile.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.focsit.mobile.R
import ru.focsit.mobile.databinding.FragmentHomeBinding
import ru.focsit.mobile.repository.HomeRepository
import ru.focsit.mobile.utils.PreferencesHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeRepository: HomeRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Инициализация репозитория
        homeRepository = HomeRepository(requireContext())

        val textView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Получаем элементы интерфейса
        val userProfileTextView = binding.textUserProfile
        val likedTracksButton = binding.btnLikedTracks

        // Получаем пользователя и обновляем интерфейс
        if (PreferencesHelper.isUser(requireContext())) {
            // Получаем профиль пользователя
            homeRepository.getUserProfile { user ->
                if (user != null) {
                    // Отображаем имя пользователя и делаем его кликабельным
                    userProfileTextView.text = user.username
                    userProfileTextView.setOnClickListener {
                        // Логика перехода на экран профиля (можно заменить на более подходящую)
                        Toast.makeText(requireContext(), "Переход к профилю ${user.username}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    userProfileTextView.text = "Не удалось загрузить профиль"
                }
            }

            // Получаем любимые треки пользователя
            likedTracksButton.setOnClickListener {
                homeRepository.getLikedTracks { playlist ->
                    if (playlist != null) {
                        // Показать список любимых треков (можно реализовать в виде RecyclerView)
                        Toast.makeText(requireContext(), "Загружены любимые треки", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Не удалось загрузить любимые треки", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Получаем кнопки
        val btnCountries = root.findViewById<Button>(R.id.btnCountries)
        val btnGenres = root.findViewById<Button>(R.id.btnGenres)
        val btnRoles = root.findViewById<Button>(R.id.btnRoles)
        val btnUsers = root.findViewById<Button>(R.id.btnUsers)
        val btnAlbums = root.findViewById<Button>(R.id.btnAlbums)
        val btnArtists = root.findViewById<Button>(R.id.btnArtists)
        val btnComments = root.findViewById<Button>(R.id.btnComments)
        val btnTracks = root.findViewById<Button>(R.id.btnTracks)
        val btnUserProfiles = root.findViewById<Button>(R.id.btnUserProfiles)

        // Управляем видимостью кнопок в зависимости от роли пользователя
        when {
            PreferencesHelper.isAdmin(requireContext()) -> {
                // Все кнопки видимы для админа
                btnCountries.visibility = View.VISIBLE
                btnGenres.visibility = View.VISIBLE
                btnRoles.visibility = View.VISIBLE
                btnUsers.visibility = View.VISIBLE
                btnAlbums.visibility = View.GONE
                btnArtists.visibility = View.GONE
                btnComments.visibility = View.GONE
                btnTracks.visibility = View.GONE
                btnUserProfiles.visibility = View.GONE
                binding.btnLikedTracks.visibility = View.GONE
                userProfileTextView.visibility = View.GONE
            }
            PreferencesHelper.isModerator(requireContext()) -> {
                // Модератор видит только определенные кнопки
                btnCountries.visibility = View.GONE
                btnGenres.visibility = View.GONE
                btnRoles.visibility = View.GONE
                btnUsers.visibility = View.GONE
                btnAlbums.visibility = View.VISIBLE
                btnArtists.visibility = View.VISIBLE
                btnComments.visibility = View.VISIBLE
                btnTracks.visibility = View.VISIBLE
                btnUserProfiles.visibility = View.VISIBLE
                binding.btnLikedTracks.visibility = View.GONE
                userProfileTextView.visibility = View.GONE
            }
            PreferencesHelper.isUser(requireContext()) -> {
                // Пользователь видит только определенные кнопки
                btnCountries.visibility = View.GONE
                btnGenres.visibility = View.GONE
                btnRoles.visibility = View.GONE
                btnUsers.visibility = View.GONE
                btnAlbums.visibility = View.GONE
                btnArtists.visibility = View.GONE
                btnComments.visibility = View.GONE
                btnTracks.visibility = View.GONE
                btnUserProfiles.visibility = View.GONE
                binding.btnLikedTracks.visibility = View.VISIBLE
                userProfileTextView.visibility = View.VISIBLE
            }
            else -> {
                // Скрыть все кнопки, если роль не определена
                btnCountries.visibility = View.GONE
                btnGenres.visibility = View.GONE
                btnRoles.visibility = View.GONE
                btnUsers.visibility = View.GONE
                btnAlbums.visibility = View.GONE
                btnArtists.visibility = View.GONE
                btnComments.visibility = View.GONE
                btnTracks.visibility = View.GONE
                btnUserProfiles.visibility = View.GONE
                binding.btnLikedTracks.visibility = View.GONE
                userProfileTextView.visibility = View.GONE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}