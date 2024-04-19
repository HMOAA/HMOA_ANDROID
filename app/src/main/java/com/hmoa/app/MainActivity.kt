package com.hmoa.app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feature_userinfo.navigateToBack
import com.hmoa.app.navigation.SetUpNavGraph
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.component.HomeTopBar
import com.hmoa.core_designsystem.component.MainBottomBar
import com.hmoa.feature_authentication.navigation.AuthenticationRoute
import com.hmoa.feature_brand.navigation.navigateToBrandSearch
import com.hmoa.feature_brand.screen.BrandSearchRoute
import com.hmoa.feature_community.Navigation.CommunityRoute
import com.hmoa.feature_home.navigation.HomeRoute
import com.hmoa.feature_home.navigation.navigateToHome
import com.hmoa.feature_hpedia.Navigation.HPediaRoute
import com.hmoa.feature_hpedia.Navigation.navigateToHPedia
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    private lateinit var initialRoute: String
    private val needBottomBarScreens = listOf(
        HomeRoute.Home.name,
        CommunityRoute.CommunityHomeRoute.name,
        CommunityRoute.CommunityPageRoute.name,
        HPediaRoute.HPedia.name,
        "${HPediaRoute.HPediaSearchRoute.name}/{type}",
        "${HPediaRoute.HPediaDescRoute.name}/{id}/{type}"
    )
    private val bottomNav = listOf(
        BottomScreen.Home.name,
        BottomScreen.HPedia.name,
        BottomScreen.Like.name,
        BottomScreen.MyPage.name
    )

    private val needTopBarScreens = HomeRoute.Home.name

    fun createRoute() {
        if (viewModel.authTokenState.value == null && viewModel.rememberedTokenState.value == null) {
            initialRoute = AuthenticationRoute.Login.name
        } else {
            initialRoute = HomeRoute.Home.name
        }
        Log.d(
            "MainActivity",
            "authTokenState: ${viewModel.authTokenState.value}\n, rememberedTokenState: ${viewModel.rememberedTokenState.value}"
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        createRoute()

        setContent {
            val navHostController = rememberNavController()
            var currentScreen by remember { mutableStateOf(BottomScreen.Home.name) }
            var isBottomBarVisible = true
            var isTopBarVisible = true
            var isDrawerGestureEnabled = false
            fun customShape() = object : Shape {
                override fun createOutline(
                    size: Size,
                    layoutDirection: LayoutDirection,
                    density: Density
                ): Outline {
                    return Outline.Rectangle(Rect(0f, 0f, 100f /* width */, 131f /* height */))
                }
            }


            val navBackStackEntry = navHostController.currentBackStackEntryAsState()
            navBackStackEntry.value?.destination?.route?.let { route ->
                if (route in bottomNav) {
                    currentScreen = route
                }

                isBottomBarVisible = route in needBottomBarScreens
                isTopBarVisible = route in needTopBarScreens
                isDrawerGestureEnabled = if (isTopBarVisible) true else false
            }
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))


            Scaffold(
                backgroundColor = Color.White,
                bottomBar = {
                    if (isBottomBarVisible) {
                        MainBottomBar(
                            initValue = currentScreen,
                            onClickHome = navHostController::navigateToHome,
                            onClickHPedia = navHostController::navigateToHPedia,
                            onClickLike = { /*TODO*/ },
                            onClickMyPage = {}
                        )
                    }
                },
                drawerContent = {
                    Column(modifier = Modifier.fillMaxWidth().fillMaxSize()) {
                        BrandSearchRoute(
                            onBrandClick = { navHostController.navigateToBrandSearch() },
                            onBackClick = navHostController::navigateToBack
                        )
                    }
                },
                drawerGesturesEnabled = isDrawerGestureEnabled,
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