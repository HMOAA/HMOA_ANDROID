package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun SurveyOptionItem(text: String, onClick: () -> Unit, isSelected: Boolean) {
    val isSelected = remember { mutableStateOf(isSelected) }
    Row(
        modifier = Modifier.fillMaxWidth(1f)
            .background(
                color = if (isSelected.value) CustomColor.gray5 else CustomColor.gray11,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 16.dp, vertical = 19.dp)
            .clickable {
                isSelected.value = !isSelected.value
                onClick()
            },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Image(
            painter = if (isSelected.value) painterResource(com.hmoa.core_designsystem.R.drawable.ic_check) else painterResource(
                com.hmoa.core_designsystem.R.drawable.ic_check_empty
            ), contentDescription = null, modifier = Modifier.padding(end = 15.dp)
        )
        Text(
            text = text,
            style = TextStyle(fontSize = 14.sp, color = if (isSelected.value) Color.White else CustomColor.gray5)
        )
    }
}

@Preview
@Composable
fun SurveyOptionItemPreview() {
    val seasons = listOf("싱그럽고 활기찬 '봄'", "화창하고 에너지 넘치는 '여름'", "우아하고 고요한 분위기의 '가을'", "차가움과 아늑함이 공존하는 '겨울'")
    val selectedStates0 = remember { mutableStateOf(false) }
    val selectedStates1 = remember { mutableStateOf(false) }

    SurveyOptionItem(
        text = seasons[0],
        onClick = {
            selectedStates1.value = false
        },
        isSelected = selectedStates0.value
    )
    SurveyOptionItem(
        text = seasons[1],
        onClick = {
            selectedStates0.value = false
        },
        isSelected = selectedStates1.value
    )
}