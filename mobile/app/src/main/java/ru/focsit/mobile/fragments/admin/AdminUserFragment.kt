package ru.focsit.mobile.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.auth.SignUpRequest
import ru.focsit.mobile.repo.admin.AdminUserRepository

class AdminUserFragment : Fragment(R.layout.fragment_admin_user) {

    private lateinit var userRepository: AdminUserRepository
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userAdapter: AdminUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_user, container, false)

        // Инициализация RecyclerView и репозитория
        userRecyclerView = view.findViewById(R.id.recycler_users)
        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Инициализация репозитория
        userRepository = AdminUserRepository(requireContext())

        // Кнопка для добавления пользователя
        view.findViewById<Button>(R.id.button_add_user).setOnClickListener {
            showCreateUserDialog() // Показать диалог для создания нового пользователя
        }

        // Загрузить пользователей
        loadUsers()

        return view
    }

    // Метод для загрузки пользователей
    private fun loadUsers() {
        userRepository.getAllUsers { users ->
            if (users != null) {
                // Создаем адаптер с подтверждением
                userAdapter = AdminUserAdapter(
                    users,
                    { userId -> deleteUserById(userId) },
                    { userId -> showDeleteConfirmationDialog(userId) }
                )
                userRecyclerView.adapter = userAdapter
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить пользователей", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDeleteConfirmationDialog(userId: Long) {
        // Создаем диалог подтверждения
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Подтверждение удаления")
            .setMessage("Вы уверены, что хотите удалить этого пользователя?")
            .setPositiveButton("Да") { _, _ ->
                deleteUserById(userId) // Удаляем пользователя после подтверждения
            }
            .setNegativeButton("Отмена", null)
            .create()
        dialog.show()
    }

    // Метод для регистрации нового пользователя
    private fun registerNewUser(username: String, email: String, password: String, role: String) {
        val request = SignUpRequest(username, email, password, role)
        userRepository.registerUser(request) { response ->
            if (response != null) {
                Toast.makeText(requireContext(), "Пользователь успешно создан", Toast.LENGTH_SHORT).show()
                loadUsers() // Перезагрузка списка пользователей
            } else {
                Toast.makeText(requireContext(), "Ошибка при создании пользователя", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showCreateUserDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_create_user, null)

        // Найдите Spinner и установите адаптер
        val roleSpinner = dialogView.findViewById<Spinner>(R.id.spinner_role)
        val rolesArray = resources.getStringArray(R.array.roles)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, rolesArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter

        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Создать пользователя")
            .setView(dialogView)
            .setPositiveButton("Создать") { _, _ ->
                val username = dialogView.findViewById<EditText>(R.id.input_username).text.toString()
                val email = dialogView.findViewById<EditText>(R.id.input_email).text.toString()
                val password = dialogView.findViewById<EditText>(R.id.input_password).text.toString()
                val role = roleSpinner.selectedItem.toString() // Получите выбранную роль
                registerNewUser(username, email, password, role)
            }
            .setNegativeButton("Отмена", null)
            .create()
        dialog.show()
    }

    // Метод для удаления пользователя по ID
    private fun deleteUserById(userId: Long) {
        userRepository.deleteUser(userId) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Пользователь удален", Toast.LENGTH_SHORT).show()
                loadUsers()  // Перезагрузим пользователей после удаления
            } else {
                Toast.makeText(requireContext(), "Ошибка удаления пользователя", Toast.LENGTH_SHORT).show()
            }
        }
    }
}