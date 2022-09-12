package com.example.points.di

import com.example.points.domain.repository.PointsRepository
import com.example.points.domain.usecase.GetPointsUseCase
import com.example.points.domain.usecase.GetPointsUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetPointsUseCaseModule {
    @Provides
    fun provideGetPointsUseCase(
        pointsRepository: PointsRepository
    ): GetPointsUseCase {
        return GetPointsUseCaseImpl(pointsRepository)
    }
}