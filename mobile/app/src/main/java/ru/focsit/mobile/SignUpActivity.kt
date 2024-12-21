package ru.focsit.mobile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.focsit.mobile.data.auth.SignUpRequest
import ru.focsit.mobile.repo.AuthRepository

class SignUpActivity : Activity() {

    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signUpButton: Button
    private lateinit var goToSignInLink: TextView

    private val authRepository = AuthRepository()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        usernameInput = findViewById(R.id.usernameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        signUpButton = findViewById(R.id.signUpButton)
        goToSignInLink = findViewById(R.id.goToSignInLink)

        // Обработчик для кнопки регистрации
        signUpButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val signUpRequest = SignUpRequest(username, email, password, "ROLE_USER")
                authRepository.signUp(signUpRequest) { response ->
                    if (response != null) {
                        Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                        // Переход на экран входа после успешной регистрации
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                        finish()  // Завершаем текущую активность (SignUpActivity)
                    } else {
                        Toast.makeText(this, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        // Переход на экран входа
        goToSignInLink.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}