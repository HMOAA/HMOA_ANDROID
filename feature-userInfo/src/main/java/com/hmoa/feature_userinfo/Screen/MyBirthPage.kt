package com.example.feature_userinfo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.Spinner
import com.hmoa.component.TopBar
import com.hmoa.component.YearPickerDialog
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.viewModel.MyBirthUiState
import com.hmoa.feature_userinfo.viewModel.MyBirthViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
@Composable
fun MyBirthRoute(
    onNavBack: () -> Unit,
    viewModel: MyBirthViewModel = hiltViewModel()
) {
    val availableYearRange = (1950..LocalDateTime.now().year).toList()

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    val isEnabled = viewModel.isEnabled.collectAsStateWithLifecycle(false)
    val birth = viewModel.birth.collectAsStateWithLifecycle()
    viewModel.init()

    MyBirthPage(
        availableYearRange = availableYearRange,
        uiState = uiState.value,
        birth = birth.value,
        isEnabled = isEnabled.value,
        showDialog = showDialog,
        onChangeDialogState = {showDialog = !showDialog},
        onUpdateBirth = {viewModel.updateBirth(it)},
        onSaveBirth = {viewModel.saveBirth()},
        onNavBack = onNavBack
    )
}

@Composable
fun MyBirthPage(
    availableYearRange : List<Int>,
    uiState: MyBirthUiState,
    birth : Int?,
    isEnabled: Boolean,
    showDialog: Boolean,
    onChangeDialogState: () -> Unit,
    onUpdateBirth: (Int) -> Unit,
    onSaveBirth: () -> Unit,
    onNavBack: () -> Unit
) {
    when (uiState) {
        MyBirthUiState.Loading -> {
            AppLoadingScreen()
        }
        MyBirthUiState.Success -> {
            SelectBirthContent(
                availableYearRange = availableYearRange,
                birth = birth!!,
                isEnabled = isEnabled,
                showDialog = showDialog,
                onChangeDialogState = onChangeDialogState,
                onUpdateBirth = onUpdateBirth,
                onSaveBirth = onSaveBirth,
                onNavBack = onNavBack
            )
        }
        MyBirthUiState.Error -> {
            Text("Error : 뭐징")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SelectBirthContent(
    availableYearRange : List<Int>,
    birth : Int,
    isEnabled: Boolean,
    showDialog: Boolean,
    onChangeDialogState: () -> Unit,
    onUpdateBirth: (Int) -> Unit,
    onSaveBirth: () -> Unit,
    onNavBack: () -> Unit
){
    val scope = rememberCoroutineScope()
    val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {it!=ModalBottomSheetValue.HalfExpanded},
        skipHalfExpanded = true
    )
    Log.d("TAG TEST", "showDialog : ${showDialog}")
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(CustomColor.gray4),
                verticalArrangement = Arrangement.Bottom
            ){
                YearPickerDialog(
                    yearList = availableYearRange,
                    initialValue = birth,
                    height = 370.dp,
                    onDismiss = {scope.launch{modalSheetState.hide()}},
                    onDoneClick = {
                        onUpdateBirth(it)
                        scope.launch{modalSheetState.hide()}
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
                    color = CustomColor.gray4
                )
                Spacer(Modifier.height(8.dp))
                Spinner(
                    width = 152.dp,
                    height = 46.dp,
                    value = birth,
                    onClick = {scope.launch{modalSheetState.show()}},
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
                    onNavBack()
                }
            )
        }
    }
}