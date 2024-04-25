package com.example.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.RadioButtonList
import com.hmoa.feature_userinfo.viewModel.MyGenderUiState
import com.hmoa.feature_userinfo.viewModel.MyGenderViewModel

@Composable
fun MyGenderRoute(
    onNavBack: () -> Unit,
    viewModel: MyGenderViewModel = hiltViewModel()
) {

    val isEnabled = viewModel.isEnabled.collectAsStateWithLifecycle(false)
    val gender = viewModel.gender.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MyGenderPage(
        uiState = uiState.value,
        gender = gender.value,
        onUpdateGender = {viewModel.updateGender(it)},
        onSaveGender = {viewModel.saveGender()},
        isEnabled = isEnabled.value,
        onNavBack = onNavBack
    )
}

@Composable
fun MyGenderPage(
    uiState: MyGenderUiState,
    gender : String?,
    onUpdateGender: (String) -> Unit,
    onSaveGender: () -> Unit,
    isEnabled: Boolean,
    onNavBack: () -> Unit,
) {
    when (uiState) {
        MyGenderUiState.Loading -> {
            AppLoadingScreen()
        }
        MyGenderUiState.Success -> {
            SelectGenderContent(
                isEnabled = isEnabled,
                gender = gender!!,
                onUpdateGender = onUpdateGender,
                onSaveGender = onSaveGender,
                onNavBack = onNavBack
            )
        }
        MyGenderUiState.Error -> {

        }
    }
}

@Composable
private fun SelectGenderContent(
    isEnabled : Boolean,
    gender : String,
    onUpdateGender : (String) -> Unit,
    onSaveGender : () -> Unit,
    onNavBack: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack,
            title = "성별"
        )
        Spacer(Modifier.height(38.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButtonList(
                initValue = gender,
                radioOptions = listOf("남성","여성"),
                onButtonClick ={onUpdateGender(it)}
            )
        }
        Spacer(Modifier.weight(1f))
        Button(
            buttonModifier = Modifier
                .fillMaxWidth()
                .height(78.dp),
            isEnabled = isEnabled,
            btnText = "변경",
            onClick = {
                onSaveGender()
                onNavBack()
            }
        )
    }
}