package com.example.saboresdelecuador.models

data class Recipe(
    val title: String,
    val description: String,
    val imageRes: Int,
    val category: String // Agregar este parámetro
)