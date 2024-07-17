package com.example.gourmet.api

import android.util.Log
import com.example.gourmet.App
import com.example.gourmet.models.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("uploads/{fileName}")
    suspend fun getFile(@Path("fileName") fileName: String): Response<ResponseBody>

    @Multipart
    @POST("upload")
    suspend fun uploadFile(@Part image: MultipartBody.Part): Response<FileUploadResponse>

    @GET("api/recipes")
    suspend fun getAllRecipes(
        @Query("sortBy") sortBy: String?,
        @Query("direction") direction: String?
    ): Response<List<Recipe>>

    @GET("api/recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: String): Response<Recipe>

    @Multipart
    @POST("api/recipes")
    suspend fun addRecipe(
        @Part("recipe") recipe: Recipe,
        @Part dishPhoto: MultipartBody.Part,
        @Part stepPhotos: List<MultipartBody.Part>
    ): Response<Recipe>

    @PUT("api/recipes/{id}")
    suspend fun updateRecipe(@Path("id") id: String, @Body recipeDetails: Recipe): Response<Recipe>

    @DELETE("api/recipes/{id}")
    suspend fun deleteRecipe(@Path("id") id: String): Response<Unit>
}

object ApiClient {
    val api: Api by lazy {
        App.getRetrofit().create(Api::class.java)
    }
}


object ApiRequester {
    fun getRecipes(
        sortBy: String? = null,
        direction: String? = null,
        callback: (List<Recipe>?) -> Unit,
        onError: (Int, ResponseBody?) -> Unit
    ) {
        launchRequest(
            { ApiClient.api.getAllRecipes(sortBy, direction) },
            callback,
            onError,
            "Failed to get recipes"
        )
    }

    fun getRecipeById(
        id: String,
        callback: (Recipe?) -> Unit,
        onError: (Int, ResponseBody?) -> Unit
    ) {
        launchRequest(
            { ApiClient.api.getRecipeById(id) },
            callback,
            onError,
            "Failed to get recipe"
        )
    }

    fun addRecipe(
        recipe: Recipe,
        dishPhoto: MultipartBody.Part,
        stepPhotos: List<MultipartBody.Part>,
        callback: (Recipe?) -> Unit,
        onError: (Int, ResponseBody?) -> Unit
    ) {
        launchRequest(
            { ApiClient.api.addRecipe(recipe, dishPhoto, stepPhotos) },
            callback,
            onError,
            "Failed to add recipe"
        )
    }

    fun updateRecipe(
        id: String,
        recipeDetails: Recipe,
        callback: (Recipe?) -> Unit,
        onError: (Int, ResponseBody?) -> Unit
    ) {
        launchRequest(
            { ApiClient.api.updateRecipe(id, recipeDetails) },
            callback,
            onError,
            "Failed to update recipe"
        )
    }

    fun deleteRecipe(
        id: String,
        callback: () -> Unit,
        onError: (Int, ResponseBody?) -> Unit
    ) {
        launchRequest(
            { ApiClient.api.deleteRecipe(id) },
            { callback() },
            onError,
            "Failed to delete recipe"
        )
    }

    fun getFile(
        fileName: String,
        callback: (ResponseBody?) -> Unit,
        onError: (Int, ResponseBody?) -> Unit
    ) {
        launchRequest(
            { ApiClient.api.getFile(fileName) },
            callback,
            onError,
            "Failed to get file"
        )
    }

    fun uploadFile(
        image: MultipartBody.Part,
        callback: (FileUploadResponse?) -> Unit,
        onError: (Int, ResponseBody?) -> Unit
    ) {
        launchRequest(
            { ApiClient.api.uploadFile(image) },
            callback,
            onError,
            "Failed to upload file"
        )
    }
}

private fun <T> launchRequest(
    request: suspend () -> Response<T>,
    callback: (T?) -> Unit,
    onError: (Int, ResponseBody?) -> Unit,
    errorMessage: String
) {
    CoroutineScope(Dispatchers.IO).launch {
        val response = request()
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                callback(response.body())
            } else {
                val body: ResponseBody? = response.errorBody()
                Log.e("Requester", "$errorMessage}")
                onError(response.code(), body)
            }
        }
    }
}