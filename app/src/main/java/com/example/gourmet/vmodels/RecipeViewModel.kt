package com.example.gourmet.vmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmet.api.ApiClient
import com.example.gourmet.api.ApiRequester
import com.example.gourmet.models.Ingredient
import com.example.gourmet.models.Recipe
import com.example.gourmet.models.Step
import kotlinx.coroutines.launch

class RecipeViewModel(recipeId: String) : ViewModel() {
    lateinit var recipe: Recipe

    init {
        getRecipe(recipeId)
    }

    private fun getRecipe(id: String) {
        ApiRequester.getRecipeById(
            id = id,
            callback = { response ->
                response?.let {
                    recipe = response
                    Log.d("RecipeViewModel", "Recipe: $recipe")
                }
            },
            onError = { error, body ->
                Log.e("RecipeViewModel", "Error: $error, body: $body")
            }
        )
    }

//    fun onImageSelected(image: Uri) {
//        recipe.image = image
//    }
//
//    fun setSelectedType(choice: String) {
//
//        var type = choice
//    }

}