package com.example.saboresdelecuador.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        // Configurar botones con color correcto al inicio
        registerTab.isEnabled = false
        registerTab.setBackgroundColor(resources.getColor(R.color.yellow)) // Amarillo activo
        loginTab.setBackgroundColor(resources.getColor(R.color.gray)) // Gris inactivo

        registerTab.setOnClickListener {
            if (viewSwitcher.currentView.id != R.id.registerLayout) {
                viewSwitcher.showNext()
                registerTab.isEnabled = false
                loginTab.isEnabled = true
                registerTab.setBackgroundColor(resources.getColor(R.color.yellow))
                loginTab.setBackgroundColor(resources.getColor(R.color.gray))
            }
        }

        loginTab.setOnClickListener {
            if (viewSwitcher.currentView.id != R.id.loginLayout) {
                viewSwitcher.showPrevious()
                loginTab.isEnabled = false
                registerTab.isEnabled = true
                loginTab.setBackgroundColor(resources.getColor(R.color.yellow))
                registerTab.setBackgroundColor(resources.getColor(R.color.gray))
            }
        }
    }
}