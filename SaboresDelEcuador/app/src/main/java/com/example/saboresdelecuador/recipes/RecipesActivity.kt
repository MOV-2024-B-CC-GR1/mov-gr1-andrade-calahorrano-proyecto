package com.example.saboresdelecuador.recipes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saboresdelecuador.R
import com.example.saboresdelecuador.adapters.RecipesAdapter
import com.example.saboresdelecuador.models.Recipe

class RecipesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipesAdapter: RecipesAdapter
    private val recipesList = mutableListOf<Recipe>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewRecipes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // Configurar Adapter con datos simulados
        recipesAdapter = RecipesAdapter(recipesList)
        recyclerView.adapter = recipesAdapter

        // Cargar datos de prueba
        loadDummyRecipes()
    }

    private fun loadDummyRecipes() {
        val sampleRecipes = listOf(
            Recipe("Encebollado", "Sopa tradicional de pescado con yuca.", R.drawable.sample_recipe),
            Recipe("Ceviche", "Plato frío con mariscos y limón.", R.drawable.sample_recipe),
            Recipe("Fritada", "Carne de cerdo frita con mote.", R.drawable.sample_recipe),
            Recipe("Seco de Pollo", "Guiso de pollo con arroz amarillo.", R.drawable.sample_recipe),
            Recipe("Locro de Papa", "Sopa de papa con queso.", R.drawable.sample_recipe)
        )

        recipesList.addAll(sampleRecipes)
        recipesAdapter.notifyDataSetChanged()
    }
}