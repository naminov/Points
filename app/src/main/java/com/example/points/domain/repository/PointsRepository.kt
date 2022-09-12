package com.example.points.domain.repository

import com.example.points.domain.model.Point

interface PointsRepository {
    suspend fun getPoints(count: Int): List<Point>
}