package com.example.points.presentation.pointsLoading

sealed class UiEvent {
    class OnCountChange(
        val count: String
    ) : UiEvent()

    object OnLoadClick : UiEvent()
}