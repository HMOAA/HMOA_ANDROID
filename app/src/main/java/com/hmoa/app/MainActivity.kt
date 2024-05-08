package com.hmoa.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feature_userinfo.UserInfoGraph
import com.example.feature_userinfo.navigateToUserInfoGraph
import com.google.firebase.messaging.FirebaseMessaging
import com.hmoa.app.navigation.SetUpNavGraph
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.component.HomeTopBar
import com.hmoa.core_designsystem.component.MainBottomBar
import com.hmoa.feature_authentication.navigation.AuthenticationRoute
import com.hmoa.feature_brand.navigation.navigateToBrandSearch
import com.hmoa.feature_community.Navigation.CommunityRoute
import com.hmoa.feature_home.navigation.HomeRoute
import com.hmoa.feature_home.navigation.navigateToHome
import com.hmoa.feature_home.navigation.navigateToPerfumeSearch
import com.hmoa.feature_hpedia.Navigation.HPediaRoute
import com.hmoa.feature_hpedia.Navigation.navigateToHPedia
import com.hmoa.feature_like.Screen.LIKE_ROUTE
import com.hmoa.feature_like.Screen.navigateToLike
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    private lateinit var initialRoute: String
    private val PERMISSION_REQUEST_CODE = 1001
    private val needBottomBarScreens = listOf(
        HomeRoute.Home.name,
        CommunityRoute.CommunityHomeRoute.name,
        CommunityRoute.CommunityPageRoute.name,
        HPediaRoute.HPedia.name,
        "${HPediaRoute.HPediaSearchRoute.name}/{type}",
        "${HPediaRoute.HPediaDescRoute.name}/{id}/{type}",
        UserInfoGraph.MyPage.name,
        UserInfoGraph.MyInfoRoute.name,
        UserInfoGraph.MyFavoriteCommentRoute.name,
        UserInfoGraph.MyActivityRoute.name,
        UserInfoGraph.MyCommentRoute.name,
        UserInfoGraph.MyPostRoute.name,
        LIKE_ROUTE
    )
    private val bottomNav = listOf(
        BottomScreen.Home.name,
        BottomScreen.HPedia.name,
        BottomScreen.Like.name,
        BottomScreen.MyPage.name
    )

    private val needTopBarScreens = HomeRoute.Home.name

    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("TAG TEST", "${Utility.getKeyHash(this)}") 카카오 디벨로퍼에 key 등록

        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestNotificationPermission()
        lifecycleScope.launch {
            val authTokenState = viewModel.authToken().stateIn(this)
            val rememberedTokenState = viewModel.rememberedToken().stateIn(this)
            val newFlow = authTokenState.zip(rememberedTokenState) { authtoken, rememberedToken ->
                Pair<String?, String?>(authtoken, rememberedToken)
            }
            launch {
                newFlow.collectLatest {
                    if (it.first == null && it.second == null) {
                        initialRoute = AuthenticationRoute.Login.name
                    } else {
                        checkFcmToken()
                        initialRoute = HomeRoute.Home.name
                    }
                }
            }
        }
        setContent {
            val navHostController = rememberNavController()
            var currentScreen by remember { mutableStateOf(BottomScreen.Home.name) }
            var isBottomBarVisible = true
            var isTopBarVisible = true


            val navBackStackEntry = navHostController.currentBackStackEntryAsState()
            navBackStackEntry.value?.destination?.route?.let { route ->
                if (route in bottomNav) {
                    currentScreen = route
                }

                isBottomBarVisible = route in needBottomBarScreens
                isTopBarVisible = route in needTopBarScreens
            }
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))


            Scaffold(
                modifier = Modifier.systemBarsPadding(),
                backgroundColor = Color.White,
                bottomBar = {
                    if (isBottomBarVisible) {
                        MainBottomBar(
                            initValue = currentScreen,
                            onClickHome = navHostController::navigateToHome,
                            onClickHPedia = navHostController::navigateToHPedia,
                            onClickLike = navHostController::navigateToLike,
                            onClickMyPage = navHostController::navigateToUserInfoGraph
                        )
                    }
                },
                scaffoldState = scaffoldState,
                topBar = {
                    if (isTopBarVisible) {
                        HomeTopBar(
                            title = "H M O A",
                            onDrawerClick = { navHostController.navigateToBrandSearch() },
                            onSearchClick = { navHostController.navigateToPerfumeSearch() },
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

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun checkFcmToken(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            CoroutineScope(Dispatchers.IO).launch{
                val fcmToken = viewModel.getFcmToken().stateIn(this)
                if (fcmToken.value == null) {
                    FirebaseMessaging.getInstance().token.addOnSuccessListener {
                        viewModel.saveFcmToken(it)
                        viewModel.postFcmToken(it)
                    }.addOnFailureListener{
                        Log.e("Firebase Token", "Fail to save fcm token")
                    }
                } else {
                    viewModel.postFcmToken(fcmToken.value!!)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /** 권한 부여되면 처리할 작업? */
            } else {
                /** 권한 거부 시 사용자에게 설명 혹은 재요청 가능 */
            }
        }
    }
}