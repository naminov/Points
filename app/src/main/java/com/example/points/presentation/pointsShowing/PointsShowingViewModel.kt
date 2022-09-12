package com.example.points.presentation.pointsShowing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.points.R
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PointsShowingViewModel : ViewModel() {

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
            is UiEvent.OnLoadPoints -> handleEventOnLoadPoints(uiEvent)
            is UiEvent.OnSaveGraphClick -> handleEventOnSaveGraphClick()
            is UiEvent.OnSaveGraphPermissionCheck ->
                handleEventOnSaveGraphPermissionCheck(uiEvent)
            is UiEvent.OnSaveGraphPermissionRequest ->
                handleEventOnSaveGraphPermissionRequest(uiEvent)
        }
    }

    private fun handleEventOnLoadPoints(
        uiEvent: UiEvent.OnLoadPoints
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(points = uiEvent.points)
        }
    }

    private fun handleEventOnSaveGraphClick() {
        viewModelScope.launch {
            _action.emit(UiAction.SaveGraphPermissionCheck)
        }
    }

    private fun handleEventOnSaveGraphPermissionCheck(
        uiEvent: UiEvent.OnSaveGraphPermissionCheck
    ) {
        viewModelScope.launch {
            if (uiEvent.granted) {
                _action.emit(UiAction.SaveGraph(R.string.snapshot))
            } else {
                _action.emit(UiAction.SaveGraphPermissionRequest)
            }
        }
    }

    private fun handleEventOnSaveGraphPermissionRequest(
        uiEvent: UiEvent.OnSaveGraphPermissionRequest
    ) {
        viewModelScope.launch {
            if (!uiEvent.granted) return@launch
            _action.emit(UiAction.SaveGraph(R.string.snapshot))
        }
    }
}