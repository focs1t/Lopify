package ru.focsit.mobile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import ru.focsit.mobile.data.auth.SignInRequest
import ru.focsit.mobile.repo.AuthRepository
import ru.focsit.mobile.utils.PreferencesHelper

/**
 * Класс для обработки экрана входа в приложение.
 */
class SignInActivity : Activity() {

    // Поле для ввода имени пользователя
    private lateinit var usernameInput: EditText

    // Поле для ввода пароля
    private lateinit var passwordInput: EditText

    // Кнопка для выполнения входа
    private lateinit var signInButton: Button

    // Ссылка для перехода на экран регистрации
    private lateinit var goToSignUpLink: TextView

    // Репозиторий для выполнения операций авторизации
    private val authRepository = AuthRepository()

    /**
     * Вызывается при создании активности. Инициализирует элементы интерфейса и задает обработчики событий.
     *
     * @param savedInstanceState Состояние активности, сохраненное ранее.
     */
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Инициализация элементов интерфейса
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        signInButton = findViewById(R.id.signInButton)
        goToSignUpLink = findViewById(R.id.goToSignUpLink)

        // Установка обработчика для кнопки входа
        signInButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val signInRequest = SignInRequest(username, password)
                authRepository.signIn(signInRequest) { response ->
                    if (response != null) {
                        // Логирование успешного ответа
                        Log.d("SignIn", "Response: $response")
                        Toast.makeText(this, "Вход успешен", Toast.LENGTH_SHORT).show()

                        // Сохранение токена и роли пользователя
                        PreferencesHelper.saveToken(this, response.token)
                        PreferencesHelper.saveRole(this, response.role)

                        // Переход на главный экран приложения
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        // Логирование ошибки
                        Log.e("SignIn", "Ошибка входа: Response is null")
                        Toast.makeText(this, "Ошибка входа", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        // Установка обработчика для ссылки на экран регистрации
        goToSignUpLink.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}