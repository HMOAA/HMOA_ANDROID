package com.hmoa.core_designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun NicknameInput(
    initNickname : String? = null,
    onChangeValue : (String) -> Unit = {},
    onPressNicknameExist: (text: String) -> Unit,
    isAvailable: Boolean,
    isEnabled : Boolean = true,
) {
    var nickname by remember {
        mutableStateOf(initNickname ?: "")
    }
    var descriptionText by remember { mutableStateOf("닉네임 제한 캡션입니다") }
    var descriptionTextColor by remember { mutableStateOf(Color.Black) }
    var nicknameLength by remember { mutableStateOf(nickname.length.toString()) }

    LaunchedEffect(isAvailable) {
        descriptionText = handleText(isAvailable)
        descriptionTextColor = handleTextColor(isAvailable)
    }

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
                            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Light, fontFamily = CustomFont.regular),
                            value = nickname,
                            onValueChange = {
                                if (isLenthUnder9(it)) {
                                    nickname = it
                                    nicknameLength = it.length.toString()
                                    onChangeValue(it)
                                }
                            },
                            placeholder = {
                                Text(
                                    "닉네임을 입력하세요",
                                    style = TextStyle(
                                        color = CustomColor.gray3,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = pretendard
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
                Column(modifier = Modifier.padding(start = 8.dp), verticalArrangement = Arrangement.Center) {
                    Button(
                        isEnabled,
                        "중복확인",
                        { onPressNicknameExist(nickname) },
                        textSize = 14,
                        radious = 10,
                        buttonModifier = Modifier.height(46.dp).fillMaxWidth(1f),
                    )
                }
            }
            Row(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    descriptionText,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(color = descriptionTextColor, fontSize = 14.sp, fontWeight = FontWeight.Normal,
                        fontFamily = pretendard)
                )
            }
        }
    }
}

fun handleText(isAvailable: Boolean): String {
    if (isAvailable) {
        return "사용가능한 닉네임 입니다"
    }
    return "닉네임 제한 캡션입니다"
}

fun isLenthUnder9(text: String): Boolean {
    if (text.length < 9) return true
    return false
}

fun handleTextColor(isAvailable: Boolean): Color {
    if (isAvailable) {
        return CustomColor.blue
    }
    return Color.Black
}


@Preview
@Composable
fun NicknameInputPreview() {
    NicknameInput(null,{}, {}, true)
}
