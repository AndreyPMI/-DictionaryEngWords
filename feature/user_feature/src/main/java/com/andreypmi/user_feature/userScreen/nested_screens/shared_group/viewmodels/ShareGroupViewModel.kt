package com.andreypmi.user_feature.userScreen.nested_screens.shared_group.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.usecase.categoryUseCases.GetCategoriesWithWordCountUseCase
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.models.ShareGroupState
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.models.toPresenterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShareGroupViewModel(
    private val  getCategoriesUseCase : GetCategoriesWithWordCountUseCase,
) : ViewModel() {
    private var currentPage = 0
    private val _state = MutableStateFlow(ShareGroupState())
    val state: StateFlow<ShareGroupState> = _state.asStateFlow()

    fun processIntent(intent: ShareGroupIntent) {
        when (intent) {
            ShareGroupIntent.LoadFirstPage -> loadFirstPage()
            ShareGroupIntent.LoadNextPage -> loadNextPage()
            ShareGroupIntent.ErrorShown -> onErrorShown()
        }
    }

    private fun loadFirstPage() {
        currentPage = 0
        loadPage(currentPage, isLoadingMore = false)
    }

    private fun loadNextPage() {
        if (_state.value.isLoadingMore || !_state.value.hasMore) return
        currentPage++
        loadPage(currentPage, isLoadingMore = true)
    }

    private fun loadPage(page: Int, isLoadingMore: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = !isLoadingMore,
                    isLoadingMore = isLoadingMore,
                    error = null
                )
            }

            try {
                val categories = getCategoriesUseCase.execute(page = page).map { it.toPresenterModel() }
                val hasMore = getCategoriesUseCase.hasMoreCategories(currentPage = page)

                _state.update {
                    it.copy(
                        categories = if (page == 0) categories else it.categories + categories,
                        isLoading = false,
                        isLoadingMore = false,
                        hasMore = hasMore
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = e.message ?: "Failed to load categories"
                    )
                }
            }
        }
    }
    private fun onErrorShown() {
        _state.update { it.copy(error = null) }
    }
}