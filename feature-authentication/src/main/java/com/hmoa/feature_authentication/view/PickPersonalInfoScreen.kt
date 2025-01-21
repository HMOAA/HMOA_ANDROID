package com.hmoa.feature_authentication.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_authentication.contract.PickPersonalInfoEffect
import com.hmoa.feature_authentication.contract.PickPersonalInfoEvent
import com.hmoa.feature_authentication.viewmodel.PickPersonalInfoViewmodel
import kotlinx.coroutines.launch

@Composable
internal fun PickPersonalInfoRoute(
    onHomeClick: () -> Unit,
    onPickNicknameClick: () -> Unit,
    loginProvider: String,
    viewModel: PickPersonalInfoViewmodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            Log.d("Effects", "effect:${effect}")
            when (effect) {
                PickPersonalInfoEffect.NavigateToHome -> onHomeClick()
            }
        }
    }

    PickPersonalInfoScreen(
        onFinishOnBoarding = {
            viewModel.handleEvent(PickPersonalInfoEvent.FinishOnBoarding(loginProvider))
        },
        onPickNicknameClick = { onPickNicknameClick() },
        onClickBirthYear = { viewModel.handleEvent(PickPersonalInfoEvent.SaveBirthYear(it)) },
        onClickSex = { viewModel.handleEvent(PickPersonalInfoEvent.SaveSex(it)) },
        birthYearState = uiState.birthYear,
        isNextButtonEnabled = uiState.isAvailableToSignup,
        radioOptions = viewModel.SEX
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PickPersonalInfoScreen(
    onFinishOnBoarding: () -> Unit,
    onPickNicknameClick: () -> Unit,
    onClickBirthYear: (birthYear: Int) -> Unit,
    onClickSex: (sex: String) -> Unit,
    birthYearState: Int?,
    isNextButtonEnabled: Boolean,
    radioOptions: List<String>
) {
    val scope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxHeight().background(color = Color.Gray),
                verticalArrangement = Arrangement.Bottom
            ) {
                YearPickerDialog(
                    yearList = (1950..2024).toList(),
                    initialValue = 2000,
                    height = 380.dp,
                    onDismiss = { scope.launch { modalSheetState.hide() } },
                    onDoneClick = {
                        onClickBirthYear(it)
                        scope.launch { modalSheetState.hide() }
                    })
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {
            Column {
                TopBar(
                    navIcon = painterResource(R.drawable.ic_back),
                    onNavClick = { onPickNicknameClick() },
                    title = "2/2"
                )
                Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                    Text(
                        "나에게 꼭 맞는\n향수 추천을 위한\n3초",
                        modifier = Modifier.padding(top = 60.dp),
                        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Medium)
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Text(
                        "출생연도와 성별을 설정하면\n나와 비슷한 사람들이 찾아보는 향수를 \n추천받을 수 있어요",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Thin)
                    )
                    Text(
                        "출생연도",
                        modifier = Modifier.padding(top = 30.dp),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = CustomColor.gray4)
                    )
                    Column {
                        Spinner(
                            width = 152.dp,
                            height = 46.dp,
                            value = birthYearState,
                            onClick = { scope.launch { modalSheetState.show() } },
                            placeholder = "선택"
                        )
                    }
                }
                Column(modifier = Modifier.padding(top = 25.dp).padding(start = 5.dp)) {
                    RadioButtonList(
                        radioOptions[0],
                        radioOptions, onButtonClick = { onClickSex(it) }
                    )
                }
            }
            Button(
                isNextButtonEnabled, "시작하기", { onFinishOnBoarding() },
                buttonModifier = Modifier.fillMaxWidth().height(80.dp)
            )
        }
    }
}


@Preview
@Composable
fun PickPersonalInfoScreenPreview() {
    PickPersonalInfoScreen({}, {}, {}, {}, 2000, true, listOf("1", "2"))
}

