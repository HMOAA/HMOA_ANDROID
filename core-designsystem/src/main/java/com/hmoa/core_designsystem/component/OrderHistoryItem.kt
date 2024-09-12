package com.hmoa.core_designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_common.formatWon
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont

@Composable
fun OrderHistoryItem(
    shippingType: String,
    noteUrl: String,
    noteName: String,
    notes: List<String>,
    noteCount: Int,
    price: Int,
    navCheckDeliveryStatus: () -> Unit,
    onRefundClick: () -> Unit,
    onReturnClick: () -> Unit,
){
    val notes = notes.joinToString {it}
    val totalPrice = noteCount * price
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = shippingType,
                fontSize = 12.sp,
                fontFamily = CustomFont.bold,
                color = if (shippingType == "배송 완료") CustomColor.blue else Color.Black
            )
            Spacer(Modifier.width(24.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = CustomColor.gray1)
        }
        Spacer(Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth()){
            CircleImageView(
                imgUrl = noteUrl,
                width = 60,
                height = 60
            )
            Spacer(Modifier.width(20.dp))
            Column(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = noteName,
                    fontSize = 14.sp,
                    fontFamily = CustomFont.semiBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = notes,
                    fontSize = 10.sp,
                    fontFamily = CustomFont.medium
                )
                Spacer(Modifier.height(38.dp))
                Text(
                    text = "수량 ${noteCount}개 ${formatWon(price)}원/개",
                    fontSize = 10.sp,
                    fontFamily = CustomFont.medium,
                    color = CustomColor.gray3
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "${formatWon(totalPrice)}원",
                    fontSize = 14.sp,
                    fontFamily = CustomFont.semiBold,
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(3.dp),
                        onClick = { /*TODO*/ },
                        border = BorderStroke(width = 1.dp, color = CustomColor.gray3),
                        contentPadding = PaddingValues(vertical = 10.dp),
                    ) {
                        Text(
                            text = "배송 조회",
                            fontSize = 12.sp,
                            color = CustomColor.gray3,
                            fontFamily = CustomFont.semiBold
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(3.dp),
                        onClick = { /*TODO*/ },
                        border = BorderStroke(width = 1.dp, color = CustomColor.gray3),
                        contentPadding = PaddingValues(vertical = 10.dp),
                    ) {
                        Text(
                            text = if(shippingType == "결제 완료") "환불 신청" else "반품 신청", //배송 상태에 대한 Enum class (결제 완료, 배송 중, 배송 완료)
                            fontSize = 12.sp,
                            color = CustomColor.gray3,
                            fontFamily = CustomFont.semiBold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun OrderHistoryItemUiTest(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        OrderHistoryItem(
            shippingType = "배송 중",
            noteUrl = "",
            noteName = "시트러스",
            notes = listOf("라임 만다린", "베르가못", "비터 오렌지", "자몽"),
            noteCount = 4,
            price = 1200,
            navCheckDeliveryStatus = {},
            onRefundClick = {},
            onReturnClick = {}
        )
        OrderHistoryItem(
            shippingType = "배송 완료",
            noteUrl = "",
            noteName = "플로럴",
            notes = listOf("네롤리", "화이트 로즈", "링크 로즈", "화이트로즈", "바이올렛", "피오니"),
            noteCount = 6,
            price = 1200,
            navCheckDeliveryStatus = {},
            onRefundClick = {},
            onReturnClick = {}
        )
    }
}