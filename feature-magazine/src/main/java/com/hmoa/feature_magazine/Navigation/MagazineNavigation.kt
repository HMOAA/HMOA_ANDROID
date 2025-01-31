package com.hmoa.feature_magazine.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.hmoa.core_domain.entity.navigation.CommunityRoute
import com.hmoa.core_domain.entity.navigation.MagazineRoute
import com.hmoa.feature_magazine.Screen.MagazineDescRoute
import com.hmoa.feature_magazine.Screen.MagazineMainRoute

fun NavHostController.navigateToMagazineHome() = navigate(MagazineRoute.Magazine.name){
    launchSingleTop = true
}
fun NavHostController.navigateToMagazineDesc(magazineId : Int) = navigate("${MagazineRoute.MagazineDescRoute.name}/${magazineId}")
fun NavGraphBuilder.magazineMain(
    navHome : () -> Unit,
    navPerfumeDesc : (perfumeId: Int) -> Unit,
    navCommunityDesc : (befRoute: CommunityRoute, communityId: Int) -> Unit,
    navMagazineDesc : (magazineId: Int) -> Unit,
){
    composable(MagazineRoute.Magazine.name){
        MagazineMainRoute(
            navHome = navHome,
            navPerfumeDesc = navPerfumeDesc,
            navCommunityDesc = navCommunityDesc,
            navMagazineDesc = navMagazineDesc
        )
    }
}

fun NavGraphBuilder.magazineDesc(
    navBack : () -> Unit,
    navLogin : () -> Unit,
    navDesc : (Int) -> Unit,
){
    composable("${MagazineRoute.MagazineDescRoute.name}/{magazineId}"){
        val magazineId = it.arguments?.getString("magazineId")?.toInt()
        MagazineDescRoute(
            id = magazineId,
            navBack = navBack,
            navLogin = navLogin,
            navDesc = navDesc
        )
    }
}