package com.example.saboresdelecuador.models

data class Recipe(
    val id: String,  // ID único de la receta
    val title: String,
    val description: String,
    val category: String, // Categoría
    //val imageUrl: String // Campo para la URL de la imagen
)