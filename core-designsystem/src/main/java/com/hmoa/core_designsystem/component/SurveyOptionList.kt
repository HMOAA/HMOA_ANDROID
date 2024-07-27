package com.hmoa.core_designsystem.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun SurveyOptionList(
    initValue: String? = null,
    surveyOptions: List<String>,
    onButtonClick: (optionIndex: Int) -> Unit
) {
    val surveyOptions = surveyOptions
    val (selectedOption, onOptionSelected) = remember {
        val idx = if (initValue == null) 0 else surveyOptions.indexOf(initValue)
        mutableStateOf(surveyOptions[idx])
    }
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxHeight(0.7f).verticalScroll(scrollState).background(color =Color.White)
    ) {
        surveyOptions.forEachIndexed { index, it ->
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                SurveyOptionItem(
                    text = it,
                    onClick = {
                        onOptionSelected(it)
                        onButtonClick(index)
                    },
                    isSelected = (it == selectedOption)
                )
            }
        }
    }
}

@Composable
fun SurveyOptionItem(text: String, onClick: () -> Unit, isSelected: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(1f)
            .background(
                color = if (isSelected) CustomColor.gray5 else CustomColor.gray11,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 16.dp, vertical = 19.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Image(
            painter = if (isSelected) painterResource(com.hmoa.core_designsystem.R.drawable.ic_check) else painterResource(
                com.hmoa.core_designsystem.R.drawable.ic_check_empty
            ), contentDescription = null, modifier = Modifier.padding(end = 15.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else CustomColor.gray5
            )
        )
    }
}

@Preview
@Composable
fun SurveyOptionItemPreview() {
    val seasons = listOf("싱그럽고 활기찬 '봄'", "화창하고 에너지 넘치는 '여름'", "우아하고 고요한 분위기의 '가을'", "차가움과 아늑함이 공존하는 '겨울'")
    Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight(1f).background(color = Color.Yellow)) {
        SurveyOptionList(null, seasons, {})
        Button(
            isEnabled = true,
            btnText = "다음",
            onClick = {},
            buttonModifier = Modifier.fillMaxWidth(1f).height(52.dp).background(color = Color.Black),
            textSize = 18,
            textColor = Color.White,
            radious = 5
        )
    }
}