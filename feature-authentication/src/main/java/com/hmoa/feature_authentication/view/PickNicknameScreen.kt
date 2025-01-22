package com.hmoa.feature_authentication.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.NicknameInput
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_authentication.contract.PickNicknameEffect
import com.hmoa.feature_authentication.contract.PickNicknameEvent
import com.hmoa.feature_authentication.viewmodel.PickNicknameViewmodel
import kotlinx.coroutines.flow.collectLatest


@Composable
internal fun PickNicknameRoute(
    onPickPersonalInfoClick: () -> Unit,
    onSignupClick: () -> Unit,
    viewmodel: PickNicknameViewmodel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.effects.collectLatest { effect ->
            when (effect) {
                PickNicknameEffect.NavigateToPickPersonalInfo -> onPickPersonalInfoClick()
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column {
            TopBar(
                navIcon = painterResource(R.drawable.ic_back),
                onNavClick = { onSignupClick() },
                title = "1/2"
            )
            Column() {
                Text(
                    "닉네임",
                    modifier = Modifier
                        .padding(top = 60.dp)
                        .padding(horizontal = 15.dp),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Thin, color = CustomColor.gray4)
                )
                NicknameInput(
                    initNickname = "",
                    onPressNicknameExist = {
                        viewmodel.handleEvent(PickNicknameEvent.ClickCheckDuplicate(it))
                    },
                    isAvailable = uiState.isAvailableNickname
                )
            }
        }
        Button(
            uiState.isNextButtonEnabled,
            "다음",
            {
                viewmodel.handleEvent(PickNicknameEvent.NavigateToPickPersonalInfo)
            },
            Modifier
                .fillMaxWidth()
                .height(80.dp),
        )
    }
}
