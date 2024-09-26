package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun HbtiRoute(onHbtiSurveyClick: () -> Unit, onAfterOrderClick: () -> Unit) {
    HbtiScreen(onHbtiSurveyClick = { onHbtiSurveyClick() }, onAfterOrderClick = { onAfterOrderClick() })
}

@Composable
fun HbtiScreen(onHbtiSurveyClick: () -> Unit, onAfterOrderClick: () -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.background(color = Color.Black).fillMaxSize().padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        TopBar(title = "향BTI", titleColor = Color.White)
        Text(
            "당신의 향BTI는 무엇일까요?",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = pretendard,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
        )
        Text(
            "검사를 통해 좋아하는 향료와\n향수까지 알아보세요!",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontFamily = pretendard,
                fontSize = 14.sp
            )
        )

        Row(modifier = Modifier.padding(top = 20.dp, bottom = 32.dp)) {
            Box(Modifier.padding(end = 15.dp).fillMaxWidth(0.5f).height(107.dp).clickable { onHbtiSurveyClick() }
                .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp))) {
                ImageView(
                    imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/122bc5b1-1cc1-44b3-a468-1b56f9998994",
                    width = 1f,
                    height = 1f,
                    backgroundColor = Color.Transparent,
                    contentScale = ContentScale.FillBounds
                )
                Column(modifier = Modifier.fillMaxWidth(1f).height(107.dp)) {
                    Text(
                        "향BTI\n검사하러 가기",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = pretendard,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(1f).height(107.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp))
                    .clickable { onAfterOrderClick() }
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp))
            ) {
                ImageView(
                    imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/4bb30703-d77d-49ac-8a01-2aee48bf04c3",
                    width = 1f,
                    height = 1f,
                    backgroundColor = Color.Transparent,
                    contentScale = ContentScale.FillBounds
                )
                Column(modifier = Modifier.fillMaxWidth(1f).height(107.dp)) {
                    Text(
                        "향료 입력하기\n(주문 후)",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = pretendard,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp).height(30.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp))
        ) {
            ImageView(
                imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/eb5499d5-25e4-4141-af66-353daa76f2a2",
                width = 0.1f,
                height = 1f,
                backgroundColor = Color.Transparent,
                contentScale = ContentScale.FillHeight
            )
            Text(
                "향BTI란?",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard,
                    fontSize = 20.sp
                )
            )
        }
        Text(
            "공감되는 상황을 통해 알아보는 기능",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontFamily = pretendard,
                fontSize = 14.sp
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(1f).padding(bottom = 20.dp)
                .background(color = CustomColor.gray5, shape = RoundedCornerShape(5.dp))
        ) {
            Text(
                "\"엇?!\"저 향기 뭐지?",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(horizontal = 20.dp).padding(top = 20.dp).padding(bottom = 8.dp)
            )
            Text(
                "했던 경험 많이들 있으시지 않나요?\n보통 우리는 특정 향수를 선호하기도 하지만, 그 향수를 구성하고\n있는 '향료'에 이끌려 이런 현상을 경험하게 됩니다.",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontFamily = pretendard,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(1f).padding(bottom = 20.dp)
                .background(color = CustomColor.gray5, shape = RoundedCornerShape(5.dp))
        ) {
            Text(
                "하지만.. 이게 어떤 향이야?",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(horizontal = 20.dp).padding(top = 20.dp).padding(bottom = 8.dp)
            )
            Text(
                "향료들은 시더우드, 피오니, 베르가못과 같이 우리에게 친숙하지\n않은 단어들이 대부분입니다.\n향BTI는 향료들을 소비자에게 직접 배송해서 소비자가 선호하고\n원하는 향료를 찾아낼 수 있도록 도움을 제공합니다.",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontFamily = pretendard,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(1f).padding(bottom = 20.dp)
                .background(color = CustomColor.gray5, shape = RoundedCornerShape(5.dp))
        ) {
            Text(
                "그래서 이 향이 들어간 향수가 뭔데?",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(horizontal = 20.dp).padding(top = 20.dp).padding(bottom = 8.dp)
            )
            Text(
                "소비자가 선호하는 향료 데이터를 수집한 후, 그 향료가 들어간\n향수를 종합적으로 추천합니다.",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontFamily = pretendard,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp)
            )
        }
    }
}

@Preview
@Composable
fun HbtiScreenPreview() {
    HbtiScreen({}, {})
}
