package com.hmoa.core_designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
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
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun NicknameInput(onPressNicknameExist: (text: String) -> Unit, isAvailable: Boolean) {
    var nickname by remember { mutableStateOf("") }
    var isAvailable by remember { mutableStateOf(isAvailable) }
    var nicknameLength by remember { mutableStateOf(nickname.length.toString()) }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                focusedIndicatorColor = Color.White,
                            ),
                            minLines = 1,
                            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Light),
                            value = nickname,
                            onValueChange = {
                                if(isLenthUnder9(it)){
                                    nickname = it
                                }
                            },
                            placeholder = {
                                Text(
                                    "닉네임을 입력하세요",
                                    style = TextStyle(
                                        color = CustomColor.gray3,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Light
                                    )
                                )
                            }
                        )
                        Row {
                            Text(
                                nicknameLength,
                                modifier = Modifier.padding(top = 8.dp),
                                style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Light)
                            )
                            Text(
                                "/8",
                                modifier = Modifier.padding(top = 8.dp),
                                style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Light)
                            )
                        }
                    }
                    Divider(modifier = Modifier.border(width = 1.dp, color = CustomColor.gray2).fillMaxWidth(0.75f))
                }
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Button(
                        true,
                        "중복확인",
                        {onPressNicknameExist(nickname)},
                        textSize = 14,
                        radious = 10,
                        buttonModifier = Modifier.height(46.dp).fillMaxWidth(1f)
                    )
                }
            }
            Row(modifier = Modifier.padding(start = 10.dp)) { DescriptionText(isAvailable) }
        }
    }
}

fun isLenthUnder9(text: String):Boolean {
    if (text.length < 9) return true
    return false
}


@Composable
fun DescriptionText(isAvailable: Boolean) {
    handleTextColor(isAvailable)
}
@Composable
fun handleTextColor(isAvailable: Boolean) {
    if(isAvailable){
        return Text(
            "사용가능한 닉네임 입니다",
            modifier = Modifier.padding(top = 8.dp),
            style = TextStyle(color = CustomColor.blue, fontSize = 14.sp, fontWeight = FontWeight.Light)
        )
    }
    return Text(
        "닉네임 제한 캡션입니다",
        modifier = Modifier.padding(top = 8.dp),
        style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Light)
    )
}


@Preview
@Composable
fun NicknameInputPreview() {
    NicknameInput({},true)
}
