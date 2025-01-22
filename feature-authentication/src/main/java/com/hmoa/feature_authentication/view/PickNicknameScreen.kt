package com.hmoa.feature_authentication.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.NicknameInput
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_authentication.viewmodel.PickNicknameUiState
import com.hmoa.feature_authentication.viewmodel.PickNicknameViewmodel
import kotlinx.coroutines.flow.MutableSharedFlow


@Composable
internal fun PickNicknameRoute(
    onPickPersonalInfoClick: () -> Unit,
    onSignupClick: () -> Unit,
    viewmodel: PickNicknameViewmodel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    PickNicknameScreen(
        onPickPersonalInfoClick,
        onSignupClick,
        uiState = uiState,
        onCheckNicknameDuplication = viewmodel::onNicknameChanged,
        onSaveNickname = viewmodel::saveNickname
    )
}

@Composable
fun PickNicknameScreen(
    onPickPersonalInfoClick: () -> Unit,
    onSignupClick: () -> Unit,
    uiState: PickNicknameUiState,
    onCheckNicknameDuplication: (nickname: String) -> Unit,
    onSaveNickname: (nickname: String) -> Unit,
) {
    when (uiState) {
        PickNicknameUiState.Loading -> {}
        PickNicknameUiState.Empty -> {}
        is PickNicknameUiState.PickNickname -> {
            val isAvailableNickname by uiState.isExistedNickname.collectAsStateWithLifecycle(initialValue = null)
            var nickname by remember { mutableStateOf(uiState.initNickname) }
            val isEnabled by remember { derivedStateOf { isAvailableNickname != null && isAvailableNickname!! && nickname == uiState.initNickname } }
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
                            initNickname = nickname,
                            onPressNicknameExist = {
                                onCheckNicknameDuplication(it)
                                nickname = it
                            },
                            isAvailable = isAvailableNickname
                        )
                    }
                }
                Button(
                    isEnabled,
                    "다음",
                    {
                        onSaveNickname(nickname)
                        onPickPersonalInfoClick()
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                )
            }

        }
    }


}

@Preview
@Composable
fun PickNicknameScreenPreview() {
    PickNicknameScreen({}, {}, PickNicknameUiState.PickNickname("", MutableSharedFlow()), {}, {})
}
