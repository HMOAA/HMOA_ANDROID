package com.hmoa.feature_authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hmoa.core_designsystem.component.BottomButton
import com.hmoa.core_designsystem.theme.CustomColor


@Composable
fun SignupScreen(navController: NavController) {
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
        BottomButton(true, "다음", {})
    }
}

@Composable
fun SignUpNicknameScreen() {
    var text by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(top = 190.dp)
    ) {
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text("닉네임", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Thin, color = CustomColor.gray4))
            TextField(
                modifier = Modifier.fillMaxWidth().height(46.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = CustomColor.gray2,
                    focusedIndicatorColor = CustomColor.gray2
                ),
                value = text,
                onValueChange = {},
            )
            Button(content = {Text(text="중복확인")}, onClick = {}, modifier = Modifier.background(color = Color.Black))
        }
        BottomButton(true, "다음", {})
    }
}


@Preview
@Composable
fun SignupScreenPreview() {
    SignUpNicknameScreen()
}