package com.example.points.data.api.dto

import com.google.gson.annotations.SerializedName

data class PointsDto(
    @field:SerializedName("points")
    val points: List<PointDto>
)
