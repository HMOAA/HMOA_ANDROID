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
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.NicknameInput
import com.hmoa.core_designsystem.theme.CustomColor


@Composable
internal fun PickNicknameRoute(
    onPickPersonalInfoClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    PickNicknameScreen(onPickPersonalInfoClick, onSignupClick, isAvailableNickname = true)
}

@Composable
fun PickNicknameScreen(onPickPersonalInfoClick: () -> Unit, onSignupClick: () -> Unit, isAvailableNickname: Boolean) {
    var isAvailableNickname by remember { mutableStateOf(isAvailableNickname) }

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
                NicknameInput({}, isAvailable = isAvailableNickname)
            }
        }
        Button(isAvailableNickname, "다음", { onPickPersonalInfoClick() }, Modifier.fillMaxWidth().height(80.dp))
    }
}

@Preview
@Composable
fun PickNicknameScreenPreview() {
    PickNicknameScreen({}, {}, true)
}