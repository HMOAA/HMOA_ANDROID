package com.hmoa.feature_perfume.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.feature_perfume.PerfumeScreen

const val PERFUME_ROUTE = "perfume_route"

fun NavController.navigateToPerfume() = navigate(PERFUME_ROUTE)

fun NavGraphBuilder.perfumeScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCommentAddClick: () -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onViewCommentAllClick: (perfumeId: Int) -> Unit,
    onSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    perfumeId: Int,
) {
    composable(route = PERFUME_ROUTE) {
        PerfumeScreen(
            onBrandClick = { onBrandClick(it) },
            onBackClick = { onBackClick() },
            onHomeClick = { onHomeClick() },
            onCommentAddClick = { onCommentAddClick() },
            onViewCommentAllClick = { onViewCommentAllClick(it) },
            onSimilarPerfumeClick = { onSimilarPerfumeClick(it) },
            perfumeId = perfumeId
        )
    }
}