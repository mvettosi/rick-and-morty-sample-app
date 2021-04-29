package com.urban.androidhomework.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.urban.androidhomework.data.character.CharacterRepository
import com.urban.androidhomework.presentation.general.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

/**
 * View Model of the application's Home Fragment.
 * It internally stores a long-lasting flow instance of the character list query, caching it in
 * memory to allow re-use of already downloaded items, and exposes the ability to perform a query on
 * them.
 */
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
            query == null || query.toLocalDate() == it.created?.toLocalDate()
        }
    }
}