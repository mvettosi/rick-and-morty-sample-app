package com.urban.androidhomework.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.urban.androidhomework.data.CharacterRepository
import com.urban.androidhomework.presentation.general.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val characterRepository: CharacterRepository
) : ViewModel() {
    private val characterFlow =
            Pager(PagingConfig(pageSize = 20)) { characterRepository.getPagingSource() }
                    .flow
                    .cachedIn(viewModelScope)

    fun getCharacterFlow(query: Date? = null) = characterFlow.map { pagingData ->
        pagingData.filter {
            query == null || query.toLocalDate() == it.created.toLocalDate()
        }
    }
}