package com.example.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_userinfo.viewModel.UserInfoUiState
import com.example.feature_userinfo.viewModel.UserViewModel
import com.hmoa.component.Spinner
import com.hmoa.component.TopBar
import com.hmoa.component.YearPickerDialog
import com.hmoa.core_designsystem.component.Button
import com.hmoa.feature_userinfo.R

@Composable
fun MyBirthRoute(
    onNavBack: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    val isEnabled = viewModel.isEnabled.collectAsStateWithLifecycle()

    MyBirthPage(
        uiState = uiState.value,
        isEnabled = isEnabled.value,
        showDialog = showDialog,
        onChangeDialogState = {
            showDialog = !showDialog
        },
        onUpdateBirth = {
            viewModel.updateBirth(it)
        },
        onSaveBirth = {
            viewModel.saveBirth()
        },
        onNavBack = onNavBack
    )
}

@Composable
fun MyBirthPage(
    uiState: UserInfoUiState,
    isEnabled: Boolean,
    showDialog: Boolean,
    onChangeDialogState: () -> Unit,
    onUpdateBirth: (Int) -> Unit,
    onSaveBirth: () -> Unit,
    onNavBack: () -> Unit
) {
    when (uiState) {
        is UserInfoUiState.Loading -> {

        }

        is UserInfoUiState.UserInfo -> {
            if (showDialog) {
                YearPickerDialog(
                    yearList = (1950..2024).toList(),
                    initialValue = uiState.birth,
                    height = 370.dp,
                    onDismiss = onChangeDialogState,
                    onDoneClick = {
                        // view model에서 가지고 있는 데이터 값을 init value로 가지고 이를 교체
                        onUpdateBirth(it)

                        onChangeDialogState()
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                TopBar(
                    navIcon = painterResource(R.drawable.back_btn),
                    onNavClick = onNavBack,
                    title = "출생연도"
                )

                Spacer(Modifier.height(36.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "출생연도",
                        fontSize = 16.sp,
                        color = Color(0xFF414141)
                    )

                    Spacer(Modifier.height(8.dp))

                    /** Spinner 이용 dialog 띄우는 곳 */
                    Spinner(
                        width = 152.dp,
                        height = 46.dp,
                        value = uiState.birth,
                        onClick = {
                            onChangeDialogState()
                        },
                        placeholder = "선택"
                    )
                }

                /** 데이터 update */
                Button(
                    isEnabled = isEnabled,
                    btnText = "변경",
                    onClick = {
                        onSaveBirth()
                        onNavBack()
                    }
                )
            }

        }
    }
}