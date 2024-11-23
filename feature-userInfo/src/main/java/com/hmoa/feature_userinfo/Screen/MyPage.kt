package com.example.userinfo

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_userinfo.viewModel.MyPageViewModel
import com.example.feature_userinfo.viewModel.UserInfoUiState
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.checkPermission
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.OnAndOffBtn
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.data.ColumnData
import com.hmoa.core_domain.entity.navigation.UserInfoRoute
import com.hmoa.feature_userinfo.BuildConfig
import com.hmoa.feature_userinfo.screen.NoAuthMyPage
import com.kakao.sdk.talk.TalkApiClient
import kotlinx.coroutines.launch

const val APP_VERSION = "1.1.0"

@Composable
internal fun MyPageRoute(
    appVersion: String,
    navEditProfile: () -> Unit,
    navMyActivity: () -> Unit,
    navManageMyInfo: () -> Unit,
    navLogin: () -> Unit,
    navMyPerfume: () -> Unit,
    navOrderRecord: (befRoute: UserInfoRoute) -> Unit,
    navRefundRecord: () -> Unit,
    navBack: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    val isLogin = viewModel.isLogin.collectAsStateWithLifecycle(false)
    val isEnabledAlarm = viewModel.isEnabled.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()
    val privacyPolicyIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVACY_POLICY_URI)) }
    val termsOfServiceIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.TERMS_OF_SERVICE)) }


    val onChangeAlarm: (Boolean) -> Unit = {
        if (checkPermission(context, Manifest.permission.POST_NOTIFICATIONS)) { viewModel.changeAlarmSetting(it) }
        else { Toast.makeText(context, "알림 권한이 없습니다.\n알림 권한을 설정해주세요.", Toast.LENGTH_SHORT).show() }
    }
    val navKakao = {
        TalkApiClient.instance.chatChannel(context, BuildConfig.KAKAO_CHAT_PROFILE) { err ->
            if (err != null) { Toast.makeText(context, "향모아 챗봇 오류가 발생했습니다:(", Toast.LENGTH_LONG).show() }
        }
    }
    val navOrderRecord = remember{{navOrderRecord(UserInfoRoute.MyPage)}}

    val openLicenses = remember{{
        launcher.launch(Intent(context, OssLicensesMenuActivity::class.java))
        OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스")
    }}
    val logout: () -> Unit = remember{{
        scope.launch {
            launch { viewModel.logout() }.join()
            navLogin()
        }
    }}
    val deleteAccount: () -> Unit = remember{{
        scope.launch {
            launch { viewModel.delAccount() }.join()
            navLogin()
        }
    }}
    val openPrivacyPolicyLink = remember{{context.startActivity(privacyPolicyIntent) }}
    val openTermsOfServiceLink = remember{{context.startActivity(termsOfServiceIntent) }}

    if (isLogin.value) {
        //로그인 분기 처리 (토큰 확인)
        MyPage(
            uiState = uiState.value,
            errorUiState = errorUiState,
            appVersion = appVersion,
            isEnabledAlarm = isEnabledAlarm.value,
            onChangeAlarm = onChangeAlarm,
            logoutEvent = logout,
            openLicenses = openLicenses,
            openPrivacyPolicyLink = openPrivacyPolicyLink,
            openTermsOfServiceLink = openTermsOfServiceLink,
            onDelAccount = deleteAccount,
            onNavKakaoChat = navKakao,
            navMyPerfume = navMyPerfume,
            navEditProfile = navEditProfile,
            navMyActivity = navMyActivity,
            navManageMyInfo = navManageMyInfo,
            navOrderRecord = { navOrderRecord(UserInfoRoute.MyPage) },
            navRefundRecord = navRefundRecord,
            onErrorHandleLoginAgain = navLogin,
            onBackClick = navBack
        )
    } else {
        //로그인 안 되어 있으면
        NoAuthMyPage(
            navLogin = navLogin
        )
    }
}

