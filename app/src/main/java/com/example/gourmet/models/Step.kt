package com.example.gourmet.models

import android.net.Uri

data class Step (
    val num: Int,
    val image: Uri,
    val description: String
)