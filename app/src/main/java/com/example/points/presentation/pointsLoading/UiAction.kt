package com.example.points.presentation.pointsLoading

import androidx.annotation.StringRes
import com.example.points.domain.model.Point

sealed class UiAction {
    class NavigateToShowing(
        val points: List<Point>
    ) : UiAction()

    class ShowMessage(
        @StringRes val messageId: Int
    ) : UiAction()

    object HideKeyboard: UiAction()
}