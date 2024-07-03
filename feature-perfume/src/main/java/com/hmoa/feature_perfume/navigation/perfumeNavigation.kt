package com.hmoa.feature_perfume.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.hmoa.feature_perfume.screen.CreateNewPerfumeCommentRoute
import com.hmoa.feature_perfume.screen.EditMyPerfumeCommentRoute
import com.hmoa.feature_perfume.screen.PerfumeCommentRoute
import com.hmoa.feature_perfume.screen.PerfumeRoute
import com.hmoa.feature_perfume.screen.SpecificCommentRoute

fun NavController.navigateToPerfume(perfumeId: Int) = navigate("${PerfumeRoute.Perfume.name}/${perfumeId}")
fun NavController.navigateToPerfumeComment(perfumeId: Int) =
    navigate("${PerfumeRoute.PerfumeComment.name}/${perfumeId}")

fun NavController.navigateToSpecificPerfumeComment(commentId: Int, isEditable: Boolean) {
    when (isEditable) {
        true -> navigate("${PerfumeRoute.EditMyPerfumeComment.name}/${commentId}")
        false -> navigate("${PerfumeRoute.SpecificPerfumeComment.name}/${commentId}")
    }
}

fun NavController.navigateToCreateNewperfumeComment(perfumeId: Int) =
    navigate("${PerfumeRoute.CreateNewPerfumeComment.name}/$perfumeId")

fun NavGraphBuilder.perfumeScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCommentAddClick: (perfumeId: Int) -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onViewCommentAllClick: (perfumeId: Int) -> Unit,
    onSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
    onErrorHandleLoginAgain: () -> Unit
) {
    composable(
        route = "${PerfumeRoute.Perfume.name}/{perfumeId}",
        arguments = listOf(navArgument("perfumeId") { type = NavType.StringType }),
    ) {
        val perfumeId = it.arguments?.getString("perfumeId")
        PerfumeRoute(
            onBrandClick = { brandId -> onBrandClick(brandId) },
            onBackClick = { onBackClick() },
            onHomeClick = { onHomeClick() },
            onCommentAddClick = { onCommentAddClick(it) },
            onViewCommentAllClick = { onViewCommentAllClick(it) },
            onSimilarPerfumeClick = { onSimilarPerfumeClick(it) },
            perfumeId = perfumeId,
            onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) },
            onErrorHandleLoginAgain = {onErrorHandleLoginAgain()}
        )
    }
}

fun NavGraphBuilder.perfumeComment(
    onBackClick: () -> Unit,
    onAddCommentClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit
) {
    composable(
        route = "${PerfumeRoute.PerfumeComment.name}/{perfumeId}",
        arguments = listOf(navArgument("perfumeId") { type = NavType.IntType }),
        deepLinks = listOf(navDeepLink { uriPattern="hmoa://perfume_comment/{perfumeId}" })
    ) {
        val perfumeId = it.arguments?.getInt("perfumeId")
        PerfumeCommentRoute(
            onBackClick = { onBackClick() },
            onAddCommentClick = { onAddCommentClick(it) },
            perfumeId = perfumeId,
            onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) }
        )
    }
}

fun NavGraphBuilder.specificComment(
    onBackClick: () -> Unit
) {
    composable(
        route = "${PerfumeRoute.SpecificPerfumeComment.name}/{commentId}",
        arguments = listOf(navArgument("commentId") { type = NavType.IntType })
    ) {
        val commentId = it.arguments?.getInt("commentId")
        SpecificCommentRoute(onBackClick = { onBackClick() }, commentId = commentId)
    }
}

fun NavGraphBuilder.editMyPerfumeComment(onBackClick: () -> Unit) {
    composable(
        route = "${PerfumeRoute.EditMyPerfumeComment.name}/{commentId}",
        arguments = listOf(navArgument("commentId") { type = NavType.IntType })
    ) {
        val commentId = it.arguments?.getInt("commentId")
        EditMyPerfumeCommentRoute(onBackClick = { onBackClick() }, commentId = commentId)
    }
}

fun NavGraphBuilder.createNewPerfumeComment(onBackClick: () -> Unit) {
    composable(
        route = "${PerfumeRoute.CreateNewPerfumeComment.name}/{perfumeId}",
        arguments = listOf(navArgument("perfumeId") { type = NavType.IntType })
    ) {
        val perfumeId = it.arguments?.getInt("perfumeId")
        CreateNewPerfumeCommentRoute(onBackClick = { onBackClick() }, perfumeId = perfumeId)
    }
}