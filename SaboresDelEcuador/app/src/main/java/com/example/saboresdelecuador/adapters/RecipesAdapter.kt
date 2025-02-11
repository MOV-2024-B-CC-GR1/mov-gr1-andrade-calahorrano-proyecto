package com.example.saboresdelecuador.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saboresdelecuador.R
import com.example.saboresdelecuador.models.Recipe
import com.example.saboresdelecuador.recipes.RecipeDetailActivity

class RecipesAdapter(
    private var recipes: List<Recipe>,
    private val onRecipeClick: (Recipe) -> Unit  // 🔥 Agregar esta función
) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)
        val recipeDescription: TextView = itemView.findViewById(R.id.recipeDescription)
        //val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        val btnViewRecipe: Button = itemView.findViewById(R.id.btnViewRecipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeTitle.text = recipe.title
        holder.recipeDescription.text = recipe.description
        // No estamos utilizando la imagen por el momento

        // Configura el clic en el botón "Ver receta"
        holder.btnViewRecipe.setOnClickListener {
            // Pasar el ID de la receta a RecipeDetailActivity
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.id) // Asume que 'recipe.id' es el identificador único de la receta
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = recipes.size

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}