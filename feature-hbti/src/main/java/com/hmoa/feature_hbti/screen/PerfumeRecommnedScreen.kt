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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Button

@Composable
fun PerfumeRecommendRoute(
    onNavBack: () -> Unit
){
    var curIdx = remember{mutableIntStateOf(0)}
    PerfumeRecommendScreen(
        onNavBack = onNavBack,
        curIdx = curIdx.intValue
    )
}

@Composable
fun PerfumeRecommendScreen(
    onNavBack : () -> Unit,
    curIdx : Int
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
                curIdx = curIdx
            )
            Button(
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                isEnabled = true,
                btnText = "다음",
                onClick = {}
            )
        }
    }
}

@Composable
private fun QuestionContent(
    modifier : Modifier,
    curIdx : Int
){
    Column(
        modifier = modifier
    ){
        when(curIdx) {
            0 -> SelectPriceScreen(
                value = ""
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun PreviewScreen(){
    PerfumeRecommendScreen(
        onNavBack = {},
        curIdx = 0
    )
}