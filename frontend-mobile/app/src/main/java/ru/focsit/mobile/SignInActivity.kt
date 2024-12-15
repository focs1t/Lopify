package ru.focsit.mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.focsit.mobile.data.auth.SignInRequest
import ru.focsit.mobile.repository.AuthRepository
import ru.focsit.mobile.utils.PreferencesHelper

class SignInActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signInButton: Button
    private lateinit var goToSignUpButton: Button

    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        signInButton = findViewById(R.id.signInButton)
        goToSignUpButton = findViewById(R.id.goToSignUpButton)

        // Обработчик для кнопки входа
        signInButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val signInRequest = SignInRequest(username, password)
                authRepository.signIn(signInRequest) { response ->
                    if (response != null) {
                        // Логируем успешный ответ
                        Log.d("SignIn", "Response: $response")
                        Toast.makeText(this, "Вход успешен", Toast.LENGTH_SHORT).show()

                        // Сохраняем токен
                        PreferencesHelper.saveToken(this, response.token)

                        // Переход на MainActivity и открытие вкладки Home
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        // Логируем ошибку
                        Log.e("SignIn", "Ошибка входа: Response is null")
                        Toast.makeText(this, "Ошибка входа", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        // Переход на экран регистрации
        goToSignUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}