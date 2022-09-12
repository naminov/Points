package com.example.points.domain.usecase

import com.example.points.domain.model.Point
import com.example.points.domain.repository.PointsRepository

class GetPointsUseCaseImpl(
    private val pointsRepository: PointsRepository
) : GetPointsUseCase {
    override suspend fun invoke(count: Int): List<Point> {
        return pointsRepository.getPoints(count)
            .sortedBy { it.x }
    }
}