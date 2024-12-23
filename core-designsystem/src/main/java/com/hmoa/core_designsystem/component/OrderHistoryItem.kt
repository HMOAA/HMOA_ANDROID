package com.hmoa.core_designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_common.formatWon
import com.hmoa.core_common.toDisplayString
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.data.OrderStatus
import com.hmoa.core_model.response.Note
import com.hmoa.core_model.response.NoteProduct

@Composable
fun OrderRecordItem(
    courierCompany: String?,
    shippingType: OrderStatus,
    products: List<NoteProduct>,
    totalPrice: Int,
    shippingPayment: Int,
    trackingNumber: String?,
    createdAt: String,
    isReviewed: Boolean,
    onRefundClick: () -> Unit,
    onReturnClick: () -> Unit,
    onReviewWriteClick: () -> Unit,
){
    val shippingStatus = shippingType.toDisplayString()
    val shippingStatusColor = if(shippingType == OrderStatus.SHIPPING_COMPLETE) CustomColor.blue else Color.Black
    val buttonText = if(shippingType == OrderStatus.SHIPPING_PROGRESS || shippingType == OrderStatus.SHIPPING_COMPLETE) "반품 신청" else "환불 신청"
    val buttonEvent = if(shippingType == OrderStatus.PURCHASE_CONFIRMATION) onReturnClick else onRefundClick
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
                text = shippingStatus,
                fontSize = 12.sp,
                fontFamily = CustomFont.bold,
                color = shippingStatusColor
            )
            Spacer(Modifier.width(12.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color.Black)
            Spacer(Modifier.width(12.dp))
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = createdAt,
                fontSize = 12.sp,
                fontFamily = CustomFont.bold,
                color = Color.Black
            )
        }
        Spacer(Modifier.height(20.dp))
        products.forEach{
            ProductView(it)
            Spacer(Modifier.height(30.dp))
        }
        if (courierCompany != null && trackingNumber != null){
            Text(
                modifier = Modifier.padding(start = 80.dp),
                text = "택배사 : ${courierCompany}\n운송장 번호 : ${trackingNumber}",
                textAlign = TextAlign.Justify,
                style = TextStyle(
                    fontSize = 10.sp,
                    color = CustomColor.gray3,
                    fontFamily = CustomFont.regular,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.height(24.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "배송비",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Spacer(Modifier.width(7.dp))
            Text(
                text = "${formatWon(shippingPayment)}원",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
        }
        Spacer(Modifier.height(24.dp))
        HorizontalDivider(color = CustomColor.gray1, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "결제금액",
                fontSize = 18.sp,
                fontFamily = CustomFont.bold,
            )
            Text(
                text = "${formatWon(totalPrice)}원",
                fontSize = 18.sp,
                fontFamily = CustomFont.bold,
                color = CustomColor.red
            )
        }
        Spacer(Modifier.height(32.dp))
        Buttons(
            isReviewed = isReviewed,
            shippingStatus = shippingType,
            buttonText = buttonText,
            buttonEvent = buttonEvent,
            onReviewWriteClick = onReviewWriteClick
        )

    }
}

@Composable
fun ProductView(
    product: NoteProduct
){
    val notes = product.notes.joinToString {it.noteName}
    val price = product.price / product.notesCount

    Row(modifier = Modifier.fillMaxWidth()){
        CircleImageView(
            imgUrl = product.productPhotoUrl,
            width = 60,
            height = 60
        )
        Spacer(Modifier.width(20.dp))
        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = product.productName,
                fontSize = 14.sp,
                fontFamily = CustomFont.semiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = notes,
                fontSize = 10.sp,
                fontFamily = CustomFont.medium
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "수량 ${product.notesCount}개 ${formatWon(price)}원/개",
                fontSize = 10.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "${formatWon(product.price)}원",
                fontSize = 14.sp,
                fontFamily = CustomFont.semiBold,
            )
        }
    }
}

