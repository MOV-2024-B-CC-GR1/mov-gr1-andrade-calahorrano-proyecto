package com.example.saboresdelecuador.recipes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saboresdelecuador.R
import com.example.saboresdelecuador.adapters.RecipesAdapter
import com.example.saboresdelecuador.auth.ProfileActivity
import com.example.saboresdelecuador.models.Recipe
import com.example.saboresdelecuador.recipe_form.RecipeFormActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

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
        loadRecipesFromFirestore()
        recipesAdapter = RecipesAdapter(filteredRecipes) { selectedRecipe ->
            openRecipeDetail(selectedRecipe)
        }
        recyclerView.adapter = recipesAdapter

        // Configurar filtros de categorías
        setupCategoryFilters()

        // Agregar funcionalidad al botón de perfil
        val profileButton = findViewById<ImageView>(R.id.profileButton)
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val fabAddRecipe = findViewById<FloatingActionButton>(R.id.fabAddRecipe)
        fabAddRecipe.setOnClickListener {
            val intent = Intent(this, RecipeFormActivity::class.java)
            startActivity(intent)
        }

    }

//    private fun loadDummyRecipes() {
//        val sampleRecipes = listOf(
//            Recipe("Encebollado", "Sopa tradicional de pescado con yuca.", R.drawable.sample_recipe, "Costa"),
//            Recipe("Ceviche", "Plato frío con mariscos y limón.", R.drawable.sample_recipe, "Costa"),
//            Recipe("Fritada", "Carne de cerdo frita con mote.", R.drawable.sample_recipe, "Sierra"),
//            Recipe("Seco de Pollo", "Guiso de pollo con arroz amarillo.", R.drawable.sample_recipe, "Oriente"),
//            Recipe("Locro de Papa", "Sopa de papa con queso.", R.drawable.sample_recipe, "Sierra"),
//            Recipe("Encocado", "Plato de mariscos con coco.", R.drawable.sample_recipe, "Costa"),
//            Recipe("Maito", "Pescado envuelto en hojas de bijao.", R.drawable.sample_recipe, "Amazonia"),
//            Recipe("Sopa Marinera", "Sopa con mariscos.", R.drawable.sample_recipe, "Galápagos")
//        )
//
//        allRecipes.addAll(sampleRecipes)
//        filteredRecipes.addAll(allRecipes) // Mostrar todas al inicio
//    }

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
            filteredRecipes.addAll(allRecipes)
            Toast.makeText(this, "Mostrando todas las recetas", Toast.LENGTH_SHORT).show()  // Mostrar todas las recetas
        } else {
            filteredRecipes.addAll(allRecipes.filter { it.category == category })
            Toast.makeText(this, "Filtrado por categoría: $category", Toast.LENGTH_SHORT).show()  // Mostrar filtradas
        }
        recipesAdapter.notifyDataSetChanged()
    }

    // 🔥 Método agregado para abrir RecipeDetailActivity
    private fun openRecipeDetail(recipe: Recipe) {
        val intent = Intent(this, RecipeDetailActivity::class.java).apply {
            putExtra("RECIPE_NAME", recipe.title)
            putExtra("RECIPE_DESCRIPTION", recipe.description)
        }
        startActivity(intent)
    }

    private fun loadRecipesFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        db.collection("Recetas")
            .get()
            .addOnSuccessListener { documents ->
                allRecipes.clear()
                for (document in documents) {
                    val recipe = Recipe(
                        id = document.id,  // El ID de la receta
                        title = document.getString("nombre") ?: "",  // Nombre de la receta
                        description = document.getString("descripcion") ?: "",  // Descripción
                        category = document.getString("region") ?: ""  // Categoría (región)
                    )
                    allRecipes.add(recipe)
                }

                // Inicialmente, mostrar todas las recetas
                filteredRecipes.addAll(allRecipes)
                recipesAdapter.notifyDataSetChanged()

                Toast.makeText(this, "Recetas cargadas con éxito", Toast.LENGTH_SHORT).show()  // Éxito en la carga
            }
            .addOnFailureListener { exception ->
                // Error al cargar las recetas
                Toast.makeText(this, "Error al cargar recetas", Toast.LENGTH_SHORT).show()  // Error al cargar
            }
    }

}