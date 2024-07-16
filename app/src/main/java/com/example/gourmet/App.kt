package com.example.gourmet

import android.app.Application
import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class App: Application() {
    lateinit var retrofit: Retrofit
    private val json = Json { coerceInputValues = true }

    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("http://cobuy.sytes.net:9090")
            .build()
    }

    companion object {
        private lateinit var instance: App

        fun getRetrofit(): Retrofit {
            return instance.retrofit
        }

        fun getContext(): Context {
            return instance
        }
    }
}


