package com.example.userinfo

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_userinfo.viewModel.MyPageViewModel
import com.example.feature_userinfo.viewModel.UserInfoUiState
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.hmoa.component.TopBar
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.checkPermission
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.OnAndOffBtn
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.BuildConfig
import com.hmoa.feature_userinfo.ColumnData
import com.hmoa.feature_userinfo.NoAuthMyPage
import com.kakao.sdk.talk.TalkApiClient

@Composable
internal fun MyPageRoute(
    onNavEditProfile: () -> Unit,
    onNavMyActivity: () -> Unit,
    onNavManageMyInfo: () -> Unit,
    onNavLogin: () -> Unit,
    onNavMyPerfume: () -> Unit,
    onNavBack: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val appVersion = "1.0.1"
    val isLogin = viewModel.isLogin.collectAsStateWithLifecycle(false)
    val isEnabledAlarm = viewModel.isEnabled.collectAsStateWithLifecycle()
    val onChangeAlarm: (Boolean) -> Unit = {
        if (checkPermission(context, Manifest.permission.POST_NOTIFICATIONS)) {viewModel.changeAlarmSetting(it)}
        else {Toast.makeText(context, "알림 권한이 없습니다.\n알림 권한을 설정해주세요.", Toast.LENGTH_SHORT).show()}
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()
    val navKakao = {
        TalkApiClient.instance.chatChannel(context, BuildConfig.KAKAO_CHAT_PROFILE) { err ->
            if (err != null) {Toast.makeText(context, "향모아 챗봇 오류가 발생했습니다:(", Toast.LENGTH_LONG).show()}
        }
    }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    val privacyPolicyIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVACY_POLICY_URI)) }
    if (isLogin.value) {
        //로그인 분기 처리 (토큰 확인)
        MyPage(
            uiState = uiState.value,
            errorUiState = errorUiState,
            appVersion = appVersion,
            isEnabledAlarm = isEnabledAlarm.value,
            onChangeAlarm = onChangeAlarm,
            logoutEvent = {
                viewModel.logout()
                onNavLogin()
            },
            doOpenLicense = {
                launcher.launch(Intent(context, OssLicensesMenuActivity::class.java))
                OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스")
            },
            openPrivacyPolicyLink = { context.startActivity(privacyPolicyIntent) },
            onDelAccount = {
                viewModel.delAccount()
                onNavLogin()
            },
            onNavKakaoChat = navKakao,
            onNavMyPerfume = onNavMyPerfume,
            onNavEditProfile = onNavEditProfile,
            onNavMyActivity = onNavMyActivity,
            onNavManageMyInfo = onNavManageMyInfo,
            onErrorHandleLoginAgain = onNavLogin,
            onBackClick = onNavBack
        )
    } else {
        //로그인 안 되어 있으면
        NoAuthMyPage(
            onNavLogin = onNavLogin
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
    onChangeAlarm: (Boolean) -> Unit,
    logoutEvent: () -> Unit,
    doOpenLicense: () -> Unit,
    onDelAccount: () -> Unit,
    openPrivacyPolicyLink: () -> Unit,
    onNavKakaoChat: () -> Unit,
    onNavMyPerfume: () -> Unit,
    onNavEditProfile: () -> Unit,
    onNavMyActivity: () -> Unit,
    onNavManageMyInfo: () -> Unit,
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
                doOpenLicense = doOpenLicense,
                openPrivacyPolicyLink = openPrivacyPolicyLink,
                onDelAccount = onDelAccount,
                onNavKakaoChat = onNavKakaoChat,
                onNavMyPerfume = onNavMyPerfume,
                onNavEditProfile = onNavEditProfile,
                onNavMyActivity = onNavMyActivity,
                onNavManageMyInfo = onNavManageMyInfo,
            )
        }
        UserInfoUiState.Error -> {
            ErrorUiSetView(
                onConfirmClick = { onErrorHandleLoginAgain() },
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
    onChangeAlarm: (Boolean) -> Unit,
    logoutEvent: () -> Unit,
    doOpenLicense: () -> Unit,
    openPrivacyPolicyLink: () -> Unit,
    onDelAccount: () -> Unit,
    onNavMyPerfume : () -> Unit,
    onNavKakaoChat: () -> Unit,
    onNavEditProfile: () -> Unit,
    onNavMyActivity: () -> Unit,
    onNavManageMyInfo: () -> Unit,
) {
    val columnInfo = listOf(
        ColumnData("나의 향수") {onNavMyPerfume() },
        ColumnData("내 활동") { onNavMyActivity() },
        ColumnData("내 정보관리") { onNavManageMyInfo() },
        ColumnData("오픈소스라이센스") { doOpenLicense() },
        ColumnData("이용 약관") { },
        ColumnData("개인정보 처리방침") { openPrivacyPolicyLink() },
        ColumnData("버전 정보 ${appVersion}") {},
        ColumnData("1대1 문의") { onNavKakaoChat() },
        ColumnData("로그아웃") { logoutEvent() },
        ColumnData("계정삭제") { onDelAccount() }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        item{
            TopBar(title = "마이페이지")
            UserProfileInfo(
                profile = profile,
                nickname = nickname,
                provider = provider,
                onNavEditProfile = onNavEditProfile
            )
            ServiceAlarm(isEnabledAlarm = isEnabledAlarm, onChangeAlarm = onChangeAlarm)
            HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
        }

        itemsIndexed(columnInfo) { idx, it ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = it.title,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                )
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = it.onNavClick
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_next),
                        contentDescription = "Navigation Button",
                        tint = CustomColor.gray2
                    )
                }
            }
            if (idx % 3 == 2) {HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)}
        }
    }
}

@Composable
private fun UserProfileInfo(
    profile: String,
    nickname: String,
    provider: String,
    onNavEditProfile: () -> Unit,
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
            onClick = onNavEditProfile
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
@Preview
@Composable
private fun TestUiMyPage(){
    MyPage(
        uiState = UserInfoUiState.User("", "안드 호준", "Kakao"),
        errorUiState = ErrorUiState.Loading,
        appVersion = "1.1.0",
        isEnabledAlarm = false,
        onChangeAlarm = {},
        logoutEvent = { /*TODO*/ },
        doOpenLicense = { /*TODO*/ },
        onDelAccount = { /*TODO*/ },
        openPrivacyPolicyLink = { /*TODO*/ },
        onNavKakaoChat = { /*TODO*/ },
        onNavMyPerfume = { /*TODO*/ },
        onNavEditProfile = { /*TODO*/ },
        onNavMyActivity = { /*TODO*/ },
        onNavManageMyInfo = { /*TODO*/ },
        onErrorHandleLoginAgain = { /*TODO*/ }) {

    }
}