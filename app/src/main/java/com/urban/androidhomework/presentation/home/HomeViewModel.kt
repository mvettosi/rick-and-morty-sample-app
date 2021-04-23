package com.urban.androidhomework.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.urban.androidhomework.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {
    val characters = getAllCharactersUseCase().asLiveData()
}