package com.example.points.data.api.dto

import com.google.gson.annotations.SerializedName

data class PointDto(
    @field:SerializedName("x")
    val x: Double,
    @field:SerializedName("y")
    val y: Double
)
