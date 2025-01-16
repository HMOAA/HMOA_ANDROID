package com.hmoa.feature_authentication.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.component.Button

@Composable
internal fun SignupRoute(
    onPickNicknameClick: () -> Unit,
) {
    SignupScreen(onPickNicknameClick)
}

@Composable
fun SignupScreen(
    onPickNicknameClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(top = 190.dp)
    ) {
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text("환영합니다", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Medium))
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Text("향모아를 시작하기 위해\n간단한 몇가지 정보만 입력해주세요", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Thin))
        }
        Button(true, "다음", { onPickNicknameClick() }, Modifier.fillMaxWidth().height(80.dp))
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    SignupScreen({})
}
