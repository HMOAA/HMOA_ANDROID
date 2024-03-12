package com.hmoa.feature_perfume

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.TypeButton
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun PerfumeRoute() {
    PerfumeScreen()
}

@Composable
fun PerfumeScreen() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().verticalScroll(scrollState)) {
        TopBar(
            title = "조말론 런던",
            iconSize = 25.dp,
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = {},
            menuIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_home),
            onMenuClick = {}
        )
        Column(modifier = Modifier.fillMaxWidth().heightIn(360.dp).background(color = CustomColor.gray2)) { }
        Column(modifier = Modifier.padding(16.dp)) {
            TypeButton(
                roundedCorner = 20.dp,
                type = "2310",
                fontColor = Color.Black,
                icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_heart),
                iconColor = Color.Black,
                fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
                selected = false,
                unSelectedColor = CustomColor.gray1
            )
            Text(
                "우드 세이지 앤 씨 솔트 코롱",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "Wood Sage & Sea Salt Cologne",
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(bottom = 22.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray2))
            Text(
                "₩218,000",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(top = 16.dp)
            )
            LazyRow(modifier = Modifier.padding(bottom = 48.dp)) {
                items(listOf(30, 50, 100)) { it ->
                    PerfumeVolumeView(it, CustomColor.gray3)
                }
            }
            BrandCard()
            Text(
                "테이스팅 노트",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(bottom = 8.dp).padding(top = 48.dp)
            )
            Text(
                "이 제품에 대해 평가해주세요",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(bottom = 8.dp).padding(top = 48.dp)
            )
            Text(
                "계절감",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                "성별",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                "연령대",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(verticalAlignment = Alignment.Bottom){
                Text(
                    "댓글",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(bottom = 4.dp).padding(top = 48.dp)
                )
                Text(
                    "+565",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(bottom = 14.dp).padding(top = 48.dp)
                )
            }
        }
    }
}

@Composable
fun PerfumeVolumeView(volume: Int, color: Color) {
    Column(modifier = Modifier.padding(top = 16.dp).padding(end = 10.dp)) {
        Icon(
            painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_perfume),
            contentDescription = "향수용량 아이콘",
            tint = color,
        )
        Text(
            text = "${volume}ml", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun BrandCard() {
    Row(modifier = Modifier.border(border = BorderStroke(width = 1.dp, color = CustomColor.gray3))) {
        Column(modifier = Modifier.width(68.dp).height(68.dp)) {
            Text("JO", color = Color.White, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium))
        }
        Column(
            modifier = Modifier.fillMaxWidth().height(68.dp).background(color = Color.Black),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "JO MALONE LONDON",
                color = Color.White,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
            )
            Text("조말론 런던", color = Color.White, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium))
        }
    }
}

@Preview
@Composable
fun PerfumeScreenPreview() {
    PerfumeScreen()
}