package com.hmoa.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.component.MainBottomBar
import com.hmoa.core_designsystem.component.MainTopBar
import com.hmoa.feature_home.HomeRoute
import com.hmoa.feature_perfume.navigation.navigateToPerfume

@Composable
fun MainRoute(
    onNavCommunity: () -> Unit,
    navController: NavHostController
) {
    MainScreen(
        onNavCommunity = onNavCommunity,
        navController = navController
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    onNavCommunity: () -> Unit,
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        backgroundColor = Color.White,
        scaffoldState = scaffoldState,
        topBar = { MainTopBar({}, {}, {}, true) },
        drawerContent = { Text(text = "drawerContent") },
        bottomBar = {
            MainBottomBar(BottomScreen.Home, {
                onNavCommunity()
            }, {}, {}, {})
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth().padding(bottom = it.calculateBottomPadding())
        ) {
            HomeRoute({ navController.navigateToPerfume(it) }, {})
        }
    }
}

@Preview
@Composable
fun ScaffoldDemoPreview() {
    val navHostController = rememberNavController()
    MainScreen({}, navHostController)
}