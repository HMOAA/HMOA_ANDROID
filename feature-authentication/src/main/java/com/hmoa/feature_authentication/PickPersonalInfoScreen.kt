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

@Composable
internal fun PickPersonalInfoRoute(
    onHomeClick: () -> Unit,
    onPickNicknameClick: () -> Unit,
) {
    PickPersonalInfoScreen(onHomeClick,onPickNicknameClick)
}

@Composable
fun PickPersonalInfoScreen(onHomeClick: () -> Unit, onPickNicknameClick: () -> Unit,) {
    var text by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        Column {
            TopBar(
                navIcon = painterResource(R.drawable.ic_back),
                onNavClick = {onPickNicknameClick()},
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
            }
        }
        Button(true, "시작하기", { onHomeClick() }, Modifier.fillMaxWidth().height(80.dp))
    }
}


@Preview
@Composable
fun PickPersonalInfoScreenPreview() {
    PickPersonalInfoScreen({},{})
}

