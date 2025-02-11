package com.example.saboresdelecuador.recipes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.saboresdelecuador.R
import com.example.saboresdelecuador.recipe_form.RecipeFormActivity
import com.google.firebase.firestore.FirebaseFirestore

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var spinnerRegion: Spinner
    private lateinit var recipeTitle: TextView
    private lateinit var recipeDescription: TextView
    private lateinit var recipeIngredients: TextView
    private lateinit var recipeSteps: TextView

    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Vistas
        btnBack = findViewById(R.id.btnBack)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        spinnerRegion = findViewById(R.id.spinnerRegion)
        recipeTitle = findViewById(R.id.recipeTitle)
        recipeDescription = findViewById(R.id.recipeDescription)
        recipeIngredients = findViewById(R.id.recipeIngredients)
        recipeSteps = findViewById(R.id.recipeSteps)

        val recipeId = intent.getStringExtra("RECIPE_ID") // Obtenemos el ID de la receta desde el intent

        if (recipeId != null) {
            // Cargar los detalles de la receta desde Firestore
            loadRecipeDetails(recipeId)
        }

        // Configurar el botón de regreso
        btnBack.setOnClickListener {
            finish() // Cierra la actividad y vuelve a la anterior
        }

        // Configurar Spinner con Listener
        val regiones = resources.getStringArray(R.array.region_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, regiones)
        spinnerRegion.adapter = adapter

        spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val regionSeleccionada = regiones[position]
                Toast.makeText(applicationContext, "Región seleccionada: $regionSeleccionada", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Configurar el botón de actualizar (abre RecipeFormActivity)
        btnUpdate.setOnClickListener {
            val intent = Intent(this, RecipeFormActivity::class.java).apply {
                putExtra("recipeId", recipeId) // Pasar el ID de la receta
                putExtra("title", recipeTitle.text.toString())
                putExtra("description", recipeDescription.text.toString())
                putExtra("ingredients", recipeIngredients.text.toString())
                putExtra("steps", recipeSteps.text.toString())
                putExtra("region", spinnerRegion.selectedItem.toString()) // Agregar la región seleccionada
            }
            startActivity(intent)
        }

        // Configurar el botón de eliminar
        btnDelete.setOnClickListener {
            if (recipeId != null) {
                deleteRecipe(recipeId)
            }
        }
    }

    // Cargar los detalles de la receta desde Firestore
    private fun loadRecipeDetails(recipeId: String) {
        db.collection("Recetas").document(recipeId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val title = document.getString("nombre") ?: "Título no disponible"
                    val description = document.getString("descripcion") ?: "Descripción no disponible"
                    val region = document.getString("region") ?: "Región no especificada"

                    // Establecer los datos en las vistas correspondientes
                    recipeTitle.text = title
                    recipeDescription.text = description

                    // Establecer la región en el Spinner
                    val regionArray = resources.getStringArray(R.array.region_options)
                    spinnerRegion.setSelection(regionArray.indexOf(region))

                    // Recuperar ingredientes y pasos
                    loadIngredients(recipeId)
                    loadSteps(recipeId)
                } else {
                    Toast.makeText(this, "Receta no encontrada", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar la receta", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadIngredients(recipeId: String) {
        db.collection("Recetas").document(recipeId)
            .collection("Ingredientes")
            .get()
            .addOnSuccessListener { snapshot ->
                val ingredients = StringBuilder()
                for (document in snapshot) {
                    val ingredientName = document.getString("nombre") ?: "Ingrediente no disponible"
                    val ingredientQuantity = document.getString("cantidad") ?: "Cantidad no especificada"
                    val ingredientUnit = document.getString("unidad") ?: "Unidad no especificada"
                    ingredients.append("• $ingredientName ($ingredientQuantity $ingredientUnit)\n")
                }
                recipeIngredients.text = ingredients.toString()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar los ingredientes", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadSteps(recipeId: String) {
        db.collection("Recetas").document(recipeId)
            .collection("Pasos")
            .get()
            .addOnSuccessListener { snapshot ->
                val steps = StringBuilder()
                for ((index, document) in snapshot.withIndex()) {
                    val stepDescription = document.getString("descripcion") ?: "Paso no disponible"
                    steps.append("${index + 1}. $stepDescription\n")
                }
                recipeSteps.text = steps.toString()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar los pasos", Toast.LENGTH_SHORT).show()
            }
    }


    // Eliminar receta de Firestore
    private fun deleteRecipe(recipeId: String) {
        db.collection("Recetas").document(recipeId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Receta eliminada", Toast.LENGTH_SHORT).show()
                finish() // Regresar a la pantalla anterior
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al eliminar receta", Toast.LENGTH_SHORT).show()
            }
    }
}