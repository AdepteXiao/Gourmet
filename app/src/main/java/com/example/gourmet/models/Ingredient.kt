package com.example.gourmet.models

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient (
    var id: Int,
    var name: String,
    var quantity: Float,
    var measurement: String
)