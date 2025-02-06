package com.example.saboresdelecuador.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.saboresdelecuador.R
import com.example.saboresdelecuador.auth.AuthManager

class HomeActivity : AppCompatActivity() {
    private lateinit var viewSwitcher: ViewSwitcher
    private lateinit var registerTab: Button
    private lateinit var loginTab: Button
    private lateinit var actionButton: Button
    private lateinit var forgotPassword: TextView

    // Campos de Registro
    private lateinit var registerUsername: EditText
    private lateinit var registerPassword: EditText

    // Campos de Login
    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button

    private var isRegisterMode = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar componentes
        viewSwitcher = findViewById(R.id.viewSwitcher)
        registerTab = findViewById(R.id.registerTab)
        loginTab = findViewById(R.id.loginTab)
        actionButton = findViewById(R.id.createButton)
        forgotPassword = findViewById(R.id.forgotPasswordButton)

        registerUsername = findViewById(R.id.nicknameInput)
        registerPassword = findViewById(R.id.passwordInput)

        loginUsername = findViewById(R.id.loginUsername)
        loginPassword = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)

        updateButtonStyles()

        registerTab.setOnClickListener {
            if (!isRegisterMode) {
                viewSwitcher.showNext()
                isRegisterMode = true
                updateButtonStyles()
            }
        }

        loginTab.setOnClickListener {
            if (isRegisterMode) {
                viewSwitcher.showPrevious()
                isRegisterMode = false
                updateButtonStyles()
            }
        }

        actionButton.setOnClickListener {
            if (isRegisterMode) {
                registerUser()
            } else {
                val nickname = loginUsername.text.toString().trim()
                val password = loginPassword.text.toString().trim()

                Log.d("LOGIN_FIRESTORE", "Botón presionado. Usuario: $nickname, Contraseña: $password")
                loginUser(nickname, password)
            }
        }

        loginButton.setOnClickListener {
            Log.d("LOGIN_BUTTON", "Botón de inicio de sesión presionado")

            val username = loginUsername.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            Log.d("LOGIN_BUTTON", "Usuario ingresado: $username")
            Log.d("LOGIN_BUTTON", "Contraseña ingresada: $password")

            if (username.isEmpty() || password.isEmpty()) {
                showToast("Por favor, ingresa tu usuario y contraseña")
                return@setOnClickListener
            }

            loginUser(username, password)
        }

        forgotPassword.setOnClickListener {
            recoverPassword()
        }
    }

    private fun updateButtonStyles() {
        if (isRegisterMode) {
            registerTab.isEnabled = false
            loginTab.isEnabled = true
            registerTab.setBackgroundColor(resources.getColor(R.color.yellow))
            loginTab.setBackgroundColor(resources.getColor(R.color.gray))
            actionButton.text = "Crear cuenta"
            actionButton.setBackgroundColor(resources.getColor(R.color.yellow))
        } else {
            loginTab.isEnabled = false
            registerTab.isEnabled = true
            loginTab.setBackgroundColor(resources.getColor(R.color.yellow))
            registerTab.setBackgroundColor(resources.getColor(R.color.gray))
            actionButton.text = "Iniciar sesión"
            actionButton.setBackgroundColor(resources.getColor(R.color.green))
        }
    }

    private fun registerUser() {
        val username = registerUsername.text.toString().trim()
        val password = registerPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Por favor, completa todos los campos.")
            return
        }

        AuthManager.registerUser(
            this, username, password,
            onSuccess = {
                showToast("Registro exitoso")
                val intent = Intent(this, DashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            },
            onFailure = { message -> showToast(message) }
        )
    }

    private fun loginUser(username: String, password: String) {
        Log.d("LOGIN_FIRESTORE", "Intentando iniciar sesión con usuario: $username")

        AuthManager.loginUser(
            this, username, password,
            onSuccess = {
                Log.d("LOGIN_FIRESTORE", "Inicio de sesión exitoso")
                showToast("Inicio de sesión exitoso")
                val intent = Intent(this, DashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            },
            onFailure = { message ->
                Log.d("LOGIN_FIRESTORE", "Error en login: $message")
                showToast(message)
            }
        )
    }

    private fun recoverPassword() {
        val username = loginUsername.text.toString().trim()

        if (username.isEmpty()) {
            showToast("Por favor, ingresa tu nombre de usuario")
            return
        }

        Log.d("RECOVER_PASSWORD", "Buscando usuario: $username")

        AuthManager.recoverPassword(
            this, username,
            onSuccess = { mensaje ->
                Log.d("RECOVER_PASSWORD", "Usuario encontrado: $username")
                showToast(mensaje) // ✅ Muestra la contraseña recuperada
            },
            onFailure = { message ->
                Log.d("RECOVER_PASSWORD", "Error: $message")
                showToast(message) // ❌ Usuario no encontrado
            }
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}