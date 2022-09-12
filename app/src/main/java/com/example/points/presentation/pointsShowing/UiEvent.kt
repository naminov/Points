package com.example.points.presentation.pointsShowing

import com.example.points.domain.model.Point

sealed class UiEvent {
    class OnLoadPoints(
        val points: List<Point>
    ) : UiEvent()

    object OnSaveGraphClick : UiEvent()

    class OnSaveGraphPermissionCheck(
        val granted: Boolean
    ) : UiEvent()

    class OnSaveGraphPermissionRequest(
        val granted: Boolean
    ) : UiEvent()
}