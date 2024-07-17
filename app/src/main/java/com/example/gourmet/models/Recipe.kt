package com.example.gourmet.models

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.gourmet.UriSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Recipe  constructor(
    var id: String,
    var name: String = "",
    var description: String = "",
    var ingredients: List<Ingredient> = listOf(),
    var steps: List<Step> = listOf(),
    var tags: List<String>,
    @SerialName("dishPhotoUrl")
    @Serializable(with = UriSerializer::class)
    var image: Uri = Uri.EMPTY,
    @Serializable(with = LocalDateTimeSerializer::class)
    var createdAt: LocalDateTime = LocalDateTime.now(),
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }
}