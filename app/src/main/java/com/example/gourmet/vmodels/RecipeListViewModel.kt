package com.example.gourmet.vmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gourmet.api.ApiRequester
import com.example.gourmet.models.Recipe
import kotlinx.coroutines.flow.MutableStateFlow

class RecipeListViewModel : ViewModel() {
    val recipeList: MutableStateFlow<List<Recipe>> = MutableStateFlow(emptyList())
    private val sortType = mutableStateOf("")
    private val direction = mutableStateOf("")

    init {
        getRecipes()
    }

    private fun getRecipes() {
        ApiRequester.getRecipes(
            sortType.value,
            direction.value,
            callback = { response ->
                response?.let {
                    recipeList.value = response
                    Log.d("RecipeListViewModel", "Recipes: ${recipeList.value}")
                }
            },
            onError = { error, body ->
                Log.e("RecipeListViewModel", "Error: $error, body: $body")
            }
        )

    }
}
