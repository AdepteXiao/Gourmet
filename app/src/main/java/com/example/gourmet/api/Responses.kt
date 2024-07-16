package com.example.gourmet.api

import com.example.gourmet.models.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class FileUploadResponse(
    val url: String
)

@Serializable
data class GetRecipesResponse(
    val recipes: List<Recipe>
)