package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.component.SurveyOptionList

@Composable
fun SelectPriceScreen(
    value : String,
    onChangedValue : (value : String) -> Unit,
){
    val surveyOptions = listOf("5만원 이하", "5만원 이상", "10만원 이상", "가격 상관 없음")
    Column{
        Text(
            text = "Q. 원하시는 가격대를 설정해주세요",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_semi_bold))
        )
        Spacer(Modifier.height(32.dp))
        SurveyOptionList(
            surveyOptions = surveyOptions,
            initValue = null,
            onButtonClick = onChangedValue
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TestScreen(){
    Column(
        modifier = Modifier.padding(16.dp)
    ){
        var option = remember{mutableStateOf("가격 상관 없음")}
        SelectPriceScreen(
            value = option.value,
            onChangedValue = {option.value = it}
        )
    }
}