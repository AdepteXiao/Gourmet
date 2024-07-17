package com.example.gourmet.models

import android.net.Uri
import com.example.gourmet.UriSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step(
    val id: Int,
    @SerialName("picUrl")
    @Serializable(with = UriSerializer::class)
    val image: Uri,
    val description: String
)
