package com.hmoa.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import com.hmoa.feature_fcm.navigateToAlarmScreen
import com.hmoa.feature_home.navigation.HomeRoute
import com.hmoa.feature_home.navigation.navigateToHome
import com.hmoa.feature_home.navigation.navigateToPerfumeSearch
import com.hmoa.feature_hpedia.Navigation.HPediaRoute
import com.hmoa.feature_hpedia.Navigation.navigateToHPedia
import com.hmoa.feature_like.Screen.LIKE_ROUTE
import com.hmoa.feature_magazine.Navigation.MagazineRoute
import com.hmoa.feature_magazine.Navigation.navigateToMagazineHome
import com.hmoa.feature_perfume.navigation.PerfumeRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    private var initialRoute = AuthenticationRoute.Login.name
    private val PERMISSION_REQUEST_CODE = 1001
    private val needBottomBarScreens = listOf(
        HomeRoute.Home.name,
        CommunityRoute.CommunityHomeRoute.name,
        CommunityRoute.CommunityPreviewRoute.name,
        HPediaRoute.HPedia.name,
        "${HPediaRoute.HPediaSearchRoute.name}/{type}",
        "${HPediaRoute.HPediaDescRoute.name}/{id}/{type}",
        UserInfoGraph.MyPage.name,
        UserInfoGraph.MyInfoRoute.name,
        UserInfoGraph.MyFavoriteCommentRoute.name,
        UserInfoGraph.MyActivityRoute.name,
        UserInfoGraph.MyCommentRoute.name,
        UserInfoGraph.MyPostRoute.name,
        LIKE_ROUTE,
        MagazineRoute.Magazine.name
    )
    private val bottomNav = listOf(
        BottomScreen.Home.name,
        BottomScreen.HPedia.name,
        BottomScreen.Magazine.name,
        BottomScreen.MyPage.name
    )

    private val needTopBarScreens = HomeRoute.Home.name

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestNotificationPermission()

        lifecycleScope.launch {
            val currentJob = coroutineContext.job
            val authTokenState = viewModel.authToken().stateIn(this)
            val rememberedTokenState = viewModel.rememberedToken().stateIn(this)
            val newFlow = authTokenState.zip(rememberedTokenState) { authtoken, rememberedToken ->
                Pair<String?, String?>(authtoken, rememberedToken)
            }
            launch {
                newFlow.collectLatest { token ->
                    Log.d("LOGIN TOKEN","access : ${token.first} refresh : ${token.second}")
                    if (token.first == null && token.second == null) {
                        initialRoute = AuthenticationRoute.Login.name
                        currentJob.cancel()
                    } else {
                        initialRoute = HomeRoute.Home.name
                        currentJob.cancel()
                    }
                }
            }
            FirebaseMessaging.getInstance().token.addOnSuccessListener {
                Log.d("FCM TEST", "token : ${it}")
                CoroutineScope(Dispatchers.IO).launch{
                    if (viewModel.getFcmToken().stateIn(this).value != it){
                        checkFcmToken(authTokenState.value, rememberedTokenState.value, it)
                    }
                }
            }.addOnFailureListener{
                Log.e("FCM TEST", "Token Failure")
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
            val deeplink = remember{ handleDeeplink(intent) }

            Scaffold(
                modifier = Modifier.systemBarsPadding(),
                backgroundColor = Color.White,
                bottomBar = {
                    if (isBottomBarVisible) {
                        MainBottomBar(
                            initValue = currentScreen,
                            onClickHome = navHostController::navigateToHome,
                            onClickHPedia = navHostController::navigateToHPedia,
                            onClickLike = navHostController::navigateToMagazineHome,
                            onClickMyPage = navHostController::navigateToUserInfoGraph
                        )
                    }
                },
                scaffoldState = scaffoldState,
                topBar = {
                    if (isTopBarVisible) {
                        HomeTopBar(
                            onDrawerClick = { navHostController.navigateToBrandSearch() },
                            onSearchClick = { navHostController.navigateToPerfumeSearch() },
                            onNotificationClick = { navHostController.navigateToAlarmScreen() },
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
                    SetUpNavGraph(navHostController,initialRoute)
                    LaunchedEffect(Unit){
                        if (deeplink.first != null) {
                            navHostController.navigate(deeplink.first!!)
                            viewModel.checkAlarm(deeplink.second!!)
                        }
                    }
                }
            }
        }
    }

    //deeplink 처리 함수
    private fun handleDeeplink(intent : Intent?) : Pair<String?, Int?> {
        var deeplink: String? = intent?.getStringExtra("deeplink") ?: return Pair(null, null)
        val alarm_id = intent.extras?.getString("id")?.toInt() ?: return Pair(null, null)

        val uri = Uri.parse(deeplink)
        val host = uri.host
        val targetId = uri.lastPathSegment?.toInt()
        deeplink = when (host){
            "community" -> "${CommunityRoute.CommunityDescriptionRoute.name}/${targetId}"
            "perfume_comment" -> "${PerfumeRoute.PerfumeComment.name}/${targetId}"
            else -> null
        }
        return Pair(deeplink, alarm_id)
    }

    private fun checkFcmToken(
        authToken: String?,
        rememberToken: String?,
        fcmToken : String?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val isEnabled = viewModel.getNotificationEnabled().stateIn(this)
            // isEnabled 가 true 면
            if (isEnabled.value){
                if (fcmToken != null && authToken != null && rememberToken != null) {
                    viewModel.postFcmToken(fcmToken)
                } else {
                    return@launch
                }
            } else {
                viewModel.delFcmToken()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
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
            CoroutineScope(Dispatchers.IO).launch{
                viewModel.saveNotificationEnabled(false)
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch{
                viewModel.saveNotificationEnabled(true)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // android 13 미만인 버전에서는 notification 권한이 필요 없음
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && requestCode == PERMISSION_REQUEST_CODE){
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                // 권한 부여되면 그 때 fcm token을 서버에 보내도록 해야 할 듯?
                CoroutineScope(Dispatchers.IO).launch{
                    viewModel.saveNotificationEnabled(true)
                }
            } else {
                // 알림 권한 설정을 안할 시 알림을 받지 않도록 함
                Toast.makeText(this, "알림을 받고 싶다면 별도로 설정해주세요.", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch{
                    viewModel.saveNotificationEnabled(false)
                }
            }
        }
    }
}