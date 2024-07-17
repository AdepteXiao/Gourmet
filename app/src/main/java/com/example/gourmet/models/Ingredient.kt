package com.example.gourmet.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient (
    var id: Int,
    var name: String,
    @SerialName("amount")
    var quantity: Float,
    @SerialName("unitType")
    var measurement: String
)