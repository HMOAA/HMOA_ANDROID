package com.hmoa.core_designsystem.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
    onPressNicknameExist: (text: String) -> Unit,
    clearAvailable: () -> Unit = {},
    isAvailable: Boolean?,
) {
    var nickname by remember {mutableStateOf(initNickname ?: "")}
    val isEnabled by remember{derivedStateOf{ nickname != initNickname && nickname.isNotEmpty()}}
    var descriptionText by remember {mutableStateOf("닉네임 제한 캡션입니다")}
    var descriptionTextColor by remember { mutableStateOf(Color.Black) }
    var nicknameLength by remember { mutableStateOf(nickname.length.toString()) }

    LaunchedEffect(isAvailable) {
        descriptionText = handleText(isAvailable)
        descriptionTextColor = handleTextColor(isAvailable)
    }
    LaunchedEffect(nickname){ if(initNickname != nickname) clearAvailable() }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Column {
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
                                if (isLengthUnder9(it)) {
                                    nickname = it
                                    nicknameLength = it.length.toString()
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
                    HorizontalDivider(modifier = Modifier
                        .border(width = 1.dp, color = CustomColor.gray2)
                        .fillMaxWidth(0.75f))
                }
                Column(modifier = Modifier.padding(start = 8.dp), verticalArrangement = Arrangement.Center) {
                    Button(
                        isEnabled,
                        "중복확인",
                        {
                            onPressNicknameExist(nickname)
                        },
                        textSize = 14,
                        radious = 10,
                        buttonModifier = Modifier
                            .height(46.dp)
                            .fillMaxWidth(1f),
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

fun handleText(isAvailable: Boolean?): String {
    return if (isAvailable == null){
        "닉네임 제한 캡션입니다"
    } else {
        if (isAvailable) {
            "사용가능한 닉네임 입니다"
        } else {
            "사용할 수 없는 닉네임 입니다"
        }
    }
}

fun isLengthUnder9(text: String): Boolean {
    if (text.length < 9) return true
    return false
}

fun handleTextColor(isAvailable: Boolean?): Color {
    return if(isAvailable == null){
        Color.Black
    } else {
        if (isAvailable) {
            CustomColor.blue
        } else {
            CustomColor.red
        }
    }
}


@Preview
@Composable
fun NicknameInputPreview() {
    var nickname by remember{mutableStateOf("호준")}
    var baseNickname = "안드"
    var isDup by remember{mutableStateOf(false)}
    Log.d("TAG TEST", "isDup : ${isDup}")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ){
        NicknameInput(
            initNickname = nickname,
            onPressNicknameExist = {
                isDup = it==baseNickname
                if(isDup) {baseNickname = nickname}
            },
            isAvailable = !isDup
        )
    }
}
