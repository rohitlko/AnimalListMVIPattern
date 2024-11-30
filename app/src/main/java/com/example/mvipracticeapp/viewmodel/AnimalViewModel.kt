package com.example.mvipracticeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvipracticeapp.repo.AnimalRepository
import com.example.mvipracticeapp.view.AnimalIntents
import com.example.mvipracticeapp.view.AnimalStates
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AnimalViewModel(val repository: AnimalRepository): ViewModel() {

    val userIntent = Channel<AnimalIntents>(
       // capacity = Channel.UNLIMITED)
        capacity = 2)

    private val _uiState = MutableStateFlow<AnimalStates>(AnimalStates.Idle)
    val uiState: StateFlow<AnimalStates> = _uiState.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intents ->
                when (intents) {
                    is AnimalIntents.FetchAnimals -> getAnimalList()
                }
            }
        }
    }


    private fun getAnimalList() {
        viewModelScope.launch {
            _uiState.value = AnimalStates.Loading
            try {
                repository.getAnimalList()
                _uiState.value = AnimalStates.ShowAnimalState(repository.getAnimalList())
            } catch (e: Exception) {
                _uiState.value = AnimalStates.Error(e.localizedMessage)
            }
        }
    }
}


