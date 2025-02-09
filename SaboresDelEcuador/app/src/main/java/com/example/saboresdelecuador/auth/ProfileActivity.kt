package com.example.saboresdelecuador.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.saboresdelecuador.R

class ProfileActivity : AppCompatActivity() {
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

        // 🟢 Guardar el nombre ingresado
        val editNickname = findViewById<EditText>(R.id.editNickname)
        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfile)
        btnSaveProfile.setOnClickListener {
            val nickname = editNickname.text.toString()
            // Aquí podrías guardar el nombre en SharedPreferences o en una base de datos local
        }

        // 🟢 Cancelar cambios
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            finish() // Cierra la actividad sin guardar cambios
        }
    }
}