package com.example.points.presentation.pointsLoading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.points.domain.usecase.GetPointsUseCase
import javax.inject.Inject

class PointsLoadingViewModelFactory @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PointsLoadingViewModel(getPointsUseCase) as T
    }
}