@Composable
private fun Buttons(
    isReviewed: Boolean,
    shippingStatus: OrderStatus,
    buttonText: String,
    buttonEvent: () -> Unit,
    onReviewWriteClick: () -> Unit,
){
    val twoButtonStatusList = listOf(
        OrderStatus.SHIPPING_PROGRESS,
        OrderStatus.SHIPPING_COMPLETE,
        OrderStatus.RETURN_COMPLETE,
        OrderStatus.RETURN_PROGRESS
    )
    if (shippingStatus in twoButtonStatusList){
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            val buttonEnabled = shippingStatus == OrderStatus.SHIPPING_PROGRESS
                    || shippingStatus == OrderStatus.SHIPPING_COMPLETE
            OutlinedButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(3.dp),
                onClick = buttonEvent,
                enabled = buttonEnabled,
                border = BorderStroke(width = 1.dp, color = if(buttonEnabled) Color.Black else CustomColor.gray3),
                contentPadding = PaddingValues(vertical = 10.dp),
            ) {
                Text(
                    text = buttonText,
                    fontSize = 12.sp,
                    color = if(buttonEnabled) Color.Black else CustomColor.gray3,
                    fontFamily = CustomFont.semiBold
                )
            }
            Spacer(Modifier.width(20.dp))
            Column(
                modifier = Modifier.weight(1f)
            ){
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(3.dp),
                    onClick = onReviewWriteClick,
                    enabled = shippingStatus != OrderStatus.SHIPPING_PROGRESS && (!isReviewed),
                    border = BorderStroke(width = 1.dp, color = if(!isReviewed) Color.Black else CustomColor.gray3),
                    contentPadding = PaddingValues(vertical = 10.dp),
                ) {
                    Text(
                        text = "후기 작성(이벤트 자동 응모)",
                        fontSize = 12.sp,
                        color = if(!isReviewed) Color.Black else CustomColor.gray3,
                        fontFamily = CustomFont.semiBold
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    text = "(5만원 상당 향수 증정 이벤트 응모)\n자세한 내용은 향모아 인스타그램에서 확인하세요.",
                    fontSize = 10.sp,
                    lineHeight = 12.sp,
                    fontFamily = CustomFont.regular,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        val buttonEnabled = shippingStatus == OrderStatus.PAY_COMPLETE
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(3.dp),
            onClick = buttonEvent,
            enabled = buttonEnabled,
            border = BorderStroke(width = 1.dp, color = if(buttonEnabled) Color.Black else CustomColor.gray3),
            contentPadding = PaddingValues(vertical = 10.dp),
        ) {
            Text(
                text = buttonText,
                fontSize = 12.sp,
                color = if(buttonEnabled) Color.Black else CustomColor.gray3,
                fontFamily = CustomFont.semiBold
            )
        }
    }
}

@Preview
@Composable
private fun OrderHistoryItemUiTest(){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
            .verticalScroll(state = scrollState)
    ){
        OrderRecordItem(
            courierCompany = "대한통운(CJ)",
            trackingNumber = "365861396573",
            shippingType = OrderStatus.PAY_FAILED,
            products = listOf(
                NoteProduct(
                    notes = listOf(
                        Note(noteName = "라임 만다란", noteContent = ""),
                        Note(noteName = "베르가못", noteContent = ""),
                        Note(noteName = "비터 오렌지", noteContent = ""),
                        Note(noteName = "자몽", noteContent = "")
                    ),
                    notesCount = 4,
                    price = 4800,
                    productId = 0,
                    productName = "시트러스",
                    productPhotoUrl = ""
                ),
                NoteProduct(
                    notes = listOf(
                        Note(noteName = "네롤리", noteContent = ""),
                        Note(noteName = "화이트 로즈", noteContent = ""),
                        Note(noteName = "핑크 로즈", noteContent = ""),
                        Note(noteName = "화이트 로즈", noteContent = ""),
                        Note(noteName = "바이올렛", noteContent = ""),
                        Note(noteName = "피오니", noteContent = "")
                    ),
                    notesCount = 6,
                    price = 7200,
                    productId = 0,
                    productName = "플로럴",
                    productPhotoUrl = ""
                )
            ),
            totalPrice = 15000,
            onRefundClick = {},
            onReturnClick = {},
            shippingPayment = 3000,
            onReviewWriteClick = {},
            createdAt = "2024/10/17",
            isReviewed = true
        )
        OrderRecordItem(
            courierCompany = "대한통운(CJ)",
            trackingNumber = "365861396573",
            shippingType = OrderStatus.PAY_COMPLETE,
            products = listOf(
                NoteProduct(
                    notes = listOf(
                        Note(noteName = "라임 만다란", noteContent = ""),
                        Note(noteName = "베르가못", noteContent = ""),
                        Note(noteName = "비터 오렌지", noteContent = ""),
                        Note(noteName = "자몽", noteContent = "")
                    ),
                    notesCount = 4,
                    price = 1200,
                    productId = 0,
                    productName = "시트러스",
                    productPhotoUrl = ""
                ),
                NoteProduct(
                    notes = listOf(
                        Note(noteName = "네롤리", noteContent = ""),
                        Note(noteName = "화이트 로즈", noteContent = ""),
                        Note(noteName = "핑크 로즈", noteContent = ""),
                        Note(noteName = "화이트 로즈", noteContent = ""),
                        Note(noteName = "바이올렛", noteContent = ""),
                        Note(noteName = "피오니", noteContent = "")
                    ),
                    notesCount = 6,
                    price = 1200,
                    productId = 0,
                    productName = "플로럴",
                    productPhotoUrl = ""
                )
            ),
            totalPrice = 15000,
            onRefundClick = {},
            onReturnClick = {},
            shippingPayment = 3000,
            onReviewWriteClick = {},
            createdAt = "2024/07/21",
            isReviewed = false
        )
    }
}