package com.hmoa.feature_userinfo.screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.formatWon
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.NoteListItem
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.FinalOrderResponseDto
import com.hmoa.feature_userinfo.BuildConfig
import com.hmoa.feature_userinfo.viewModel.RefundUiState
import com.hmoa.feature_userinfo.viewModel.RefundViewModel
import com.kakao.sdk.talk.TalkApiClient

//반품 & 환불 화면
@Composable
fun RefundRoute(
    type: String?,
    orderId: Int?,
    navBack: () -> Unit,
    viewModel: RefundViewModel = hiltViewModel()
){
    viewModel.setId(orderId)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val isDone = viewModel.isDone.collectAsStateWithLifecycle()
    ReturnOrRefundScreen(
        uiState = uiState.value,
        errState = errState.value,
        type = type!!,
        doRefund = {viewModel.refundOrder()},
        navBack = navBack
    )
    LaunchedEffect(isDone.value){
        if(isDone.value) navBack()
    }
}

@Composable
fun ReturnOrRefundScreen(
    uiState: RefundUiState,
    errState: ErrorUiState,
    type: String,
    doRefund: () -> Unit,
    navBack: () -> Unit,
){
    var isOpen by remember{mutableStateOf(false)}
    when(uiState){
        RefundUiState.Loading -> AppLoadingScreen()
        is RefundUiState.Success -> {
            RefundContent(
                type = type,
                data = uiState.data,
                doRefund = doRefund,
                navBack = navBack
            )
        }
        RefundUiState.Error -> {
            ErrorUiSetView(
                isOpen = isOpen,
                onConfirmClick = {
                    isOpen = false
                },
                errorUiState = errState,
                onCloseClick = {
                    isOpen = false
                }
            )
        }
    }
}

@Composable
private fun RefundContent(
    type: String,
    data: FinalOrderResponseDto,
    doRefund: () -> Unit,
    navBack: () -> Unit,
){
    val title = if(type == "refund") "환불 신청" else "반품 신청"
    val buttonText = if(type == "refund") "환불 신청" else "반품 신청 (1대1 문의)"
    val context = LocalContext.current

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
            onNavClick = navBack
        )
        LazyColumn{
            item{Spacer(Modifier.height(20.dp))}
            items(data.productInfo.noteProducts){product ->
                NoteListItem(
                    noteUrl = product.productPhotoUrl,
                    productName = product.productName,
                    notes = product.notes,
                    noteCounts = product.notesCount,
                    totalPrice = product.price
                )
                Spacer(Modifier.height(24.dp))
            }
            item{
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
                        text = "${formatWon(data.totalAmount)}원",
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
                            text = "${formatWon(data.paymentAmount)}원",
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
                            text = "${formatWon(data.shippingAmount)}원",
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
                        text = "${formatWon(data.totalAmount)}원",
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
            onClick = {
                if(type == "refund") doRefund()
                else TalkApiClient.instance.chatChannel(context, BuildConfig.KAKAO_CHAT_PROFILE) { err ->
                    if (err != null) {
                        Toast.makeText(context, "향모아 챗봇 오류가 발생했습니다:(", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun ReturnOrRefundUITest(){
    ReturnOrRefundScreen(
        type = "refund",
        uiState = RefundUiState.Loading,
        errState = ErrorUiState.Loading,
        doRefund = {},
        navBack = {}
    )
}