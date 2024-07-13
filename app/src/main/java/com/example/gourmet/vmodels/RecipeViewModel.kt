package com.example.gourmet.vmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.gourmet.models.Ingredient
import com.example.gourmet.models.Recipe
import com.example.gourmet.models.Step

class RecipeViewModel(recipeId: Int?) : ViewModel() {
    lateinit var recipe: Recipe

    init {
        recipe = if (recipeId != null) {
            getRecipe(recipeId)
        } else {
            Recipe(0, Uri.parse("android.resource://com.example.gourmet/drawable/food"), "", "", listOf(), listOf())
        }
    }

    private fun getRecipe(id: Int): Recipe {
        return Recipe(
            id,
            Uri.parse("android.resource://com.example.gourmet/drawable/food"),
            "Beef and Mustard Pie",
            "A delicious beef and mustard pie",
            listOf(
                Ingredient(0, "Beef", 500f, "g"),
                Ingredient(1, "Shortcrust pastry", 500f, "g"),
                Ingredient(2, "Mustard", 1f, "tbsp")
            ),
            listOf(
                Step(1, Uri.parse("android.resource://com.example.gourmet/drawable/food"), "Preheat oven to 180C"),
                Step(2, Uri.parse("android.resource://com.example.gourmet/drawable/food"), "Brown the beef"),
                Step(3, Uri.parse("android.resource://com.example.gourmet/drawable/food"), "Add mustard to beef"),
                Step(4, Uri.parse("android.resource://com.example.gourmet/drawable/food"), "Line pie dish with pastry"),
                Step(5, Uri.parse("android.resource://com.example.gourmet/drawable/food"), "Add beef and mustard"),
                Step(6, Uri.parse("android.resource://com.example.gourmet/drawable/food"), "Cover with pastry"),
                Step(7, Uri.parse("android.resource://com.example.gourmet/drawable/food"), "Bake for 30 minutes")
            )
        )

    }

    fun onImageSelected(image: Uri) {
        recipe.image = image
    }


}