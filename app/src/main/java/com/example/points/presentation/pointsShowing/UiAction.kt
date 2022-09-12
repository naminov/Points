package com.example.points.presentation.pointsShowing

import androidx.annotation.StringRes

sealed class UiAction {
    class SaveGraph(
        @StringRes val name: Int
    ) : UiAction()
    object SaveGraphPermissionCheck: UiAction()
    object SaveGraphPermissionRequest: UiAction()
}