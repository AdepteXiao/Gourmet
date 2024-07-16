package com.example.gourmet

import android.content.Context
import android.net.Uri
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Uri::class)
object UriSerializer : KSerializer<Uri> {
    override fun serialize(encoder: Encoder, value: Uri) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Uri {
        return Uri.parse(decoder.decodeString())
    }
}



fun prepareBitmapToRequest(bitmap: Bitmap, fileName: String): MultipartBody.Part {
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
    val data = bos.toByteArray()

    val reqFile = data.toRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", fileName, reqFile)
}

@RequiresApi(Build.VERSION_CODES.P)
fun Context.getMultipartImageFromUri(uri: Uri): MultipartBody.Part? {
    val bitmap = this.getBitmapFromUri(uri)
    bitmap?.let {
        return prepareBitmapToRequest(bitmap, "image.jpg")
    }
    return null
}

@RequiresApi(Build.VERSION_CODES.P)
fun Context.getBitmapFromUri(uri: Uri): Bitmap? {
    return try {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, uri))
    } catch (e: Exception) {
        null
    }
}