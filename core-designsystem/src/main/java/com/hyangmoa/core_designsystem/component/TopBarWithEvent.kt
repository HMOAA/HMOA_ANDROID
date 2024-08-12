package com.hyangmoa.core_designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopBarWithEvent(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "취소",
            fontSize = 16.sp,
            style = TextStyle(fontWeight = FontWeight.Normal),
            modifier = Modifier.clickable { onCancelClick() }
        )
        Text(
            text = title,
            fontSize = 20.sp,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
        Text(
            text = "확인",
            fontSize = 16.sp,
            style = TextStyle(fontWeight = FontWeight.Normal),
            modifier = Modifier.clickable { onConfirmClick() }
        )
    }
}

@Preview
@Composable
fun TopBarWithEventPreview(){
    TopBarWithEvent(
        onCancelClick = { },
        onConfirmClick = { },
        title = "댓글"
    )
}