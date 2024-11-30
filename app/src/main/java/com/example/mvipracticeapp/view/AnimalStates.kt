package com.example.mvipracticeapp.view

import com.example.mvipracticeapp.model.Animal

sealed class AnimalStates {
    object Idle : AnimalStates()
    object Loading : AnimalStates()
    data class ShowAnimalState(val animals: List<Animal>) : AnimalStates()
    data class Error(val error: String?) : AnimalStates()
}