package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun AlarmItem(
    modifier : Modifier = Modifier,
    height : Dp,
    isRead : Boolean,
    category : String,
    content : String,
    time : String,
    profile : String? = null
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color = if(isRead) CustomColor.gray3 else Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(R.drawable.ic_fab),
                contentDescription = "App Default Icon"
            )
        }
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = category,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular))
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = content,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular))
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = time,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevAlarmItem(){
    AlarmItem(
        height= 94.dp,
        isRead = true,
        category = "Event",
        content = "지금 향모아만의 초특가 할인 상품을 만나보세요",
        time = "10/04 14:30"
    )
}