package com.example.saboresdelecuador.recipe_form

import android.os.Bundle
import android.widget.ArrayAdapter
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

    private var recipeId: String? = null // ID de la receta, puede ser nulo si es nuevo

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

        // Verificar si el intent trae el ID de la receta para editarla
        recipeId = intent.getStringExtra("recipeId") // Recibir el ID de la receta si es una edición

        if (recipeId != null) {
            // Si el ID existe, precargar datos
            edtRecipeName.setText(intent.getStringExtra("title") ?: "")
            edtRecipeDescription.setText(intent.getStringExtra("description") ?: "")
            edtIngredients.setText(intent.getStringExtra("ingredients") ?: "")
            edtSteps.setText(intent.getStringExtra("steps") ?: "")

            btnSave.text = "Actualizar Receta"
        } else {
            btnSave.text = "Agregar Receta"
        }

        // Configurar Spinner con valores de regiones
        val regiones = resources.getStringArray(R.array.region_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, regiones)
        spinnerRegion.adapter = adapter

        // Seleccionar la región correcta en el Spinner
        val selectedRegion = intent.getStringExtra("region") ?: ""
        if (selectedRegion.isNotEmpty()) {
            val position = regiones.indexOf(selectedRegion)
            if (position >= 0) {
                spinnerRegion.setSelection(position)
            }
        }

        // Configurar botón de guardar
        btnSave.setOnClickListener {
            if (recipeId != null) {
                // Si hay ID, se debe actualizar la receta
                updateRecipe(recipeId!!)
            } else {
                // Si no hay ID, se agrega una nueva receta
                saveRecipe()
            }
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

    // Método para cargar los datos de la receta para editar
    private fun loadRecipeData(recipeId: String) {
        val db = FirebaseFirestore.getInstance()

        // Obtener los datos de la receta
        db.collection("Recetas").document(recipeId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val recipe = document.data
                    edtRecipeName.setText(recipe?.get("nombre").toString())
                    edtRecipeDescription.setText(recipe?.get("descripcion").toString())
                    edtIngredients.setText(recipe?.get("ingredientes").toString())
                    edtSteps.setText(recipe?.get("pasos").toString())
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al cargar receta.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }

    // Método para actualizar la receta
    private fun updateRecipe(recipeId: String) {
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

        // Crear un objeto con los datos actualizados de la receta
        val recipe: Map<String, Any> = hashMapOf(
            "nombre" to recipeName,
            "region" to region,
            "descripcion" to recipeDescription
        )

        // Actualizar la receta en la colección 'Recetas'
        db.collection("Recetas").document(recipeId)
            .update(recipe)
            .addOnSuccessListener {
                Toast.makeText(this, "Receta actualizada con éxito.", Toast.LENGTH_SHORT).show()

                // Primero eliminar los ingredientes y pasos antiguos antes de agregar los nuevos
                deleteIngredients(recipeId) {
                    addIngredients(recipeId) // Reagregar ingredientes actualizados
                }

                deleteSteps(recipeId) {
                    addSteps(recipeId) // Reagregar pasos actualizados
                }

                // Cerrar la actividad después de actualizar
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al actualizar receta.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }

    // Función para actualizar ingredientes
    private fun deleteIngredients(recipeId: String, onComplete: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val ingredientsRef = db.collection("Recetas").document(recipeId).collection("Ingredientes")

        ingredientsRef.get().addOnSuccessListener { snapshot ->
            for (doc in snapshot) {
                doc.reference.delete()
            }
            onComplete() // Llamar cuando termine la eliminación
        }
    }

    private fun deleteSteps(recipeId: String, onComplete: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val stepsRef = db.collection("Recetas").document(recipeId).collection("Pasos")

        stepsRef.get().addOnSuccessListener { snapshot ->
            for (doc in snapshot) {
                doc.reference.delete()
            }
            onComplete() // Llamar cuando termine la eliminación
        }
    }
}