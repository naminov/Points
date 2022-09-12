package com.example.points.data.repository

import com.example.points.data.api.PointsApi
import com.example.points.domain.model.Point
import com.example.points.domain.repository.PointsRepository

class PointsRepositoryImpl(
    private val pointsApi: PointsApi
) : PointsRepository {
    override suspend fun getPoints(count: Int): List<Point> {
        return pointsApi.getPoints(count)
            .points
            .map { pointDto -> Point(pointDto.x, pointDto.y) }
    }
}