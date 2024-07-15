package com.hmoa.feature_hbti.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.SurveyOptionList
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationViewModel

@Composable
fun PerfumeRecommendationRoute(
    onNavNext: () -> Unit,
    onNavBack: () -> Unit,
    viewModel : PerfumeRecommendationViewModel = hiltViewModel()
){
    val selectedOption = viewModel.selectedOption.collectAsStateWithLifecycle()
    val isEnabledBtn = viewModel.isEnabledBtn.collectAsStateWithLifecycle(initialValue = false)
    val surveyOptions = listOf("5만원 이하", "5만원 이상", "10만원 이상", "가격 상관 없음")
    val context = LocalContext.current

    PerfumeRecommendationScreen(
        selectedOption = selectedOption.value,
        onChangedOption = { viewModel.changeOption(it) },
        surveyOptions = surveyOptions,
        isEnabledBtn = isEnabledBtn.value,
        onClickNext = {
            if(selectedOption.value != null && isEnabledBtn.value){
                viewModel.postSurveyResult()
                onNavNext()
            } else {
                Toast.makeText(context, "선택지를 골라주세요", Toast.LENGTH_SHORT).show()
            }
        },
        onNavBack = onNavBack
    )
}

@Composable
fun PerfumeRecommendationScreen(
    selectedOption : String?,
    onChangedOption : (String?) -> Unit,
    surveyOptions: List<String>,
    isEnabledBtn : Boolean,
    onClickNext : () -> Unit,
    onNavBack : () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(bottom = 40.dp)
    ){
        TopBar(
            title = "향BTI",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){
            Spacer(Modifier.height(15.dp))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black, shape = RoundedCornerShape(size = 100.dp)),
                thickness = 5.dp,
                color = Color.Black
            )
            Spacer(Modifier.height(40.dp))
            SelectPriceScreen(
                modifier = Modifier.weight(1f),
                surveyOptions = surveyOptions,
                value = selectedOption,
                onChangedValue = onChangedOption
            )
            Button(
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                isEnabled = isEnabledBtn,
                btnText = "다음",
                onClick = onClickNext
            )
        }
    }
}
@Composable
private fun SelectPriceScreen(
    modifier : Modifier,
    surveyOptions: List<String>,
    value : String?,
    onChangedValue : (value : String?) -> Unit,
){
    Column(
        modifier = modifier
    ){
        Text(
            text = "Q. 원하시는 가격대를 설정해주세요",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_semi_bold))
        )
        Spacer(Modifier.height(32.dp))
        SurveyOptionList(
            surveyOptions = surveyOptions,
            initValue = value,
            onButtonClick = onChangedValue
        )
    }
}


@Preview
@Composable
private fun PreviewScreen(){
    var option : String? by remember{mutableStateOf("5만원 이하")}
    val surveyOptions = listOf("5만원 이하", "5만원 이상", "10만원 이상", "가격 상관 없음")

    PerfumeRecommendationScreen(
        selectedOption = option,
        onChangedOption = {option = it},
        surveyOptions = surveyOptions,
        isEnabledBtn = true,
        onNavBack = {},
        onClickNext = {}
    )
}