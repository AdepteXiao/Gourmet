package com.example.gourmet.models

import android.net.Uri
import com.example.gourmet.UriSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    var id: Int,
    var name: String = "",
    var description: String = "",
    var ingredients: List<Ingredient> = listOf(),
    var steps: List<Step> = listOf(),
    var tags: List<String>,
    @Serializable(with = UriSerializer::class)
    var image: Uri = Uri.EMPTY,
)
