package com.example.gourmet.vmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.gourmet.models.Recipe

class CreateRecipeViewModel  : ViewModel() {
    val recipe: Recipe = Recipe("", "", "", emptyList(), emptyList(), emptyList(), Uri.EMPTY)
    var dishPhoto: String = ""
    var stepPhotos: List<String> = emptyList()
    var tags: List<String> = emptyList()

    fun onSaveRecipe() {

    }
}