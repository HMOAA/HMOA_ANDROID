package com.hmoa.feature_userinfo.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.component.TopBar
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.OrderRecordItem
import com.hmoa.core_model.response.OrderRecordDto
import com.hmoa.feature_userinfo.viewModel.OrderRecordUiState
import com.hmoa.feature_userinfo.viewModel.OrderRecordViewModel

// 주문 내역 화면
@Composable
fun OrderRecordRoute(
    navBack: () -> Unit,
    navReturnOrRefund: (pageType: String, orderId: Int) -> Unit,
    viewModel: OrderRecordViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    OrderRecordScreen(
        uiState = uiState.value,
        errState = errState.value,
        navBack = navBack,
        navReturnOrRefund = navReturnOrRefund
    )
}

@Composable
fun OrderRecordScreen(
    uiState: OrderRecordUiState,
    errState: ErrorUiState,
    navBack: () -> Unit,
    navReturnOrRefund: (pageType: String, orderId: Int) -> Unit
){
    var isOpen by remember{mutableStateOf(true)}
    when(uiState){
        OrderRecordUiState.Loading -> AppLoadingScreen()
        OrderRecordUiState.Error -> {
            ErrorUiSetView(
                isOpen = isOpen,
                onConfirmClick = {
                    isOpen = false
                    navBack()
                },
                errorUiState = errState,
                onCloseClick = {
                    isOpen = false
                    navBack()
                }
            )
        }
        is OrderRecordUiState.Success -> {
            OrderRecordContent(
                data = uiState.orderRecords.collectAsLazyPagingItems().itemSnapshotList,
                navBack = navBack,
                navReturnOrRefund = navReturnOrRefund
            )
        }
    }
}

@Composable
fun OrderRecordContent(
    data: ItemSnapshotList<OrderRecordDto>,
    navBack: () -> Unit,
    navReturnOrRefund: (pageType: String, orderId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        TopBar(
            title = "주문 내역",
            navIcon = painterResource(R.drawable.ic_back),
            onNavClick = navBack
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(data) { order ->
                if (order != null){
                    OrderRecordItem(
                        shippingType = order.orderStatus,
                        courierCompany = order.courierCompany,
                        products = order.orderProducts.productInfo.noteProducts,
                        totalPrice = order.orderProducts.totalAmount,
                        trackingNumber = order.trackingNumber,
                        onRefundClick = { navReturnOrRefund("refund", order.orderId) },
                        onReturnClick = { navReturnOrRefund("return", order.orderId) }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun OrderRecordUITest(){
    OrderRecordScreen(
        uiState = OrderRecordUiState.Loading,
        errState = ErrorUiState.Loading,
        navBack = {},
        navReturnOrRefund = { a, b ->

        }
    )
}