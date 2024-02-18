package com.hmoa.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.OAuthLoginButton
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun LoginScreen(){
    Column (
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,) {
            Icon(modifier = Modifier.fillMaxWidth().padding(top = 160.dp),
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_hmoa_logo),
                contentDescription = "OAuth Type Icon",)
            Spacer(modifier = Modifier.padding(top=100.dp))
            OAuthLoginButton(
                backgroundColor = Color.White,
                iconId = R.mipmap.ic_google_foreground,
                iconSize = 40,
                buttonText = " Google로 로그인",
                textColor = Color.Black,
                textSize = 16,
                onPress = {

                },
            )
            Spacer(modifier = Modifier.padding(top=15.dp))
            OAuthLoginButton(
                backgroundColor = CustomColor.kakao,
                iconId = R.drawable.ic_kakao,
                iconSize = 13,
                buttonModifier = Modifier.padding(start = 15.dp),
                buttonText = "Kakaotalk으로 로그인",
                textColor = Color.Black,
                textSize = 16,
                onPress = {

                },
            )
        }
        ClickableText(text = AnnotatedString("로그인없이 사용하기"),
            onClick = {},
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 12.sp, color=CustomColor.gray4)
        )

    }
}

@Preview
@Composable
fun PreviewLoginScreen(){
    LoginScreen()
}