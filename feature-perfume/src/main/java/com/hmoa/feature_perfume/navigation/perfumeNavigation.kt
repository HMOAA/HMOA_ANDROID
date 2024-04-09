package com.hmoa.feature_perfume.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hmoa.feature_perfume.screen.EditMyPerfumeCommentRoute
import com.hmoa.feature_perfume.screen.PerfumeCommentScreen
import com.hmoa.feature_perfume.screen.PerfumeRoute
import com.hmoa.feature_perfume.screen.SpecificCommentRoute

const val PERFUME_ROUTE = "perfume_route"
const val PERFUME_COMMENT_ROUTE = "perfume_comment_route"
const val SPECIFIC_PERFUME_COMMENT_ROUTE = "speficific_perfume_comment_route"
const val EDIT_MY_PERFUME_COMMENT_ROUTE = "edit_my_perfume_comment_route"
fun NavController.navigateToPerfume(perfumeId: Int) = navigate("${PERFUME_ROUTE}/$perfumeId")
fun NavController.navigateToPerfumeComment(perfumeId: Int) = navigate("${PERFUME_COMMENT_ROUTE}/$perfumeId")
fun NavController.navigateToSpecificPerfumeComment(commentId: Int, isEditable: Boolean) {
    when (isEditable) {
        true -> navigate("${EDIT_MY_PERFUME_COMMENT_ROUTE}/${commentId}")
        false -> navigate("${SPECIFIC_PERFUME_COMMENT_ROUTE}/${commentId}")
    }
}

fun NavGraphBuilder.perfumeScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCommentAddClick: () -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onViewCommentAllClick: (perfumeId: Int) -> Unit,
    onSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
) {
    composable(
        route = "${PERFUME_ROUTE}/{perfumeId}",
        arguments = listOf(navArgument("perfumeId") { type = NavType.IntType })
    ) {
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
    composable(
        route = "${PERFUME_COMMENT_ROUTE}/{perfumeId}",
        arguments = listOf(navArgument("perfumeId") { type = NavType.IntType })
    ) {
        val perfumeId = it.arguments?.getInt("perfumeId")
        PerfumeCommentScreen(
            onBackClick = { onBackClick() },
            onAddCommentClick = { onAddCommentClick(perfumeId) },
            perfumeId = perfumeId,
            onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) }
        )
    }
}

fun NavGraphBuilder.specificComment(
    onBackClick: () -> Unit
) {
    composable(
        route = "${SPECIFIC_PERFUME_COMMENT_ROUTE}/{commentId}",
        arguments = listOf(navArgument("commentId") { type = NavType.IntType })
    ) {
        val commentId = it.arguments?.getInt("commentId")
        SpecificCommentRoute(onBackClick = { onBackClick() }, commentId = commentId)
    }
}

fun NavGraphBuilder.editMyPerfumeComment(onBackClick: () -> Unit) {
    composable(
        route = "${EDIT_MY_PERFUME_COMMENT_ROUTE}/{commentId}",
        arguments = listOf(navArgument("commentId") { type = NavType.IntType })
    ) {
        val commentId = it.arguments?.getInt("commentId")
        EditMyPerfumeCommentRoute(onBackClick = { onBackClick() }, commentId = commentId)
    }
}