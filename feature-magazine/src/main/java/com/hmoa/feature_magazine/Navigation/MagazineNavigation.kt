package com.hmoa.feature_magazine.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.hmoa.core_domain.entity.navigation.MagazineRoute
import com.hmoa.feature_magazine.Screen.MagazineDescRoute
import com.hmoa.feature_magazine.Screen.MagazineMainRoute

fun NavHostController.navigateToMagazineHome() = navigate(MagazineRoute.Magazine.name){
    launchSingleTop = true
}
fun NavHostController.navigateToMagazineDesc(magazineId : Int) = navigate("${MagazineRoute.MagazineDescRoute.name}/${magazineId}")
fun NavGraphBuilder.magazineMain(
    onNavHome : () -> Unit,
    onNavPerfumeDesc : (Int) -> Unit,
    onNavCommunityDesc : (Int) -> Unit,
    onNavMagazineDesc : (Int) -> Unit,
){
    composable(MagazineRoute.Magazine.name){
        MagazineMainRoute(
            onNavHome = onNavHome,
            onNavPerfumeDesc = onNavPerfumeDesc,
            onNavCommunityDesc = onNavCommunityDesc,
            onNavMagazineDesc = onNavMagazineDesc
        )
    }
}

fun NavGraphBuilder.magazineDesc(
    onNavBack : () -> Unit,
    onNavLogin : () -> Unit,
    onNavDesc : (Int) -> Unit,
){
    composable("${MagazineRoute.MagazineDescRoute.name}/{magazineId}"){
        val magazineId = it.arguments?.getString("magazineId")?.toInt()
        MagazineDescRoute(
            id = magazineId,
            onNavBack = onNavBack,
            onNavLogin = onNavLogin,
            onNavDesc = onNavDesc
        )
    }
}