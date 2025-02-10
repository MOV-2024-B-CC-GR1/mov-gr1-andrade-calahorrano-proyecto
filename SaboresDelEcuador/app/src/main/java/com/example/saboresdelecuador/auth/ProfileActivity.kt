package com.example.saboresdelecuador.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.saboresdelecuador.R
import com.example.saboresdelecuador.home.HomeActivity
import com.example.saboresdelecuador.recipes.RecipesActivity
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var editNickname: EditText
    private lateinit var editNewPassword: EditText
    private lateinit var editConfirmPassword: EditText
    private var originalNickname: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Inicializar Firestore
        db = FirebaseFirestore.getInstance()

        // Referencias a los campos de entrada
        editNickname = findViewById(R.id.editNickname)
        editNewPassword = findViewById(R.id.editNewPassword)
        editConfirmPassword = findViewById(R.id.editConfirmPassword)

        // 🟢 Verificar si el usuario está autenticado
        if (!isUserLoggedIn()) {
            redirectToLogin()
            return
        }

        // 🟢 Cargar datos del usuario al iniciar la actividad
        loadUserData()

        // 🟢 Botón para regresar
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Cierra la actividad y regresa a la anterior
        }

        // 🟢 Botón para cambiar la imagen de perfil
        val btnChangeProfileImage = findViewById<ImageView>(R.id.btnChangeProfileImage)
        btnChangeProfileImage.setOnClickListener {
            // Aquí puedes abrir un selector de imágenes para cambiar la foto de perfil
        }

        // 🟢 Guardar el nombre ingresado (Actualizar nickname)
        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfile)
        btnSaveProfile.setOnClickListener {
            updateNickname()
        }

        // 🟢 Botón para actualizar contraseña
        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)
        btnChangePassword.setOnClickListener {
            updatePassword()
        }

        // 🟢 Botón para cerrar sesión
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.visibility = Button.VISIBLE
        btnLogout.setOnClickListener {
            logoutUser()
        }

        // 🟢 Botón para cancelar cambios
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            resetFields()
        }
    }

    /**
     * Verifica si el usuario está autenticado.
     */
    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        return !sharedPreferences.getString("nickname", "").isNullOrEmpty()
    }

    /**
     * Redirige al usuario a la pantalla de inicio de sesión si no está autenticado.
     */
    private fun redirectToLogin() {
        Toast.makeText(this, "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_LONG).show()
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    /**
     * Carga el nickname del usuario desde SharedPreferences y Firestore.
     */
    private fun loadUserData() {
        val nickname = AuthManager.getSavedUserNickname(this)

        if (!nickname.isNullOrEmpty()) {
            editNickname.setText(nickname)
        } else {
            Toast.makeText(this, "Error: No se encontró el usuario", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateNickname() {
        val oldNickname = AuthManager.getSavedUserNickname(this) ?: ""
        val newNickname = editNickname.text.toString().trim()

        if (newNickname.isEmpty()) {
            Toast.makeText(this, "El nickname no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        if (newNickname == oldNickname) {
            Toast.makeText(this, "No hay cambios para guardar", Toast.LENGTH_SHORT).show()
            return
        }

        AuthManager.updateUserNicknameInFirestore(
            this, oldNickname, newNickname,
            onSuccess = {
                Toast.makeText(this, "Nickname actualizado correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RecipesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            },
            onFailure = { error ->
                Toast.makeText(this, "Error al actualizar nickname: $error", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun updatePassword() {
        val userNickname = originalNickname
        val newPassword = editNewPassword.text.toString().trim()
        val confirmPassword = editConfirmPassword.text.toString().trim()

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Los campos de contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        AuthManager.updateUserPasswordInFirestore(
            userNickname, newPassword,
            onSuccess = {
                editNewPassword.text.clear()
                editConfirmPassword.text.clear()
                Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
            },
            onFailure = { error ->
                Toast.makeText(this, "Error al actualizar contraseña: $error", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun logoutUser() {
        Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()
        AuthManager.logoutUser(this)
    }

    private fun resetFields() {
        editNickname.setText(originalNickname)
        editNewPassword.text.clear()
        editConfirmPassword.text.clear()
        Toast.makeText(this, "Cambios descartados", Toast.LENGTH_SHORT).show()
    }
}