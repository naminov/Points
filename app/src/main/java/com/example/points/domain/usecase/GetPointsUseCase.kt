package com.example.points.domain.usecase

import com.example.points.domain.model.Point

interface GetPointsUseCase {
    suspend operator fun invoke(count: Int): List<Point>
}