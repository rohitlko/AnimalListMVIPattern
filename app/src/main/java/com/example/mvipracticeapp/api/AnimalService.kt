package com.example.mvipracticeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimalService {

    const val BASE_URL = "https://raw.githubusercontent.com/CatalinStefan/animalApi/master/"

    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // val api: AnimalApi = retrofit.build().create(AnimalApi::class.java)
}
