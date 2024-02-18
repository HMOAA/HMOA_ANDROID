package com.hmoa.core_designsystem.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.addButtonModifier(buttonModifier: Modifier?):Modifier{
    if(buttonModifier != null){
        return this.then(buttonModifier)
    }
    else{
        return this
    }
}
@Composable
fun OAuthLoginButton(
    backgroundColor: Color,
    buttonModifier: Modifier? = null,
    iconId: Int,
    iconSize:Int,
    buttonText: String,
    textColor: Color,
    textSize:Int,
    onPress: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(5.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(size = 5.dp))
            .clickable {
                onPress()
            }.addButtonModifier(buttonModifier),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(start = screenWidth / 5))
        Icon(
            modifier = Modifier.size(iconSize.dp),
            painter = painterResource(iconId),
            contentDescription = "OAuth Type Icon",
        )

        Text(
            text = buttonText,
            fontSize = textSize.sp,
            color = textColor,
            modifier = Modifier.padding(start = 20.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun TestOAuthLoginBtn() {
    Box(
        modifier = Modifier.fillMaxHeight().background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        OAuthLoginButton(
            backgroundColor = CustomColor.kakao,
            iconId = R.drawable.ic_kakao,
            iconSize = 16,
            buttonText = "Kakaotalk으로 로그인",
            textColor = Color.Black,
            textSize = 16,
            onPress = {

            },

        )
    }
}