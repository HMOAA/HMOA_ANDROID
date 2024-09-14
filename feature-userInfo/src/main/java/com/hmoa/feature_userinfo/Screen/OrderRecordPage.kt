package com.hmoa.feature_userinfo.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.OrderRecordItem

@Composable
fun OrderRecordRoute(){

}

@Composable
fun OrderRecordScreen(){
    val dummyData = listOf(0)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        TopBar(
            title = "주문 내역",
            navIcon = painterResource(R.drawable.ic_back),
            onNavClick = {/** 뒤로가기 */}
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(dummyData){
                OrderRecordItem(
                    shippingType = "배송 중",
                    noteUrl = "",
                    noteName = "시트러스",
                    notes = listOf("라임 만다린", "베르가못", "비터 오렌지", "자몽"),
                    noteCount = 4,
                    price = 1200,
                    navCheckDeliveryStatus = { /** 배송 조회 화면으로 navigation */},
                    onRefundClick = { /** 환불 신청 화면으로 navigation */},
                    onReturnClick = { /** 반품 신청 화면으로 navigation */}
                )
                OrderRecordItem(
                    shippingType = "배송 완료",
                    noteUrl = "",
                    noteName = "플로럴",
                    notes = listOf("네롤리", "화이트 로즈", "링크 로즈", "화이트로즈", "바이올렛", "피오니"),
                    noteCount = 6,
                    price = 1200,
                    navCheckDeliveryStatus = { /** 배송 조회 화면으로 navigation */},
                    onRefundClick = { /** 환불 신청 화면으로 navigation */},
                    onReturnClick = { /** 반품 신청 화면으로 navigation */}
                )
            }
        }
    }
}

@Composable
@Preview
private fun OrderRecordUITest(){
    OrderRecordScreen()
}