package com.example.gourmet.models

import android.net.Uri

data class Recipe(
    var id: Int,
    var image: Uri = Uri.EMPTY,
    var name: String = "",
    var description: String = "",
    var ingredients: List<Ingredient> = listOf(),
    var steps: List<Step> = listOf()
)
