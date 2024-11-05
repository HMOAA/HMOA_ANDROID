package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.viewModel.MyBirthUiState
import com.hmoa.feature_userinfo.viewModel.MyBirthViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun MyBirthRoute(
    navBack: () -> Unit,
    viewModel: MyBirthViewModel = hiltViewModel()
) {
    val availableYearRange = (1950..LocalDateTime.now().year).toList()

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errorState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val isEnabled = viewModel.isEnabled.collectAsStateWithLifecycle(false)
    val birth = viewModel.birth.collectAsStateWithLifecycle()

    MyBirthPage(
        availableYearRange = availableYearRange,
        uiState = uiState.value,
        errorState = errorState.value,
        birth = birth.value,
        isEnabled = isEnabled.value,
        onUpdateBirth = { viewModel.updateBirth(it) },
        onSaveBirth = { viewModel.saveBirth() },
        navBack = navBack
    )
}

@Composable
fun MyBirthPage(
    availableYearRange: List<Int>,
    uiState: MyBirthUiState,
    errorState: ErrorUiState,
    birth: Int?,
    isEnabled: Boolean,
    onUpdateBirth: (Int) -> Unit,
    onSaveBirth: () -> Unit,
    navBack: () -> Unit
) {
    when (uiState) {
        MyBirthUiState.Loading -> AppLoadingScreen()
        MyBirthUiState.Success -> {
            SelectBirthContent(
                availableYearRange = availableYearRange,
                birth = birth!!,
                isEnabled = isEnabled,
                onUpdateBirth = onUpdateBirth,
                onSaveBirth = onSaveBirth,
                navBack = navBack
            )
        }

        MyBirthUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navBack,
                errorUiState = errorState,
                onCloseClick = navBack
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SelectBirthContent(
    availableYearRange: List<Int>,
    birth: Int,
    isEnabled: Boolean,
    onUpdateBirth: (Int) -> Unit,
    onSaveBirth: () -> Unit,
    navBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(CustomColor.gray4),
                verticalArrangement = Arrangement.Bottom
            ) {
                YearPickerDialog(
                    yearList = availableYearRange,
                    initialValue = birth,
                    height = 370.dp,
                    onDismiss = { scope.launch { modalSheetState.hide() } },
                    onDoneClick = {
                        onUpdateBirth(it)
                        scope.launch { modalSheetState.hide() }
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            TopBar(
                navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                onNavClick = navBack,
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
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    color = CustomColor.gray4
                )
                Spacer(Modifier.height(8.dp))
                Spinner(
                    width = 152.dp,
                    height = 46.dp,
                    value = birth,
                    onClick = { scope.launch { modalSheetState.show() } },
                    placeholder = "선택"
                )
            }
            Button(
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp),
                isEnabled = isEnabled,
                btnText = "변경",
                onClick = {
                    onSaveBirth()
                    navBack()
                }
            )
        }
    }
}