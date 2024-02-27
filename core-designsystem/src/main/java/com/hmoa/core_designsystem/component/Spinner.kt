package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R

@Composable
fun Spinner(
    width : Dp, //넓이
    height : Dp, //높이
    value: String, //선택된 값
    onClick : () -> Unit, //클릭 시 이벤트 (Dialog를 띄움)
){
    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .background(color = Color.Black)
    ){
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
        ){
            Text(
                text = value,
                fontSize = 16.sp,
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.btn_down),
                contentDescription = "Expand Button"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestSpinner(){

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.LightGray),
        verticalArrangement = Arrangement.Center
    ){
        var test by remember{mutableStateOf("")}
        Spinner(
            width = 152.dp,
            height = 48.dp,
            value = "2001",
            onClick = {
                test = "clicked"
            }
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = test
        )
    }
}