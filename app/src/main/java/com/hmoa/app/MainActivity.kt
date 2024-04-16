package com.hmoa.app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feature_userinfo.navigateToBack
import com.hmoa.app.navigation.SetUpNavGraph
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.component.HomeTopBar
import com.hmoa.core_designsystem.component.MainBottomBar
import com.hmoa.feature_brand.navigation.navigateToBrandSearch
import com.hmoa.feature_community.Navigation.CommunityRoute
import com.hmoa.feature_community.Navigation.navigateToCommunityRoute
import com.hmoa.feature_home.navigation.HomeRoute
import com.hmoa.feature_home.navigation.navigateToHome
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    private lateinit var initialRoute: String
    private val needBottomBarScreens = listOf(
        HomeRoute.Home.name,
        CommunityRoute.CommunityHomeRoute.name,
        CommunityRoute.CommunityPageRoute.name
    )

    private val needTopBarScreens = HomeRoute.Home.name

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        runBlocking { initialRoute = viewModel.routeInitialScreen() }

        setContent {
            val navHostController = rememberNavController()

            var currentScreen by remember{ mutableStateOf(BottomScreen.Home.name) }
            var isBottomBarVisible = true
            var isTopBarVisible = true


            val navBackStackEntry = navHostController.currentBackStackEntryAsState()
            navBackStackEntry.value?.destination?.route?.let { route ->
                currentScreen = route
                isBottomBarVisible = route in needBottomBarScreens
                isTopBarVisible = route in needTopBarScreens
            }
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

            Scaffold(
                backgroundColor = Color.White,
                bottomBar = {
                    if (isBottomBarVisible) {
                        MainBottomBar(
                            initValue = currentScreen,
                            onClickHome = navHostController::navigateToHome,
                            onClickHPedia = navHostController::navigateToCommunityRoute,
                            onClickLike = { /*TODO*/ },
                            onClickMyPage = {}
                        )
                    }
                },
                scaffoldState = scaffoldState,
                topBar = {
                    if (isTopBarVisible) {
                        HomeTopBar(
                            title = "H M O A",
                            onDrawerClick = { navHostController.navigateToBrandSearch() },
                            onSearchClick = {},
                            onNotificationClick = {},
                            drawerIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_drawer),
                            searchIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_search),
                            notificationIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_bell)
                        )
                    }
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