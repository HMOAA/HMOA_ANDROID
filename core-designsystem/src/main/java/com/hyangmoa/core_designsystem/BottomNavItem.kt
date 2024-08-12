package com.hyangmoa.core_designsystem

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val name: BottomScreen,
    val route: () -> Unit,
    val icon: Painter
)