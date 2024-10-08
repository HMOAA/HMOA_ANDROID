package com.hmoa.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feature_userinfo.UserInfoGraph
import com.example.feature_userinfo.navigateToUserInfoGraph
import com.google.firebase.messaging.FirebaseMessaging
import com.hmoa.app.navigation.SetUpNavGraph
import com.hmoa.core_common.PERMISSION_REQUEST_CODE
import com.hmoa.core_common.permissions
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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    private var initialRoute = AuthenticationRoute.Login.name
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
        initFirebaseSetting()

        lifecycleScope.launch {
            val currentJob = coroutineContext.job
            val authTokenState = viewModel.authToken().stateIn(this)
            val rememberedTokenState = viewModel.rememberedToken().stateIn(this)
            val newFlow = authTokenState.zip(rememberedTokenState) { authtoken, rememberedToken ->
                Pair<String?, String?>(authtoken, rememberedToken)
            }
            launch {
                newFlow.collectLatest { token ->
                    Log.d("LOGIN TOKEN", "access : ${token.first} refresh : ${token.second}")
                    if (token.first == null && token.second == null) {
                        initialRoute = AuthenticationRoute.Login.name
                        currentJob.cancel()
                    } else {
                        initialRoute = HomeRoute.Home.name
                        currentJob.cancel()
                    }
                }
            }
            launch {
                viewModel.getNotificationEnabled().collectLatest {
                    Log.d("POST PERMISSION", "is granted : ${it}")
                }
            }
        }

        //fcm token post
        lifecycleScope.launch {
            val authToken = viewModel.authToken()
            val rememberToken = viewModel.rememberedToken()
            val fcmToken = viewModel.getFcmToken()
            combine(authToken, rememberToken, fcmToken) { authToken, rememberToken, fcmToken -> }.collectLatest {
                val auth = authToken.stateIn(this).value
                val remember = rememberToken.stateIn(this).value
                val fcm = fcmToken.stateIn(this).value
                if (auth != null && remember != null && fcm != null) {
                    withContext(Dispatchers.IO) { checkFcmToken(fcm) }
                    lifecycle.coroutineScope.cancel()
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
            val deeplink = remember { handleDeeplink(intent) }

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
                    SetUpNavGraph(
                        navController = navHostController,
                        startDestination = initialRoute,
                        appVersion = BuildConfig.VERSION_NAME
                    )
                    LaunchedEffect(Unit) {
                        if (deeplink.first != null) {
                            navHostController.navigate(deeplink.first!!)
                            viewModel.checkAlarm(deeplink.second!!)
                        }
                    }
                }
            }
        }
    }

    //firebase 초기 토큰 처리
    private fun initFirebaseSetting() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            CoroutineScope(Dispatchers.IO).launch {
                val fcmToken = viewModel.getFcmToken().stateIn(this).value
                Log.d("FCM TEST", "fcm token : ${fcmToken}")
                if (it != fcmToken) {
                    Log.d("FCM TEST", "firebase messaging fcm token : ${it}")
                    viewModel.saveFcmToken(it)
                }
            }
        }.addOnFailureListener {
            Log.e("FCM TEST", "${it.message} \n ${it.stackTrace}")
        }
    }

    //deeplink 처리 함수
    private fun handleDeeplink(intent: Intent?): Pair<String?, Int?> {
        var deeplink: String? = intent?.getStringExtra("deeplink") ?: return Pair(null, null)
        val alarm_id = intent.extras?.getString("id")?.toInt() ?: return Pair(null, null)

        val uri = Uri.parse(deeplink)
        val host = uri.host
        val targetId = uri.lastPathSegment?.toInt()
        deeplink = when (host) {
            "community" -> "${CommunityRoute.CommunityDescriptionRoute.name}/${targetId}"
            "perfume_comment" -> "${PerfumeRoute.PerfumeComment.name}/${targetId}"
            else -> null
        }
        Log.d("FCM TEST", "deeplink : ${deeplink} / alarm id : ${alarm_id}")
        return Pair(deeplink, alarm_id)
    }

    private suspend fun checkFcmToken(
        fcmToken: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val isEnabled = viewModel.getNotificationEnabled().stateIn(this)
            // isEnabled 가 true 면
            if (isEnabled.value) {
                Log.d("FCM TEST", "post fcm token")
                viewModel.postFcmToken(fcmToken)
            } else {
                Log.d("FCM TEST", "delete fcm token")
                viewModel.delFcmToken()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val deniedPermissions = permissions.filter { !com.hmoa.core_common.checkPermission(this, it) }
            Log.d("PERMISSION TEST", "permissions : ${permissions}")
            Log.d("PERMISSION TEST", "denied : ${deniedPermissions}")
            if (deniedPermissions.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, deniedPermissions.toTypedArray(), PERMISSION_REQUEST_CODE)
            }
        } else {
            //13 버전 미만 카메라 권한 (READ_EXTERNAL_STORAGE) 요청
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    PERMISSION_REQUEST_CODE
                )
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && requestCode == PERMISSION_REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                /** 알림 권한 유 */
                lifecycleScope.launch { viewModel.saveNotificationEnabled(true) }
            } else {
                /** 알림 권한 무 */
                lifecycleScope.launch { viewModel.saveNotificationEnabled(false) }
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            lifecycleScope.launch { viewModel.saveNotificationEnabled(true) }
        }
    }

    override fun onResume() {
        super.onResume()
        //30분 마다 토큰 재발급하는 임시대책
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : java.lang.Runnable {
            override fun run() {
                // 작업 실행
                Log.d("HandlerTest", "another shot in 1 minute")
                viewModel.refreshToken()
                handler.postDelayed(this, 30 * 60 * 1000) // 30분 후에 다시 실행
            }
        }
        handler.post(runnable)
    }
}