package com.example.feature_userinfo

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.hmoa.core_common.checkPermission
import com.hmoa.core_common.galleryPermission
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.viewModel.EditProfileUiState
import com.hmoa.feature_userinfo.viewModel.EditProfileViewModel

@Composable
fun EditProfileRoute(
    navBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    val nickname = viewModel.nickname.collectAsStateWithLifecycle()
    val isEnabled = viewModel.isEnabled.collectAsStateWithLifecycle(false)
    val isDuplicated = viewModel.isEnabledBtn.collectAsStateWithLifecycle(false)
    val profileImg = viewModel.profileImg.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            viewModel.updateProfile(it.toString())
        }
    }

    EditProfilePage(
        nickname = nickname.value ?: "",
        uiState = uiState.value,
        isEnabled = isEnabled.value,
        isDuplicated = isDuplicated.value,
        profileImg = profileImg.value,
        onChangeInfo = { viewModel.saveInfo() },
        onEditProfile = {
            if (checkPermission(context, galleryPermission)) {
                launcher.launch("image/*")
            } else {
                Toast.makeText(context, "갤러리 권한이 필요합니다", Toast.LENGTH_SHORT).show()
            }
        },
        checkDuplication = { viewModel.checkNicknameDup(it) },
        onUpdateNickname = { viewModel.updateNickname(it) },
        onNavBack = navBack
    )
}

@Composable
fun EditProfilePage(
    nickname: String,
    uiState: EditProfileUiState,
    isEnabled: Boolean,
    isDuplicated: Boolean,
    profileImg: String?,
    onChangeInfo: () -> Unit,
    onEditProfile: () -> Unit,
    checkDuplication: (String) -> Unit,
    onUpdateNickname: (String) -> Unit,
    onNavBack: () -> Unit,
) {
    when (uiState) {
        EditProfileUiState.Loading -> AppLoadingScreen()
        EditProfileUiState.Success -> {
            EditProfileContent(
                nickname = nickname,
                isEnabled = isEnabled,
                isDuplicated = isDuplicated,
                profileImg = profileImg,
                onEditProfile = onEditProfile,
                onChangeInfo = onChangeInfo,
                checkDuplication = checkDuplication,
                onUpdateNickname = onUpdateNickname,
                onNavBack = onNavBack
            )
        }

        is EditProfileUiState.Error -> {
            var showDialog by remember { mutableStateOf(true) }
            AppDefaultDialog(
                isOpen = showDialog,
                modifier = Modifier.fillMaxWidth(0.7f),
                title = "오류",
                content = uiState.message,
                onDismiss = {
                    showDialog = false
                    onNavBack()
                }
            )
        }
    }
}

@Composable
private fun EditProfileContent(
    nickname: String,
    isEnabled: Boolean,
    isDuplicated: Boolean,
    profileImg: String?,
    onEditProfile: () -> Unit,
    onChangeInfo: () -> Unit,
    checkDuplication: (String) -> Unit,
    onUpdateNickname: (String) -> Unit,
    onNavBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack,
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
                CircleImageView(imgUrl = profileImg ?: "", width = 72, height = 72)
                EditProfileButton(onEditProfile = onEditProfile)
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
                onNavBack()
            }
        )
    }
}

@Composable
private fun EditProfileButton(
    onEditProfile: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier.size(16.dp),
            onClick = onEditProfile
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.profile_edit_btn),
                contentDescription = "Profile Edit Button",
                tint = CustomColor.gray2
            )
        }
    }
}