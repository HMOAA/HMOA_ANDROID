package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_userinfo.viewModel.MyPageViewModel
import com.example.feature_userinfo.viewModel.UserInfoUiState
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppLoadingDialog
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.OnAndOffBtn
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.data.UserInfo
import com.hmoa.feature_userinfo.NoAuthMyPage

@Composable
internal fun MyPageRoute(
    onNavEditProfile : () -> Unit,
    onNavMyActivity : () -> Unit,
    onNavManageMyInfo : () -> Unit,
    onNavLogin : () -> Unit,
    viewModel : MyPageViewModel = hiltViewModel()
) {

    val isLogin = viewModel.isLogin.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (isLogin.value) {
        //로그인 분기 처리 (토큰 확인)
        MyPage(
            uiState = uiState.value,
            onDelAccount = { viewModel.delAccount() },
            onNavEditProfile = onNavEditProfile,
            onNavMyActivity = onNavMyActivity,
            onNavManageMyInfo = onNavManageMyInfo,
            onNavLogin = onNavLogin
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
    onDelAccount : () -> Unit,
    onNavEditProfile : () -> Unit,
    onNavMyActivity : () -> Unit,
    onNavManageMyInfo : () -> Unit,
    onNavLogin : () -> Unit
){
    val columnInfo = listOf(
        UserInfoColumn("내 활동"){onNavMyActivity()},
        UserInfoColumn("내 정보관리"){onNavManageMyInfo()},
        UserInfoColumn("이용 약관"){ },
        UserInfoColumn("개인정보 처리방침"){  },
        UserInfoColumn("버전 정보"){},
        UserInfoColumn("1대1 문의"){},
        UserInfoColumn("로그아웃"){},
        UserInfoColumn("계정삭제") {
            onDelAccount()
            onNavLogin()
        },
    )

    when(uiState){
        UserInfoUiState.Loading -> {
            AppLoadingDialog()
        }
        is UserInfoUiState.User -> {
            val data = uiState.userInfo
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                TopBar(title = "마이페이지")
                UserProfileInfo(
                    profile = data.profile,
                    nickname = data.nickname,
                    provider = data.provider,
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
                            Text(
                                text = it.title,
                                fontSize = 16.sp
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
                        if (idx % 3 == 2){
                            HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
                        }
                    }
                }
            }
        }
        UserInfoUiState.Error -> {

        }
        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun TestMyPage(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        MyPage(
            uiState = UserInfoUiState.User(UserInfo(2000, "male", "", "안드 호준", "카카오")),
            onDelAccount = {},
            onNavEditProfile = {},
            onNavLogin = {},
            onNavManageMyInfo = {},
            onNavMyActivity = {}
        )
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
                modifier = Modifier.padding(start = 2.dp),
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

private data class UserInfoColumn(
    val title : String,
    val onNavClick : () -> Unit,
)