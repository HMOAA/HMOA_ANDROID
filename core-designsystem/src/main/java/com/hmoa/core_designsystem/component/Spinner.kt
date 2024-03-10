package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun Spinner(
    width: Dp, //넓이
    height: Dp, //높이
    value: Int?, //선택된 값
    onClick: () -> Unit, //클릭 시 이벤트 (Dialog를 띄움)
    placeholder: String
) {

    fun handlePlaceHolder(value: Int?, placeholder: String): String {
        if (value == null) {
            return placeholder
        }
        return value.toString()
    }

    fun handleColor(): Color {
        if (value == null) {
            return CustomColor.gray3
        }
        return Color.Black
    }

    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .background(color = handleColor())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height - 1.dp)
                .background(color = Color.White)
                .clickable {
                    onClick()
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = handlePlaceHolder(value, placeholder),
                fontSize = 16.sp,
                color = handleColor()
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.btn_down),
                contentDescription = "Expand Button",
                tint = handleColor()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestSpinner() {

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.LightGray),
        verticalArrangement = Arrangement.Center
    ) {
        var test by remember { mutableStateOf("") }
        Spinner(
            width = 152.dp,
            height = 48.dp,
            value = null,
            onClick = {
                test = "clicked"
            },
            placeholder = "선택"
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = test
        )
    }
}