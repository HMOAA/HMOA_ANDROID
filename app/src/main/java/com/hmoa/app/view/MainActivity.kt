package com.hmoa.app.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.hmoa.app.BuildConfig
import com.hmoa.app.navigation.SetUpNavGraph
import com.hmoa.app.viewmodel.AppViewModel
import com.hmoa.core_common.permissions
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.component.HomeTopBar
import com.hmoa.core_designsystem.component.MainBottomBar
import com.hmoa.core_domain.entity.navigation.*
import com.hmoa.feature_brand.navigation.navigateToBrandSearch
import com.hmoa.feature_fcm.navigateToAlarmScreen
import com.hmoa.feature_home.navigation.navigateToHome
import com.hmoa.feature_home.navigation.navigateToPerfumeSearch
import com.hmoa.feature_hpedia.Navigation.navigateToHPedia
import com.hmoa.feature_magazine.Navigation.navigateToMagazineHome
import com.hmoa.feature_userinfo.navigation.navigateToUserInfoGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kr.co.bootpay.android.BootpayAnalytics

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
        UserInfoRoute.MyPage.name,
        UserInfoRoute.MyInfoRoute.name,
        UserInfoRoute.MyFavoriteCommentRoute.name,
        UserInfoRoute.MyActivityRoute.name,
        UserInfoRoute.MyCommentRoute.name,
        UserInfoRoute.MyPostRoute.name,
        UserInfoRoute.MyFavoritePerfumeRoute.name,
        MagazineRoute.Magazine.name
    )

    private val needTopBarScreens = HomeRoute.Home.name
    private val bottomNav = listOf(
        BottomScreen.Home.name,
        BottomScreen.HPedia.name,
        BottomScreen.Magazine.name,
        BottomScreen.MyPage.name
    )
    private lateinit var appUpdateManager: AppUpdateManager
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
            handleUpdateResult(result)
        }

    // Displays the snackbar notification and call to action.
    fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(com.hmoa.core_designsystem.R.drawable.ic_fab),
            "새로운 업데이트 다운로드가 완료되었습니다.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("재시작") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    private fun checkImmediateUpdateAvailability() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (shouldTriggerImmediateUpdate(appUpdateInfo)) {
                Log.d("checkUpdateAvailability", "ImemediateUpdate")
                startImmediateUpdate(appUpdateInfo)
            }
        }.addOnFailureListener { e ->
            Log.e("UpdateFlow", "Failed to check update availability", e)
        }
    }

    private fun checkFlexibleUpdateAvailability() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (shouldTriggerFlexibleUpdate(appUpdateInfo)) {
                Log.d("checkUpdateAvailability", "ImemediateUpdate")
                startFlexibleUpdate(appUpdateInfo)
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }.addOnFailureListener { e ->
            Log.e("UpdateFlow", "Failed to check update availability", e)
        }
    }


    private fun shouldTriggerImmediateUpdate(appUpdateInfo: AppUpdateInfo): Boolean {
        return appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
    }

    private fun shouldTriggerFlexibleUpdate(appUpdateInfo: AppUpdateInfo): Boolean {
        return appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
    }


    private fun startImmediateUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            activityResultLauncher,
            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
        )
    }

    private fun startFlexibleUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            activityResultLauncher,
            AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
        )
    }

    private fun handleUpdateResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            Log.d("UpdateFlow", "Update flow completed successfully!")
        } else {
            Log.e("UpdateFlow", "Update flow failed! Result code: ${result.resultCode}")
            // 필요시 재시도 로직 추가 가능
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        appUpdateManager = AppUpdateManagerFactory.create(this)
        checkImmediateUpdateAvailability()

        requestNotificationPermission()
        BootpayAnalytics.init(this, BuildConfig.BOOTPAY_APPLICATION_ID)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.fcmTokenFlow()
                    .combine(viewModel.getNotificationEnabled()) { fcmToken, isNotificationsEnabled ->
                        Pair(
                            fcmToken,
                            isNotificationsEnabled
                        )
                    }.collectLatest {
                        val fcmToken = it.first
                        val isNotificationsEnabled = it.second
                        Log.d("POST PERMISSION", "is granted : ${isNotificationsEnabled}")
                        Log.d("FCM TEST", "fcm token : ${fcmToken}")
                        initializeFirebaseSetting(
                            fcmToken = fcmToken,
                            onSaveFcmToken = { token -> viewModel.saveFcmToken(token) })
                        initializeRoute(
                            onRouteToLogin = { checkFcmToken(fcmToken, isNotificationsEnabled) })
                    }
            }
        }


        setContent {
            val navHostController = rememberNavController()

            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            val deeplink = remember { handleDeeplink(intent) }

            Scaffold(
                modifier = Modifier.systemBarsPadding(),
                backgroundColor = Color.White,
                bottomBar = {
                    MainBottomBar(
                        navController = navHostController,
                        needBottomBarScreen = needBottomBarScreens,
                        onClickHome = navHostController::navigateToHome,
                        onClickHPedia = navHostController::navigateToHPedia,
                        onClickLike = navHostController::navigateToMagazineHome,
                        onClickMyPage = navHostController::navigateToUserInfoGraph
                    )
                },
                scaffoldState = scaffoldState,
                topBar = {
                    HomeTopBar(
                        navBackStackEntry = navBackStackEntry,
                        onDrawerClick = { navHostController.navigateToBrandSearch() },
                        onSearchClick = { navHostController.navigateToPerfumeSearch() },
                        onNotificationClick = { navHostController.navigateToAlarmScreen() },
                        drawerIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_drawer),
                        searchIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_search),
                        notificationIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_bell)
                    )
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

    override fun onResume() {
        super.onResume()
        checkFlexibleUpdateAvailability()
    }

    //firebase 초기 토큰 처리
    private fun initializeFirebaseSetting(fcmToken: String?, onSaveFcmToken: (token: String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            if (it != fcmToken) {
                Log.d("FCM TEST", "firebase messaging fcm token : ${it}")
                onSaveFcmToken(it)
            }
        }.addOnFailureListener {
            Log.e("FCM TEST", "${it.message} \n ${it.stackTrace}")
        }
    }

    private suspend fun initializeRoute(
        onRouteToLogin: suspend () -> Unit
    ) {
        viewModel.authTokenFlow().collectLatest { authToken ->
            viewModel.rememberedTokenFlow().collectLatest { rememberToken ->
                Log.d("LOGIN TOKEN", "auth : ${authToken} remember : ${rememberToken}")
                if (authToken == null && rememberToken == null) {
                    initialRoute = AuthenticationRoute.Login.name
                    onRouteToLogin()
                } else {
                    initialRoute = HomeRoute.Home.name
                }
            }
        }
    }

    private suspend fun checkFcmToken(fcmToken: String?, isEnabled: Boolean) {
        if (fcmToken != null) {
            handleFcmToken(fcmToken, isEnabled)
        }
        Log.d("FCM TEST", "checkFcmToken의 fcmToken 값: ${fcmToken}")
    }

    private fun handleFcmToken(
        fcmToken: String, isNotificationEnabled: Boolean
    ) {
        if (isNotificationEnabled) {
            Log.d("FCM TEST", "post fcm token")
            viewModel.postFcmToken(fcmToken)
        } else {
            Log.d("FCM TEST", "delete fcm token")
            viewModel.delFcmToken()
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
}
