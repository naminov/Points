package com.example.points.di

import com.example.points.data.api.PointsApi
import com.example.points.data.repository.PointsRepositoryImpl
import com.example.points.domain.repository.PointsRepository
import dagger.Module
import dagger.Provides

@Module
class PointsRepositoryModule {
    @Provides
    fun providePointsRepository(
        pointsApi: PointsApi
    ): PointsRepository {
        return PointsRepositoryImpl(pointsApi)
    }
}