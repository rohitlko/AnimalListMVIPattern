package com.example.mvipracticeapp.repo

import com.example.mvipracticeapp.api.AnimalApi

class AnimalRepository(private val api: AnimalApi) {

    suspend fun getAnimalList() = api.getAnimalList()
}