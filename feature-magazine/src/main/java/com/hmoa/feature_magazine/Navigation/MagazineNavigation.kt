package com.hmoa.feature_magazine.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.hmoa.feature_magazine.Screen.MagazineDescRoute
import com.hmoa.feature_magazine.Screen.MagazineMainRoute

fun NavHostController.navigateToMagazineHome() = navigate(MagazineRoute.Magazine.name)
fun NavHostController.navigateToMagazineDesc(magazineId : Int) = navigate("${MagazineRoute.MagazineDescRoute.name}/${magazineId}")
fun NavGraphBuilder.magazineMain(
    onNavHome : () -> Unit,
){
    composable(MagazineRoute.Magazine.name){
        MagazineMainRoute(onNavHome = onNavHome)
    }
}

fun NavGraphBuilder.magazineDesc(
    onNavBack : () -> Unit,
){
    composable("${MagazineRoute.MagazineDescRoute.name}/{magazineId}"){
        val magazineId = it.arguments?.getString("magazineId")?.toInt()
        MagazineDescRoute(
            id = magazineId,
            onNavBack = onNavBack
        )
    }
}