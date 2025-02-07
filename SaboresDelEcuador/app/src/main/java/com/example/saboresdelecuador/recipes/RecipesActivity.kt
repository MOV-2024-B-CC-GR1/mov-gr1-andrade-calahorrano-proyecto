package com.example.saboresdelecuador.recipes

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
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
    private val allRecipes = mutableListOf<Recipe>() // Lista original con TODAS las recetas
    private var filteredRecipes = mutableListOf<Recipe>() // Lista filtrada

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

        // Cargar recetas y configurar Adapter
        loadDummyRecipes()
        recipesAdapter = RecipesAdapter(filteredRecipes)
        recyclerView.adapter = recipesAdapter

        // Configurar filtros de categorías
        setupCategoryFilters() // ⬅️ LLAMAR AQUÍ
    }

    private fun loadDummyRecipes() {
        val sampleRecipes = listOf(
            Recipe("Encebollado", "Sopa tradicional de pescado con yuca.", R.drawable.sample_recipe, "Costa"),
            Recipe("Ceviche", "Plato frío con mariscos y limón.", R.drawable.sample_recipe, "Costa"),
            Recipe("Fritada", "Carne de cerdo frita con mote.", R.drawable.sample_recipe, "Sierra"),
            Recipe("Seco de Pollo", "Guiso de pollo con arroz amarillo.", R.drawable.sample_recipe, "Oriente"),
            Recipe("Locro de Papa", "Sopa de papa con queso.", R.drawable.sample_recipe, "Sierra"),
            Recipe("Encocado", "Plato de mariscos con coco.", R.drawable.sample_recipe, "Costa"),
            Recipe("Maito", "Pescado envuelto en hojas de bijao.", R.drawable.sample_recipe, "Amazonia"),
            Recipe("Sopa Marinera", "Sopa con mariscos.", R.drawable.sample_recipe, "Galápagos")
        )

        allRecipes.addAll(sampleRecipes)
        filteredRecipes.addAll(allRecipes) // Mostrar todas al inicio
    }


    // ✅ Asegúrate de poner este método dentro de la clase `RecipesActivity`
    private fun setupCategoryFilters() {
        val selectedCategoryText = findViewById<TextView>(R.id.selectedCategoryText)
        val categories = mapOf(
            "All" to findViewById<LinearLayout>(R.id.categoryAll),
            "Costa" to findViewById<LinearLayout>(R.id.categoryCosta),
            "Sierra" to findViewById<LinearLayout>(R.id.categorySierra),
            "Oriente" to findViewById<LinearLayout>(R.id.categoryOriente),
            "Galápagos" to findViewById<LinearLayout>(R.id.categoryGalapagos)
        )

        for ((category, button) in categories) {
            button.setOnClickListener {
                filterRecipes(category)
                selectedCategoryText.text = "Categoría: $category"
            }
        }
    }

    private fun filterRecipes(category: String) {
        filteredRecipes.clear()
        if (category == "All") {
            filteredRecipes.addAll(allRecipes) // Mostrar todas las recetas
        } else {
            filteredRecipes.addAll(allRecipes.filter { it.category == category })
        }
        recipesAdapter.notifyDataSetChanged()
    }
}