package com.hmoa.feature_userinfo.screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.NicknameInput
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.viewModel.EditProfileUiState
import com.hmoa.feature_userinfo.viewModel.EditProfileViewModel

@Composable
fun EditProfileRoute(
    navBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errState by viewModel.errorUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val saveInfo = remember<(nickname: String, profileImg: String?) -> Unit> {{
        nickname, profileImg -> viewModel.saveInfo(
            nickname = nickname,
            profileImg = profileImg,
            context = context,
            onSuccess = navBack
        )
    }}

    EditProfilePage(
        uiState = uiState,
        errState = errState,
        onChangeInfo = saveInfo,
        checkDuplication = viewModel::checkNicknameDup,
        navBack = navBack
    )
}

@Composable
fun EditProfilePage(
    uiState: EditProfileUiState,
    errState: ErrorUiState,
    onChangeInfo : (nickname: String, profileImg: String?) -> Unit,
    checkDuplication : (String) -> Unit,
    navBack: () -> Unit,
) {
    when (uiState) {
        EditProfileUiState.Loading -> AppLoadingScreen()
        is EditProfileUiState.Success -> {
            EditProfileContent(
                data = uiState,
                onChangeInfo = onChangeInfo,
                checkDuplication = checkDuplication,
                navBack = navBack
            )
        }
        EditProfileUiState.Error -> {
            ErrorUiSetView(
                isOpen = true,
                onConfirmClick = navBack,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
    }
}

@Composable
private fun EditProfileContent(
    data: EditProfileUiState.Success,
    onChangeInfo: (nickname: String, profileImg: String?) -> Unit,
    checkDuplication: (nickname: String) -> Unit,
    navBack: () -> Unit,
){
    val isDuplicated by data.isDuplicated.collectAsStateWithLifecycle()
    LaunchedEffect(isDuplicated){ Log.d("TAG TEST", "isDup? $isDuplicated")}
    val initNickname by data.nickname.collectAsStateWithLifecycle()
    var profileImg by remember{mutableStateOf(data.profileImg)}
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        if (it != null){ profileImg = it.toString() }
    }
    val isNextEnabled by remember{derivedStateOf{!isDuplicated && profileImg != data.profileImg}}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = navBack,
            title = "프로필 수정",
        )
        Spacer(Modifier.height(38.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(72.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                CircleImageView(imgUrl = profileImg ?: "",width = 72,height = 72)
                EditProfileButton(launcher = launcher)
            }
        }
        Spacer(Modifier.height(72.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "닉네임",
                fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
                color = CustomColor.gray4
            )
            Spacer(Modifier.height(6.dp))
            NicknameInput(
                initNickname = initNickname,
                onPressNicknameExist = checkDuplication,
                isAvailable = isDuplicated
            )
        }
        Spacer(Modifier.weight(1f))
        com.hmoa.core_designsystem.component.Button(
            buttonModifier = Modifier
                .height(82.dp)
                .fillMaxWidth()
                .background(color = if (isNextEnabled) Color.Black else CustomColor.gray2),
            isEnabled = isNextEnabled,
            btnText = "변경",
            onClick = {onChangeInfo(initNickname, profileImg)}
        )
    }
}

@Composable
private fun EditProfileButton(
    launcher: ManagedActivityResultLauncher<String, Uri?>
){
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier.size(16.dp),
            onClick = {
                launcher.launch("image/*")
            }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_comment_input),
                contentDescription = "Profile Edit Button",
                tint = CustomColor.gray2
            )
        }
    }
}