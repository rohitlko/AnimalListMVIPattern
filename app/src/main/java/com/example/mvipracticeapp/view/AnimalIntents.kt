package com.example.mvipracticeapp.view

sealed class AnimalIntents {
    data object FetchAnimals : AnimalIntents()
}

//sealed interface AnimalIntents {
//    data object FetchAnimals : AnimalIntents
//}