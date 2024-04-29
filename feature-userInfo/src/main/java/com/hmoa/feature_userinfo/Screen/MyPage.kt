package com.example.userinfo

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_userinfo.viewModel.MyPageViewModel
import com.example.feature_userinfo.viewModel.UserInfoUiState
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.OnAndOffBtn
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.ColumnData
import com.hmoa.feature_userinfo.NoAuthMyPage
import com.kakao.sdk.talk.TalkApiClient
import com.hmoa.feature_userinfo.BuildConfig

const val APP_VERSION = "1.0.0"

@Composable
internal fun MyPageRoute(
    onNavEditProfile : () -> Unit,
    onNavMyActivity : () -> Unit,
    onNavManageMyInfo : () -> Unit,
    onNavLogin : () -> Unit,
    viewModel : MyPageViewModel = hiltViewModel()
) {
    val isLogin = viewModel.isLogin.collectAsStateWithLifecycle(false)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val navKakao = {
        TalkApiClient.instance.chatChannel(context, BuildConfig.KAKAO_CHAT_PROFILE) { err ->
            if (err != null) {
                viewModel.updateErr(err.message!!)
            }
        }
    }

    if (isLogin.value) {
        //로그인 분기 처리 (토큰 확인)
        MyPage(
            uiState = uiState.value,
            loginEvent = {
                viewModel.logout()
                onNavLogin()
            },
            onDelAccount = {
                viewModel.delAccount()
                onNavLogin()
            },
            onNavKakaoChat = navKakao,
            onNavEditProfile = onNavEditProfile,
            onNavMyActivity = onNavMyActivity,
            onNavManageMyInfo = onNavManageMyInfo,
        )
    } else {
        //로그인 안 되어 있으면
        NoAuthMyPage (
            onNavLogin = onNavLogin
        )
    }
}

//인증이 되어 있는 My Page
@Composable
fun MyPage(
    uiState : UserInfoUiState,
    loginEvent : () -> Unit,
    onDelAccount : () -> Unit,
    onNavKakaoChat: () -> Unit,
    onNavEditProfile : () -> Unit,
    onNavMyActivity : () -> Unit,
    onNavManageMyInfo : () -> Unit,
){
    when(uiState){
        UserInfoUiState.Loading -> {
            AppLoadingScreen()
        }
        is UserInfoUiState.User -> {
            MyPageContent(
                profile = uiState.profile,
                nickname = uiState.nickname,
                provider = uiState.provider,
                loginEvent = loginEvent,
                onDelAccount = onDelAccount,
                onNavKakaoChat = onNavKakaoChat,
                onNavEditProfile = onNavEditProfile,
                onNavMyActivity = onNavMyActivity,
                onNavManageMyInfo = onNavManageMyInfo,
            )
        }
        UserInfoUiState.Error -> {

        }
        else -> {}
    }
}
@Composable
private fun MyPageContent(
    profile : String,
    nickname : String,
    provider : String,
    loginEvent: () -> Unit,
    onDelAccount : () -> Unit,
    onNavKakaoChat : () -> Unit,
    onNavEditProfile: () -> Unit,
    onNavMyActivity: () -> Unit,
    onNavManageMyInfo : () -> Unit,
){
    val columnInfo = listOf(
        ColumnData("내 활동"){onNavMyActivity()},
        ColumnData("내 정보관리"){onNavManageMyInfo()},
        ColumnData("이용 약관"){ },
        ColumnData("개인정보 처리방침"){  },
        ColumnData("버전 정보 ${APP_VERSION}"){},
        ColumnData("1대1 문의"){onNavKakaoChat()},
        ColumnData("로그아웃"){loginEvent()},
        ColumnData("계정삭제") {onDelAccount()},
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(title = "마이페이지")
        UserProfileInfo(
            profile = profile,
            nickname = nickname,
            provider = provider,
            onNavEditProfile = onNavEditProfile
        )
        HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
        ServiceAlarm()
        LazyColumn{
            itemsIndexed(columnInfo){idx, it ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = it.title,fontSize = 16.sp)

                    if (idx != 4){
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
                }
                if (idx % 3 == 1){
                    HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
                }
            }
        }
    }
}

@Composable
private fun UserProfileInfo(
    profile : String,
    nickname : String,
    provider : String,
    onNavEditProfile : () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(horizontal = 16.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        CircleImageView(imgUrl = profile, width = 44, height = 44)
        Column(modifier = Modifier.weight(1f)){
            //user 이름
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = nickname,
                fontSize = 20.sp
            )
            Spacer(Modifier.weight(1f))
            // 로그인 방식
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = provider,
                fontSize = 12.sp,
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

){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "서비스 알림",
            fontSize = 16.sp
        )

        /** service alarm 토글 버튼 */
        OnAndOffBtn()
    }
}