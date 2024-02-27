package com.hmoa.feature_authentication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.theme.CustomColor


@Composable
internal fun PickNicknameRoute(
    onPickPersonalInfoClick: () -> Unit,
) {
    PickNicknameScreen(onPickPersonalInfoClick)
}

@Composable
fun PickNicknameScreen(onPickPersonalInfoClick: () -> Unit) {
    var text by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        Column {
            TopBar(
                navIcon = painterResource(R.drawable.ic_back),
                onNavClick = {},
                title = "1/2"
            )
            Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                Text(
                    "닉네임",
                    modifier = Modifier.padding(top = 60.dp),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Thin, color = CustomColor.gray4)
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier.height(46.dp).padding(end = 8.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = CustomColor.gray2,
                            focusedIndicatorColor = CustomColor.gray2
                        ),
                        value = text,
                        onValueChange = {},
                    )
                    Button(true, "중복확인", {}, Modifier.height(46.dp).width(80.dp), textSize = 14, radious = 5)
                }
                Text(
                    "사용할 수 있는 닉네임입니다",
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(color = CustomColor.blue, fontSize = 14.sp, fontWeight = FontWeight.Light)
                )
            }
        }
        Button(true, "다음", { onPickPersonalInfoClick }, Modifier.fillMaxWidth().height(80.dp))
    }
}

@Preview
@Composable
fun PickNicknameScreenPreview() {
    PickNicknameScreen({})
}