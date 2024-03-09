package com.hmoa.feature_authentication

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
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.NicknameInput
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_authentication.viewmodel.PickNicknameUiState
import com.hmoa.feature_authentication.viewmodel.PickNicknameViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
internal fun PickNicknameRoute(
    onPickPersonalInfoClick: () -> Unit,
    onSignupClick: () -> Unit,
    viewmodel: PickNicknameViewmodel = hiltViewModel()
) {
    val scope = CoroutineScope(Dispatchers.IO)
    val isAvailableState by viewmodel.isExistedNicknameState.collectAsStateWithLifecycle()

    PickNicknameScreen(
        onPickPersonalInfoClick,
        onSignupClick,
        isExistedNicknameState = isAvailableState,
        onCheckNicknameDuplication = {
            scope.launch {
                viewmodel.onNicknameChanged(it)
                viewmodel.saveNickname(it)
            }
        }
    )
}

@Composable
fun PickNicknameScreen(
    onPickPersonalInfoClick: () -> Unit,
    onSignupClick: () -> Unit,
    isExistedNicknameState: PickNicknameUiState = PickNicknameUiState.PickNickname(isExistedNickname = true),
    onCheckNicknameDuplication: (nickname: String) -> Unit
) {
    var isAvailableNickname by remember { mutableStateOf(false) }

    LaunchedEffect(isExistedNicknameState) {
        isAvailableNickname = handleNicknameInput(isExistedNicknameState)
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
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
                    modifier = Modifier.padding(top = 60.dp).padding(horizontal = 15.dp),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Thin, color = CustomColor.gray4)
                )
                NicknameInput(
                    { onCheckNicknameDuplication(it) },
                    isAvailable = isAvailableNickname
                )
            }
        }
        Button(
            isAvailableNickname,
            "다음",
            { onPickPersonalInfoClick() },
            Modifier.fillMaxWidth().height(80.dp)
        )
    }
}

fun handleNicknameInput(value: PickNicknameUiState): Boolean {
    if (value == PickNicknameUiState.PickNickname(isExistedNickname = true)) {
        return false
    }
    return true
}

@Preview
@Composable
fun PickNicknameScreenPreview() {
    PickNicknameScreen({}, {}, PickNicknameUiState.PickNickname(isExistedNickname = true), {})
}