package com.example.saboresdelecuador.recipe_form

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.saboresdelecuador.R
import com.google.firebase.firestore.FirebaseFirestore

class RecipeFormActivity : AppCompatActivity() {
    private lateinit var edtRecipeName: EditText
    private lateinit var edtRecipeDescription: EditText
    private lateinit var spinnerRegion: Spinner
    private lateinit var edtIngredients: EditText
    private lateinit var edtSteps: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        edtRecipeName = findViewById(R.id.edtRecipeName)
        edtRecipeDescription = findViewById(R.id.edtRecipeDescription)
        spinnerRegion = findViewById(R.id.spinnerRegion)
        edtIngredients = findViewById(R.id.edtIngredients)
        edtSteps = findViewById(R.id.edtSteps)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        // Configurar botón de guardar
        btnSave.setOnClickListener {
            saveRecipe()
        }

        // Configurar botón de cancelar
        btnCancel.setOnClickListener {
            finish() // Cierra la actividad y vuelve a la anterior
        }
    }

    // Método para guardar la receta (simulado)
    private fun saveRecipe() {
        val recipeName = edtRecipeName.text.toString().trim()
        val recipeDescription = edtRecipeDescription.text.toString().trim()
        val region = spinnerRegion.selectedItem.toString()
        val ingredients = edtIngredients.text.toString().trim()
        val steps = edtSteps.text.toString().trim()

        if (recipeName.isEmpty() || recipeDescription.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Obtener la instancia de Firestore
        val db = FirebaseFirestore.getInstance()

        // Crear un objeto con los datos de la receta
        val recipe = hashMapOf(
            "nombre" to recipeName,
            "region" to region,
            "descripcion" to recipeDescription
        )

        // Guardar la receta en la colección 'Recetas'
        db.collection("Recetas")
            .add(recipe)
            .addOnSuccessListener { documentReference ->
                val recipeId = documentReference.id
                Toast.makeText(this, "Receta guardada con éxito.", Toast.LENGTH_SHORT).show()

                // Agregar ingredientes
                addIngredients(recipeId)

                // Agregar pasos
                addSteps(recipeId)

                // Cerrar la actividad después de guardar
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al guardar receta.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }

    // Función para agregar ingredientes
    private fun addIngredients(recipeId: String) {
        val ingredients = edtIngredients.text.toString().split(",").map { it.trim() }
        val db = FirebaseFirestore.getInstance()

        for (ingredient in ingredients) {
            val ingredientData = hashMapOf(
                "nombre" to ingredient,
                "cantidad" to "Cantidad no especificada",
                "unidad" to "Unidad no especificada"
            )

            db.collection("Recetas").document(recipeId)
                .collection("Ingredientes")
                .add(ingredientData)
                .addOnSuccessListener { documentReference ->
                    // Agregar ingrediente exitoso
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al agregar ingrediente.", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
        }
    }

    // Función para agregar pasos
    private fun addSteps(recipeId: String) {
        val stepsList = edtSteps.text.toString().split(",").map { it.trim() }
        val db = FirebaseFirestore.getInstance()

        for ((index, step) in stepsList.withIndex()) {
            val stepData = hashMapOf(
                "descripcion" to step,
                "orden" to (index + 1)
            )

            db.collection("Recetas").document(recipeId)
                .collection("Pasos")
                .add(stepData)
                .addOnSuccessListener { documentReference ->
                    // Agregar paso exitoso
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al agregar paso.", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
        }
    }
}