package com.example.points.di

import com.example.points.presentation.pointsLoading.PointsLoadingFragment
import dagger.Component

@Component(
    modules = [
        PointsApiModule::class,
        PointsRepositoryModule::class,
        GetPointsUseCaseModule::class,
    ]
)
interface AppComponent {
    fun inject(pointsLoadingFragment: PointsLoadingFragment)
}