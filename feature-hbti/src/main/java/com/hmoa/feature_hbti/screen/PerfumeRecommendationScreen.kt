package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Button

@Composable
fun PerfumeRecommendationRoute(
    onNavBack: () -> Unit
){
    var curIdx by remember{mutableIntStateOf(0)}
    val values = mutableMapOf<Int, String>()
    var isEnabledBtn by remember{mutableStateOf(values[curIdx] != null)}
    PerfumeRecommendationScreen(
        values = values,
        curIdx = curIdx,
        isEnabledBtn = isEnabledBtn,
        onNavBack = onNavBack,
        onNavNext = {curIdx = (curIdx + 1) % 2}
    )
}

@Composable
fun PerfumeRecommendationScreen(
    values : MutableMap<Int,String>,
    curIdx : Int,
    isEnabledBtn : Boolean,
    onNavBack : () -> Unit,
    onNavNext : () -> Unit,
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
            QuestionContent(
                modifier = Modifier.weight(1f),
                values = values,
                curIdx = curIdx
            )
            Button(
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                isEnabled = isEnabledBtn,
                btnText = "다음",
                onClick = onNavNext
            )
        }
    }
}

@Composable
private fun QuestionContent(
    modifier : Modifier,
    values : MutableMap<Int, String>,
    curIdx : Int
){
    Column(
        modifier = modifier
    ){
        when(curIdx) {
            0 -> SelectPriceScreen(
                value = values[0],
                onChangedValue = {values[0] = it}
            )
            1 -> SelectSpiceScreen()
            else -> throw IllegalArgumentException("EXCEPTION")
        }
    }
}

@Preview
@Composable
private fun PreviewScreen(){
    PerfumeRecommendationScreen(
        onNavBack = {},
        curIdx = 0,
        values = mutableMapOf<Int, String>(),
        isEnabledBtn = false,
        onNavNext = {}
    )
}