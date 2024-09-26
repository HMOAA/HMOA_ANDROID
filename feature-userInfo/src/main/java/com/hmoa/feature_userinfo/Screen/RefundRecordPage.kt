package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.toDisplayString
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.EmptyDataPage
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.NoteListItem
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.FinalOrderResponseDto
import com.hmoa.core_model.response.GetRefundRecordResponseDto
import com.hmoa.feature_userinfo.viewModel.RefundRecordUiState
import com.hmoa.feature_userinfo.viewModel.RefundRecordViewModel

//환불 / 반품 내역 화면
@Composable
fun RefundRecordRoute(
    navBack: () -> Unit,
    viewModel: RefundRecordViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    RefundRecordScreen(
        navBack = navBack,
        uiState = uiState.value,
        errState = errState.value
    )
}

@Composable
fun RefundRecordScreen(
    navBack: () -> Unit,
    uiState: RefundRecordUiState,
    errState: ErrorUiState
){
    var isOpen by remember{mutableStateOf(true)}
    when(uiState){
        RefundRecordUiState.Loading -> AppLoadingScreen()
        RefundRecordUiState.Error -> {
            ErrorUiSetView(
                isOpen = isOpen,
                onConfirmClick = navBack,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
        is RefundRecordUiState.Success -> {
            RefundRecordContent(
                data = uiState.data.collectAsLazyPagingItems().itemSnapshotList,
                navBack = navBack,
            )
        }
    }
}

@Composable
private fun RefundRecordContent(
    data: ItemSnapshotList<GetRefundRecordResponseDto>,
    navBack: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        TopBar(
            title = "환불 / 반품 내역",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = navBack
        )
        if (data.isNotEmpty()){
            LazyColumn{
                items(data){record ->
                    if (record != null){
                        ReturnOrRefundRecordItem(
                            status = record.orderStatus.toDisplayString(),
                            requestAt = record.createdAt,
                            notes = record.orderProducts
                        )
                    }
                }
            }
        } else {
            EmptyDataPage(
                mainText = "환불/반품 내역이 없습니다.",
                buttonText = "홈으로 돌아가기",
                onClick = navBack
            )
        }
    }
}

@Composable
private fun ReturnOrRefundRecordItem(
    status: String,
    requestAt: String,
    notes: FinalOrderResponseDto
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = status,
                fontSize = 12.sp,
                fontFamily = CustomFont.bold,
            )
            Spacer(Modifier.width(16.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), thickness = 1.dp, color = CustomColor.gray1)
            Spacer(Modifier.width(16.dp))
            Text(
                text = requestAt,
                fontSize = 12.sp,
                fontFamily = CustomFont.bold,
                color = CustomColor.gray3
            )
        }
        Spacer(Modifier.height(20.dp))
        notes.productInfo.noteProducts.forEach{ note ->
            NoteListItem(
                noteUrl = note.productPhotoUrl,
                productName = note.productName,
                notes = note.notes,
                noteCounts = note.notesCount,
                totalPrice = note.price
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
private fun ReturnOrRefundRecordUITest(){
    RefundRecordScreen(
        uiState = RefundRecordUiState.Loading,
        errState = ErrorUiState.Loading,
        navBack = {},
    )
}