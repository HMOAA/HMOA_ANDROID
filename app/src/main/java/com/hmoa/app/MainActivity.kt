package com.hmoa.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hmoa.app.navigation.SetUpNavGraph
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.component.MainBottomBar
import com.hmoa.feature_community.Navigation.CommunityRoute
import com.hmoa.feature_community.Navigation.navigateToCommunityRoute
import com.hmoa.feature_home.navigation.HomeRoute
import com.hmoa.feature_home.navigation.navigateToHome
import com.hmoa.feature_hpedia.Navigation.HPediaRoute
import com.hmoa.feature_hpedia.Navigation.navigateToHPedia
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    private lateinit var initialRoute: String
    private val needBottomBarScreens = listOf(
        HomeRoute.Home.name,
        CommunityRoute.CommunityHomeRoute.name,
        CommunityRoute.CommunityPageRoute.name,
        HPediaRoute.HPeidaRoute.name,
        HPediaRoute.HPediaSearchRoute.name,
        HPediaRoute.HPeidaRoute.name,
        HPediaRoute.HPediaDescRoute.name
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        runBlocking { initialRoute = viewModel.routeInitialScreen() }

        setContent {
            val navHostController = rememberNavController()

            var isBottomBarVisible = true

            val navBackStackEntry = navHostController.currentBackStackEntryAsState()
            navBackStackEntry.value?.destination?.route?.let { route ->
                isBottomBarVisible = route in needBottomBarScreens
            }
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

            Scaffold(
                backgroundColor = Color.White,
                bottomBar = {
                    if (isBottomBarVisible) {
                        MainBottomBar(
                            initValue = BottomScreen.Home,
                            onClickHome = navHostController::navigateToHome,
                            onClickHPedia = navHostController::navigateToHPedia,
                            onClickLike = { /*TODO*/ },
                            onClickMyPage = {}
                        )
                    }
                },
                scaffoldState = scaffoldState,
                drawerContent = {
                    /** Drawer Content */
                },
                topBar = {
                    /** top bar도 비슷하게 처리할까요? */
                },
            ) {
                Box(
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                ) {
                    SetUpNavGraph(navHostController, initialRoute)
                }
            }
        }
    }
}