package ru.focsit.mobile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import ru.focsit.mobile.data.auth.SignUpRequest
import ru.focsit.mobile.repo.AuthRepository

/**
 * Класс для обработки экрана регистрации.
 */
class SignUpActivity : Activity() {

    // Поле для ввода имени пользователя
    private lateinit var usernameInput: EditText

    // Поле для ввода адреса электронной почты
    private lateinit var emailInput: EditText

    // Поле для ввода пароля
    private lateinit var passwordInput: EditText

    // Кнопка для выполнения регистрации
    private lateinit var signUpButton: Button

    // Ссылка для перехода на экран входа
    private lateinit var goToSignInLink: TextView

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
        setContentView(R.layout.activity_sign_up)

        // Инициализация элементов интерфейса
        usernameInput = findViewById(R.id.usernameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        signUpButton = findViewById(R.id.signUpButton)
        goToSignInLink = findViewById(R.id.goToSignInLink)

        // Установка обработчика для кнопки регистрации
        signUpButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val signUpRequest = SignUpRequest(username, email, password, "ROLE_USER")
                authRepository.signUp(signUpRequest) { response ->
                    if (response != null) {
                        // Уведомление об успешной регистрации
                        Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()

                        // Переход на экран входа
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)

                        // Завершаем текущую активность
                        finish()
                    } else {
                        // Уведомление об ошибке регистрации
                        Toast.makeText(this, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Уведомление о необходимости заполнить все поля
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        // Установка обработчика для ссылки на экран входа
        goToSignInLink.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}