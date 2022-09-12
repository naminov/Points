package com.example.points.presentation.pointsShowing

import com.example.points.domain.model.Point

data class UiState(
    val points: List<Point> = emptyList()
)