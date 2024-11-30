package com.example.mvipracticeapp.api

import com.example.mvipracticeapp.model.Animal
import retrofit2.http.GET

interface AnimalApi {

    @GET("animals.json")
    suspend fun getAnimalList(): List<Animal>
}