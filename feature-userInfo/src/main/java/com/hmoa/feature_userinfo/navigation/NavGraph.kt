package com.hmoa.feature_userinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.userinfo.MyPageRoute
import com.hmoa.core_domain.entity.navigation.CommunityRoute
import com.hmoa.core_domain.entity.navigation.HbtiRoute
import com.hmoa.core_domain.entity.navigation.UserInfoRoute
import com.hmoa.feature_userinfo.MyFavoriteCommentRoute
import com.hmoa.feature_userinfo.screen.*

//graph 이동
fun NavController.navigateToUserInfoGraph() = navigate(UserInfoRoute.UserInfoGraph.name)

//마이페이지로 이동
fun NavController.navigateToMyPage() = navigate(UserInfoRoute.MyPage.name)

//내 활동 페이지
fun NavController.navigateToMyActivity() = navigate(UserInfoRoute.MyActivityRoute.name)

//내 댓글 페이지
fun NavController.navigateToMyCommentPage() = navigate(UserInfoRoute.MyCommentRoute.name)

//좋아요한 댓글 페이지
fun NavController.navigateToMyFavoriteCommentPage() = navigate(UserInfoRoute.MyFavoriteCommentRoute.name)

//프로필 수정 화면
fun NavController.navigateToEditProfilePage() = navigate(UserInfoRoute.EditProfileRoute.name)

//내 정보 페이지
fun NavController.navigateToMyInfoPage() = navigate(UserInfoRoute.MyInfoRoute.name)

//내 성별 페이지
fun NavController.navigateToMyGenderPage() = navigate(UserInfoRoute.MyGenderRoute.name)

//내 출생연도 페이지
fun NavController.navigateToMyBirth() = navigate(UserInfoRoute.MyBirthRoute.name)

//내 게시글 페이지
fun NavController.navigateToMyPostPage() = navigate(UserInfoRoute.MyPostRoute.name)

//내가 좋아요 한 향수 페이지
fun NavController.navigateToMyFavoritePerfume() = navigate(UserInfoRoute.MyFavoritePerfumeRoute.name)

//환불 & 반품
fun NavController.navigateToRefund(type: String, orderId: Int) =
    navigate("${UserInfoRoute.RefundRoute.name}/${type}/${orderId}")

//주문 내역
fun NavController.navigateToOrderRecord(befRoute: UserInfoRoute) = navigate(UserInfoRoute.OrderRecordRoute.name) {
    if (befRoute == UserInfoRoute.RefundRoute) {
        popUpTo(route = "${UserInfoRoute.RefundRoute.name}/{type}/{orderId}") { inclusive = true }
    }
}

//환불 & 반품 내역
fun NavController.navigateToRefundRecord() = navigate(UserInfoRoute.RefundRecordRoute.name)

//뒤로가기
fun NavController.navigateToBack() = navigateUp()

//내가 작성한 리뷰로 이동
fun NavController.navigateToMyReview() = navigate("${UserInfoRoute.MyReview.name}")

