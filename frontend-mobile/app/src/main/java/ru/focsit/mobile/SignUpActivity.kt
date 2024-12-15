package ru.focsit.mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.focsit.mobile.data.Country
import ru.focsit.mobile.data.auth.SignUpRequest
import ru.focsit.mobile.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var countrySpinner: Spinner
    private lateinit var signUpButton: Button
    private lateinit var goToSignInButton: Button

    private val authRepository = AuthRepository()

    private var countriesList: List<Country> = emptyList()
    private var selectedCountryId: Long = 1 // ID по умолчанию, можно обновить после выбора

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        usernameInput = findViewById(R.id.usernameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        countrySpinner = findViewById(R.id.countrySpinner)
        signUpButton = findViewById(R.id.signUpButton)
        goToSignInButton = findViewById(R.id.goToSignInButton)

        // Загружаем список стран с бэкенда
        loadCountries()

        // Обработчик для кнопки регистрации
        signUpButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val signUpRequest = SignUpRequest(username, email, password, selectedCountryId, 1) // userRoleId = 1 (например)
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
        goToSignInButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    // Функция для загрузки стран с бэкенда
    private fun loadCountries() {
        lifecycleScope.launch(Dispatchers.IO) {
            val call = RetrofitClient.authApi.getCountries()
            call.enqueue(object : Callback<List<Country>> {
                override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                    if (response.isSuccessful) {
                        countriesList = response.body() ?: emptyList()
                        setupCountrySpinner()
                    } else {
                        Toast.makeText(this@SignUpActivity, "Ошибка загрузки стран", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    // Настроить Spinner с данными о странах
    private fun setupCountrySpinner() {
        val countryNames = countriesList.map { it.countryName }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = adapter

        // Устанавливаем обработчик выбора страны
        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCountryId = countriesList[position].countryId
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Обработчик на случай, если ничего не выбрано
            }
        }
    }
}