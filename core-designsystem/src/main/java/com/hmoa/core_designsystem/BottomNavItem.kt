package com.hmoa.core_designsystem

import android.media.Image
import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem (
    val name:String,
    val route:()->Unit,
    val icon: Painter
)