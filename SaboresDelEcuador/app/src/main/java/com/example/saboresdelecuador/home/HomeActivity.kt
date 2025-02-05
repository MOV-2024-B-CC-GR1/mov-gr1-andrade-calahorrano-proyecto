package com.example.saboresdelecuador.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.saboresdelecuador.R

class HomeActivity : AppCompatActivity() {
    private lateinit var viewSwitcher: ViewSwitcher
    private lateinit var registerTab: Button
    private lateinit var loginTab: Button
    private lateinit var actionButton: Button
    private lateinit var forgotPassword: TextView // Nueva opción para recuperar contraseña

    // Campos de Registro
    private lateinit var registerUsername: EditText
    private lateinit var registerEmail: EditText
    private lateinit var registerPassword: EditText

    // Campos de Login
    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText

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

        viewSwitcher = findViewById(R.id.viewSwitcher)
        registerTab = findViewById(R.id.registerTab)
        loginTab = findViewById(R.id.loginTab)
        actionButton = findViewById(R.id.createButton)
        forgotPassword = findViewById(R.id.forgotPasswordButton) // Nueva opción para recuperar contraseña

        // Obtener referencias de los campos de Registro
        registerUsername = findViewById(R.id.nicknameInput)
        registerEmail = findViewById(R.id.emailInput)
        registerPassword = findViewById(R.id.passwordInput)

        // Obtener referencias de los campos de Login
        loginUsername = findViewById(R.id.loginUsername)
        loginPassword = findViewById(R.id.loginPassword)

        // Configurar botones con color correcto al inicio
        updateButtonStyles()

//        // Configurar botones con color correcto al inicio
//        registerTab.isEnabled = false
//        registerTab.setBackgroundColor(resources.getColor(R.color.yellow)) // Amarillo activo
//        loginTab.setBackgroundColor(resources.getColor(R.color.gray)) // Gris inactivo

        registerTab.setOnClickListener {
//            if (viewSwitcher.currentView.id != R.id.registerLayout) {
//                viewSwitcher.showNext()
//                registerTab.isEnabled = false
//                loginTab.isEnabled = true
//                registerTab.setBackgroundColor(resources.getColor(R.color.yellow))
//                loginTab.setBackgroundColor(resources.getColor(R.color.gray))
//            }
            if (!isRegisterMode) {
                viewSwitcher.showNext()
                isRegisterMode = true
                updateButtonStyles()
            }
        }

        loginTab.setOnClickListener {
//            if (viewSwitcher.currentView.id != R.id.loginLayout) {
//                viewSwitcher.showPrevious()
//                loginTab.isEnabled = false
//                registerTab.isEnabled = true
//                loginTab.setBackgroundColor(resources.getColor(R.color.yellow))
//                registerTab.setBackgroundColor(resources.getColor(R.color.gray))
//            }
            if (isRegisterMode) {
                viewSwitcher.showPrevious()
                isRegisterMode = false
                updateButtonStyles()
            }
        }

        // Acción del botón para iniciar sesión
        actionButton.setOnClickListener {
            if (isRegisterMode) {
                registerUser()
            } else {
                loginUser()
            }
        }

        // Acción para recuperar contraseña
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
        val email = registerEmail.text.toString().trim()
        val password = registerPassword.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showToast("Por favor, completa todos los campos.")
            return
        }

        showToast("Registro exitoso para $username")
        // Aquí puedes agregar Firebase u otro sistema de autenticación.
    }

    private fun loginUser() {
        val username = loginUsername.text.toString().trim()
        val password = loginPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Por favor, ingresa tu usuario y contraseña")
            return
        }

        if (username == "admin" && password == "1234") {
            showToast("Inicio de sesión exitoso")
        } else {
            showToast("Usuario o contraseña incorrectos")
        }
    }

    private fun recoverPassword() {
        val username = loginUsername.text.toString().trim()

        if (username.isEmpty()) {
            showToast("Ingresa tu nombre de usuario para recuperar la contraseña")
        } else {
            showToast("Se ha enviado un enlace de recuperación a tu correo")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}