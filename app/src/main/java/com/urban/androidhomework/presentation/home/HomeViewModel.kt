package com.urban.androidhomework.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urban.androidhomework.domain.Character
import com.urban.androidhomework.usecase.GetAllCharactersUseCase
import com.urban.androidhomework.usecase.ResourceStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {
    private val _characters = MutableLiveData<ResourceStatus<Character>>()
    val characters: LiveData<ResourceStatus<Character>> = _characters

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            getAllCharactersUseCase().collect { _characters.value = it }
        }
    }
}