fun NavGraphBuilder.nestedUserInfoGraph(
    appVersion: String,
    navHome: () -> Unit,
    navMyPerfume: () -> Unit,
    navLogin: () -> Unit,
    navReview: (befRoute: HbtiRoute) -> Unit,
    navBack: () -> Unit,
    navCommunity: (befRoute: CommunityRoute, communityId: Int) -> Unit,
    navEditPost: (Int) -> Unit,
    navEditProfile: () -> Unit,
    navManageMyInfo: () -> Unit,
    navMyActivity: () -> Unit,
    navMyFavoriteComment: () -> Unit,
    navMyPost: () -> Unit,
    navMyComment: () -> Unit,
    navMyReview: () -> Unit,
    navMyBirth: () -> Unit,
    navMyGender: () -> Unit,
    navPerfume: (Int) -> Unit,
    navOrderRecord: (befRoute: UserInfoRoute) -> Unit,
    navRefund: (pageType: String, orderId: Int) -> Unit,
    navReviewWrite: (orderId: Int) -> Unit,
    navRefundRecord: () -> Unit,
) {
    navigation(
        startDestination = UserInfoRoute.MyPage.name,
        route = UserInfoRoute.UserInfoGraph.name
    ) {
        composable(route = UserInfoRoute.MyPage.name) {
            MyPageRoute(
                navMyPerfume = navMyPerfume,
                navEditProfile = navEditProfile,
                navMyActivity = navMyActivity,
                navManageMyInfo = navManageMyInfo,
                navLogin = navLogin,
                navBack = navBack,
                navOrderRecord = navOrderRecord,
                navRefundRecord = navRefundRecord,
                appVersion = appVersion
            )
        }
        composable(route = UserInfoRoute.EditProfileRoute.name) {
            EditProfileRoute(navBack = navBack, navLogin = navLogin)
        }
        composable(route = UserInfoRoute.MyPostRoute.name) {
            MyPostRoute(
                navBack = navBack,
                navEditPost = navEditPost
            )
        }
        composable(route = UserInfoRoute.MyActivityRoute.name) {
            MyActivityRoute(
                navMyFavoriteComment = navMyFavoriteComment,
                navMyComment = navMyComment,
                navMyPost = navMyPost,
                navBack = navBack,
                navMyReview = navMyReview
            )
        }
        composable(route = UserInfoRoute.MyCommentRoute.name) {
            MyCommentRoute(
                navBack = navBack,
                navCommunity = navCommunity,
                navPerfume = navPerfume,
                navLogin = navLogin
            )
        }
        composable(route = UserInfoRoute.MyFavoriteCommentRoute.name) {
            MyFavoriteCommentRoute(
                navBack = navBack,
                navCommunity = navCommunity,
                navPerfume = navPerfume
            )
        }
        composable(route = UserInfoRoute.MyInfoRoute.name) {
            MyInfoRoute(
                navBack = navBack,
                navMyBirth = navMyBirth,
                navMyGender = navMyGender
            )
        }
        composable(route = UserInfoRoute.MyBirthRoute.name) {
            MyBirthRoute(navBack = navBack, navLogin = navLogin)
        }
        composable(route = UserInfoRoute.MyGenderRoute.name) {
            MyGenderRoute(navBack = navBack)
        }
        composable(route = UserInfoRoute.MyFavoritePerfumeRoute.name) {
            MyFavoritePerfumeRoute(
                navPerfume = navPerfume,
                navHome = navHome,
                navLogin = navLogin,
                navBack = navBack
            )
        }
        composable(route = UserInfoRoute.NoAuthMyPage.name) {
            NoAuthMyPage(navLogin = navLogin)
        }
        composable(route = UserInfoRoute.OrderRecordRoute.name) {
            OrderRecordRoute(
                navLogin = navLogin,
                navBack = navBack,
                navReturnOrRefund = navRefund,
                navReviewWrite = navReviewWrite
            )
        }
        composable(
            route = "${UserInfoRoute.RefundRoute.name}/{type}/{orderId}",
            arguments = listOf(
                navArgument("type") { type = NavType.StringType },
                navArgument("orderId") { type = NavType.IntType }
            )
        ) {
            val type = it.arguments?.getString("type")
            val orderId = it.arguments?.getInt("orderId")
            RefundRoute(
                type = type,
                orderId = orderId,
                navBack = navBack,
                navLogin = navLogin,
                navOrderRecord = navOrderRecord
            )
        }
        composable(route = "${UserInfoRoute.RefundRecordRoute.name}") {
            RefundRecordRoute(navBack = navBack, navLogin = navLogin)
        }
        composable(route = "${UserInfoRoute.MyReview.name}") {
            MyReviewRoute(navReview = navReview, navBack = navBack, navLogin = navLogin)
        }
    }
}