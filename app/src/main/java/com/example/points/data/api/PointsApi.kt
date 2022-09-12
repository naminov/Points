package com.example.points.data.api

import com.example.points.data.api.dto.PointsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApi {
    @GET("points/")
    suspend fun getPoints(
        @Query("count") count: Int
    ): PointsDto
}