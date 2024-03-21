package com.hmoa.feature_perfume.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hmoa.feature_perfume.PerfumeCommentScreen
import com.hmoa.feature_perfume.PerfumeRoute

const val PERFUME_ROUTE = "perfume_route"
const val PERFUME_COMMENT_ROUTE = "perfume_comment"
fun NavController.navigateToPerfume(perfumeId: Int) = navigate("${PERFUME_ROUTE}/$perfumeId")
fun NavController.navigateToPerfumeComment(perfumeId: Int) = navigate("${PERFUME_COMMENT_ROUTE}/$perfumeId")

fun NavGraphBuilder.perfumeScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCommentAddClick: () -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onViewCommentAllClick: (perfumeId: Int) -> Unit,
    onSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit
) {
    composable(route = PERFUME_ROUTE, arguments = listOf(navArgument("perfumeId") { type = NavType.IntType })) {
        val perfumeId = it.arguments?.getInt("perfumeId")
        PerfumeRoute(
            onBrandClick = { brandId -> onBrandClick(brandId) },
            onBackClick = { onBackClick() },
            onHomeClick = { onHomeClick() },
            onCommentAddClick = { onCommentAddClick() },
            onViewCommentAllClick = { perfumeId -> onViewCommentAllClick(perfumeId) },
            onSimilarPerfumeClick = { perfumeId -> onSimilarPerfumeClick(perfumeId) },
            perfumeId = perfumeId,
            onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) }
        )
    }
}

fun NavGraphBuilder.perfumeComment(
    onBackClick: () -> Unit,
    onAddCommentClick: (perfumeId: Int?) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit
) {
    composable(route = PERFUME_COMMENT_ROUTE, arguments = listOf(navArgument("perfumeId") { type = NavType.IntType })) {
        val perfumeId = it.arguments?.getInt("perfumeId")
        PerfumeCommentScreen(
            onBackClick = { onBackClick() },
            onAddCommentClick = { onAddCommentClick(perfumeId) },
            perfumeId = perfumeId,
            onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) }
        )
    }
}