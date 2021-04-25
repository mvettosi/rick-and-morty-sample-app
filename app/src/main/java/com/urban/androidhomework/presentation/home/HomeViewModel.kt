package com.urban.androidhomework.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.urban.androidhomework.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        characterRepository: CharacterRepository
) : ViewModel() {
    val characters =
            Pager(PagingConfig(pageSize = 20)) { characterRepository }
            .flow
            .cachedIn(viewModelScope)
}