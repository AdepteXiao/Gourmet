package com.example.gourmet.models

import android.net.Uri
import com.example.gourmet.UriSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Step(
    val num: Int,
    @Serializable(with = UriSerializer::class)
    val image: Uri,
    val description: String
)