//인증이 되어 있는 My Page
@Composable
fun MyPage(
    uiState: UserInfoUiState,
    errorUiState: ErrorUiState,
    appVersion: String,
    isEnabledAlarm: Boolean,
    onChangeAlarm: (isEnabledAlarm: Boolean) -> Unit,
    logoutEvent: () -> Unit,
    openLicenses: () -> Unit,
    onDelAccount: () -> Unit,
    openPrivacyPolicyLink: () -> Unit,
    openTermsOfServiceLink: () -> Unit,
    onNavKakaoChat: () -> Unit,
    navMyPerfume: () -> Unit,
    navEditProfile: () -> Unit,
    navMyActivity: () -> Unit,
    navManageMyInfo: () -> Unit,
    navOrderRecord: () -> Unit,
    navRefundRecord: () -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit
) {
    when (uiState) {
        UserInfoUiState.Loading -> AppLoadingScreen()
        is UserInfoUiState.User -> {
            MyPageContent(
                profile = uiState.profile,
                nickname = uiState.nickname,
                provider = uiState.provider,
                appVersion = appVersion,
                isEnabledAlarm = isEnabledAlarm,
                onChangeAlarm = onChangeAlarm,
                logoutEvent = logoutEvent,
                doOpenLicense = openLicenses,
                openPrivacyPolicyLink = openPrivacyPolicyLink,
                openTermsOfServiceLink = openTermsOfServiceLink,
                onDelAccount = onDelAccount,
                onNavKakaoChat = onNavKakaoChat,
                navMyPerfume = navMyPerfume,
                navEditProfile = navEditProfile,
                navMyActivity = navMyActivity,
                navManageMyInfo = navManageMyInfo,
                navOrderRecord = navOrderRecord,
                navRefundRecord = navRefundRecord,
                navBack = onBackClick
            )
        }

        UserInfoUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = { onErrorHandleLoginAgain() },
                errorUiState = errorUiState,
                onCloseClick = { onBackClick() }
            )
        }

        else -> {}
    }
}

@Composable
private fun MyPageContent(
    profile: String,
    nickname: String,
    provider: String,
    appVersion: String,
    isEnabledAlarm: Boolean,
    onChangeAlarm: (isEnabledAlarm: Boolean) -> Unit,
    logoutEvent: () -> Unit,
    doOpenLicense: () -> Unit,
    openPrivacyPolicyLink: () -> Unit,
    openTermsOfServiceLink: () -> Unit,
    onDelAccount: () -> Unit,
    navMyPerfume: () -> Unit,
    onNavKakaoChat: () -> Unit,
    navEditProfile: () -> Unit,
    navMyActivity: () -> Unit,
    navManageMyInfo: () -> Unit,
    navOrderRecord: () -> Unit,
    navRefundRecord: () -> Unit,
    navBack: () -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }
    var url by remember { mutableStateOf("") }
    val columnInfo = listOf(
        ColumnData("주문 내역") { navOrderRecord() },
        ColumnData("취소/반품 내역") { navRefundRecord() },
        ColumnData("나의 향수") { navMyPerfume() },
        ColumnData("내 활동") { navMyActivity() },
        ColumnData("내 정보관리") { navManageMyInfo() },
        ColumnData("오픈소스라이센스") { doOpenLicense() },
        ColumnData("이용 약관") { openTermsOfServiceLink() },
        ColumnData("개인정보 처리방침") { openPrivacyPolicyLink() },
        ColumnData("버전 정보 ${appVersion}") {},
        ColumnData("1대1 문의") { onNavKakaoChat() },
        ColumnData("로그아웃") { logoutEvent() },
        ColumnData("계정삭제") { onDelAccount() }
    )

    BackHandler(
        enabled = true,
        onBack = {
            if (isOpen) isOpen = false
            else navBack()
        }
    )
    if (isOpen) {
        CustomWebView(url)
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            item {
                TopBar(title = "마이페이지")
                UserProfileInfo(
                    profile = profile,
                    nickname = nickname,
                    provider = provider,
                    navEditProfile = navEditProfile
                )
                ServiceAlarm(isEnabledAlarm = isEnabledAlarm, onChangeAlarm = onChangeAlarm)
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
            }

            itemsIndexed(columnInfo) { idx, it ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clickable { it.onNavClick() }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = it.title,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    )

                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_next),
                        contentDescription = "Navigation Button",
                        tint = CustomColor.gray2
                    )
                }
                if (idx % 3 == 2) { HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2) }
            }
        }
    }
}

@Composable
private fun UserProfileInfo(
    profile: String,
    nickname: String,
    provider: String,
    navEditProfile: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(horizontal = 16.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(imgUrl = profile, width = 44, height = 44)
        Column(modifier = Modifier.weight(1f)) {
            //user 이름
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = nickname,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            )
            Spacer(Modifier.weight(1f))
            // 로그인 방식
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = provider,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(20.dp),
            onClick = navEditProfile
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit Button",
                tint = CustomColor.gray2
            )
        }
    }
}

@Composable
private fun ServiceAlarm(
    isEnabledAlarm: Boolean,
    onChangeAlarm: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "서비스 알림",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
        )

        /** service alarm 토글 버튼 */
        OnAndOffBtn(
            isChecked = isEnabledAlarm,
            onChangeChecked = onChangeAlarm
        )
    }
}

@Composable
private fun CustomWebView(url: String) {
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.domStorageEnabled = true
                webViewClient = object : WebViewClient() {

                }

                loadUrl(url)
            }
        }
    )
}