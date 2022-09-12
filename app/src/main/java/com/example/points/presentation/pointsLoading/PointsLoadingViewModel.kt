package com.example.points.presentation.pointsLoading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.points.R
import com.example.points.domain.usecase.GetPointsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.Exception

class PointsLoadingViewModel(
    private val getPointsUseCase: GetPointsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _action = MutableSharedFlow<UiAction>()
    val action: SharedFlow<UiAction> = _action.asSharedFlow()

    val event = MutableSharedFlow<UiEvent>()

    init {
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }
    }

    private fun handleEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.OnCountChange -> handleEventOnCountChange(uiEvent)
            is UiEvent.OnLoadClick -> handleEventOnLoadClick()
        }
    }

    private fun handleEventOnCountChange(uiEvent: UiEvent.OnCountChange) {
        viewModelScope.launch {
            if (uiEvent.count == _state.value.count) {
                return@launch
            }
            _state.value = _state.value.copy(
                count = uiEvent.count,
                loadingAvailable = uiEvent.count.isNotEmpty()
            )
        }
    }

    private fun handleEventOnLoadClick() {
        viewModelScope.launch {
            _action.emit(UiAction.HideKeyboard)

            _state.value = _state.value.copy(loading = true)

            try {
                val count = Integer.valueOf(_state.value.count)
                val points = getPointsUseCase(count)
                _action.emit(UiAction.NavigateToShowing(points))
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            }

            _state.value = _state.value.copy(loading = false)
        }
    }
}