package com.hmoa.feature_userinfo.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_common.formatWon
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.NoteListItem
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.feature_userinfo.BuildConfig
import com.kakao.sdk.talk.TalkApiClient

@Composable
fun ReturnOrRefundRoute(
    type: String
){
    val context = LocalContext.current
    val onChatClick = {
        TalkApiClient.instance.chatChannel(context, BuildConfig.KAKAO_CHAT_PROFILE) { err ->
            if (err != null) {
                Toast.makeText(context, "향모아 챗봇 오류가 발생했습니다:(", Toast.LENGTH_LONG).show()
            }
        }
    }
    ReturnOrRefundScreen(
        type = type,
        onChatClick = onChatClick
    )
}

@Composable
fun ReturnOrRefundScreen(
    type: String,
    onChatClick: () -> Unit
){
    val title = if(type == "refund") "환불 신청" else "반품 신청"
    val buttonText = if(type == "refund") "환불 신청" else "반품 신청 (1대1 문의)"
    val buttonEvent = if(type == "refund") {/** 환불 신청 api */} else onChatClick()
    val dummyData = listOf(0)
    val totalPrice = 15000
    val totalOrderPrice = 12000
    val shippingPrice = 3000
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
            .padding(bottom = 40.dp)
    ){
        TopBar(
            title = title,
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = { /** 뒤로가기 navigation */ }
        )
        LazyColumn{
            item{Spacer(Modifier.height(20.dp))}
            items(dummyData){
                /** dummy data */
                NoteListItem(
                    noteUrl = "",
                    productName = "플로럴",
                    notes = listOf("네롤리", "화이트 로즈", "피읔 로즈", "화이트로즈", "바이올렛", "피오니"),
                    noteCounts = 6,
                    price = 1200
                )
            }
            item{
                Spacer(Modifier.height(24.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Black)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "결제금액",
                        fontSize = 18.sp,
                        fontFamily = CustomFont.bold
                    )
                    Text(
                        text = "${formatWon(totalPrice)}",
                        fontSize = 20.sp,
                        fontFamily = CustomFont.bold,
                        color = CustomColor.red
                    )
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = CustomColor.gray1)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "총 상품금액",
                            fontSize = 12.sp,
                            fontFamily = CustomFont.medium,
                            color = CustomColor.gray3
                        )
                        Text(
                            text = "${formatWon(totalOrderPrice)}원",
                            fontSize = 12.sp,
                            fontFamily = CustomFont.medium,
                            color = CustomColor.gray3
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "배송비",
                            fontSize = 12.sp,
                            fontFamily = CustomFont.medium,
                            color = CustomColor.gray3
                        )
                        Text(
                            text = "${formatWon(shippingPrice)}원",
                            fontSize = 12.sp,
                            fontFamily = CustomFont.medium,
                            color = CustomColor.gray3
                        )
                    }
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = CustomColor.gray1)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "총 환불금액",
                        fontSize = 12.sp,
                        fontFamily = CustomFont.semiBold
                    )
                    Text(
                        text = "${formatWon(totalPrice)}원",
                        fontSize = 12.sp,
                        fontFamily = CustomFont.semiBold
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Button(
            buttonModifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            textSize = 15,
            isEnabled = true,
            btnText = buttonText,
            radious = 5,
            onClick = { buttonEvent }
        )
    }
}

@Preview
@Composable
private fun ReturnOrRefundUITest(){
    ReturnOrRefundScreen(
        type = "refund",
        onChatClick = {}
    )
}