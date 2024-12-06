package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
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
    navLogin: () -> Unit,
    viewModel: MyBirthViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errorState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val saveBirth = remember<(newBirth: Int) -> Unit>{{viewModel.saveBirth(it, navBack)}}
    MyBirthPage(
        uiState = uiState.value,
        errorState = errorState.value,
        saveBirth = saveBirth,
        navBack = navBack,
        navLogin = navLogin
    )
}

@Composable
fun MyBirthPage(
    uiState: MyBirthUiState,
    errorState: ErrorUiState,
    saveBirth: (newBirth: Int) -> Unit,
    navBack: () -> Unit,
    navLogin: () -> Unit
) {
    when (uiState) {
        MyBirthUiState.Loading -> AppLoadingScreen()
        is MyBirthUiState.Success -> {
            SelectBirthContent(
                initBirth = uiState.defaultBirth,
                saveBirth = saveBirth,
                navBack = navBack
            )
        }

        MyBirthUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navLogin,
                errorUiState = errorState,
                onCloseClick = navBack
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SelectBirthContent(
    initBirth: Int,
    saveBirth: (newBirth: Int) -> Unit,
    navBack: () -> Unit
) {
    val availableYearRange = (1950..LocalDateTime.now().year).toList()
    var currentBirth by remember{mutableStateOf(initBirth)}
    val scope = rememberCoroutineScope()
    val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val isEnabled by remember{derivedStateOf{initBirth != currentBirth}}
    val dialogOpen: () -> Unit = {scope.launch{modalSheetState.show()}}
    val dialogClose: () -> Unit = {scope.launch{modalSheetState.hide()}}

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            YearPickerDialog(
                yearList = availableYearRange,
                initialValue = initBirth,
                height = 370.dp,
                onDismiss = dialogClose,
                onDoneClick = {
                    currentBirth = it
                    dialogClose()
                }
            )
        },
        sheetBackgroundColor = CustomColor.gray4
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            TopBar(
                navIcon = painterResource(R.drawable.ic_back),
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
                    value = currentBirth,
                    onClick = dialogOpen,
                    placeholder = "선택"
                )
            }
            Button(
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp),
                isEnabled = isEnabled,
                btnText = "변경",
                onClick = { saveBirth(currentBirth) }
            )
        }
    }
}

@Preview
@Composable
fun BrithTest(){
    var initBirth by remember{mutableIntStateOf(0)}
    SelectBirthContent(
        initBirth = initBirth, saveBirth = { }, navBack = {}
    )
}