package com.example.feature_userinfo

import android.net.Uri
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppDefaultDialog
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.NicknameInput
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.R
import com.hmoa.feature_userinfo.viewModel.EditProfileUiState
import com.hmoa.feature_userinfo.viewModel.EditProfileViewModel

@Composable
fun EditProfileRoute(
    navBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val nickname = viewModel.nickname.collectAsStateWithLifecycle()
    val isEnabled = viewModel.isEnabled.collectAsStateWithLifecycle(false)
    val isDuplicated = viewModel.isEnabledBtn.collectAsStateWithLifecycle(false)
    val profileImg = viewModel.profileImg.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        if (it != null){viewModel.updateProfile(it.toString())}
    }

    EditProfilePage(
        launcher = launcher,
        nickname = nickname.value ?: "",
        uiState = uiState.value,
        isEnabled = isEnabled.value,
        isDuplicated = isDuplicated.value,
        profileImg = profileImg.value,
        onChangeInfo = {viewModel.saveInfo()},
        checkDuplication = {viewModel.checkNicknameDup(it)},
        onUpdateNickname = {viewModel.updateNickname(it)},
        navBack = navBack
    )
}

@Composable
fun EditProfilePage(
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    nickname : String,
    uiState: EditProfileUiState,
    isEnabled: Boolean,
    isDuplicated : Boolean,
    profileImg : String?,
    onChangeInfo : () -> Unit,
    checkDuplication : (String) -> Unit,
    onUpdateNickname : (String) -> Unit,
    navBack: () -> Unit,
) {
    when (uiState) {
        EditProfileUiState.Loading -> AppLoadingScreen()
        EditProfileUiState.Success -> {
            EditProfileContent(
                launcher = launcher,
                nickname = nickname,
                isEnabled = isEnabled,
                isDuplicated = isDuplicated,
                profileImg = profileImg,
                onChangeInfo = onChangeInfo,
                checkDuplication = checkDuplication,
                onUpdateNickname = onUpdateNickname,
                navBack = navBack
            )
        }
        is EditProfileUiState.Error -> {
            var showDialog by remember{mutableStateOf(true)}
            AppDefaultDialog(
                isOpen = showDialog,
                modifier = Modifier.fillMaxWidth(0.7f),
                title = "오류",
                content = uiState.message,
                onDismiss = {
                    showDialog = false
                    navBack()
                }
            )
        }
    }
}

@Composable
private fun EditProfileContent(
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    nickname : String,
    isEnabled : Boolean,
    isDuplicated : Boolean,
    profileImg : String?,
    onChangeInfo : () -> Unit,
    checkDuplication : (String) -> Unit,
    onUpdateNickname : (String) -> Unit,
    navBack : () -> Unit,
){
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
                initNickname = nickname,
                onChangeValue = onUpdateNickname,
                onPressNicknameExist = {
                    checkDuplication(it)
                },
                isAvailable = isDuplicated,
                isEnabled = isEnabled
            )
        }
        Spacer(Modifier.weight(1f))
        com.hmoa.core_designsystem.component.Button(
            buttonModifier = Modifier
                .height(82.dp)
                .fillMaxWidth()
                .background(color = if (isDuplicated) Color.Black else CustomColor.gray2),
            isEnabled = isDuplicated,
            btnText = "변경",
            onClick = {
                onChangeInfo()
                navBack()
            }
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
                painter = painterResource(R.drawable.profile_edit_btn),
                contentDescription = "Profile Edit Button",
                tint = CustomColor.gray2
            )
        }
    }
}