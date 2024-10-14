package com.hmoa.feature_hbti.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.EditModal
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.FloatingActionBtn
import com.hmoa.core_designsystem.component.ReportModal
import com.hmoa.core_designsystem.component.ReviewItem
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.Photo
import com.hmoa.core_model.response.ReviewResponseDto
import com.hmoa.feature_hbti.viewmodel.ReviewUiState
import com.hmoa.feature_hbti.viewmodel.ReviewViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Composable
fun ReviewRoute(
    navBack: () -> Unit,
    navWriteReview: (orderId: Int) -> Unit,
    navEditReview: (reviewId: Int) -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errState by viewModel.errorUiState.collectAsStateWithLifecycle()
    ReviewScreen(
        uiState = uiState,
        errState = errState,
        navBack = navBack,
        navWriteReview = navWriteReview,
        onHeartClick = viewModel::onHeartClick,
        onDeleteClick = viewModel::deleteReview,
        onEditClick = navEditReview,
        onReportClick = viewModel::reportReview,
        handleNoDateError = viewModel::handleNoDateError
    )
}

@Composable
fun ReviewScreen(
    uiState: ReviewUiState,
    errState: ErrorUiState,
    navBack: () -> Unit,
    navWriteReview: (orderId: Int) -> Unit,
    onHeartClick: (ReviewResponseDto) -> Unit,
    onDeleteClick: (reviewId: Int) -> Unit,
    onEditClick: (reviewId: Int) -> Unit,
    onReportClick: (reviewId: Int) -> Unit,
    handleNoDateError: () -> Unit,
){
    var isOpen by remember{ mutableStateOf(true) }
    when(uiState){
        ReviewUiState.Loading -> AppLoadingScreen()
        is ReviewUiState.Success -> {
            ReviewContent(
                reviews = uiState.reviews.collectAsLazyPagingItems().itemSnapshotList,
                orderRecords = uiState.myOrders,
                onBackClick = navBack,
                onHeartClick = onHeartClick,
                onDeleteClick = onDeleteClick,
                onEditClick = onEditClick,
                onReportClick = onReportClick,
                onFABClick = navWriteReview,
                handleNoDateError = handleNoDateError
            )
        }
        ReviewUiState.Error -> {
            ErrorUiSetView(
                isOpen = isOpen,
                onConfirmClick = navBack,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ReviewContent(
    reviews: ItemSnapshotList<ReviewResponseDto>,
    orderRecords: List<GetMyOrderResponseDto>,
    onBackClick: () -> Unit,
    onHeartClick: (ReviewResponseDto) -> Unit,
    onDeleteClick: (reviewId: Int) -> Unit,
    onEditClick: (reviewId: Int) -> Unit,
    onReportClick: (reviewId: Int) -> Unit,
    onFABClick: (orderId: Int) -> Unit,
    handleNoDateError: () -> Unit
){
    var isFabOpen by remember{mutableStateOf(false)}
    val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val animatedAlpha by animateFloatAsState(targetValue = if(isFabOpen) 0.4f else 1.0f, label = "fab open animation")
    val scope = rememberCoroutineScope()
    val dialogOpen = {scope.launch { modalSheetState.show() }}
    val dialogClose = { scope.launch { modalSheetState.hide() } }
    var selectedReview by remember{mutableStateOf<ReviewResponseDto?>(null)}
    val onFabItemClick = orderIds.map{{onFABClick(it)}}

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = modalSheetState,
        sheetContent = {
            if(selectedReview != null){
                if (selectedReview!!.isWrited) {
                    EditModal(
                        onDeleteClick = { onDeleteClick(selectedReview!!.hbtiReviewId) },
                        onEditClick = { onEditClick(selectedReview!!.hbtiReviewId) },
                        onCancelClick = { dialogClose() }
                    )
                } else {
                    ReportModal(
                        onOkClick = {
                            onReportClick(selectedReview!!.hbtiReviewId)
                            dialogClose()
                        },
                        onCancelClick = { dialogClose() },
                    )
                }
            }
        },
        sheetBackgroundColor = CustomColor.gray2,
        sheetContentColor = Color.Transparent
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(animatedAlpha),
            ){
                TopBar(
                    color = Color.Black,
                    title = "향BTI 후기",
                    titleColor = Color.White,
                    navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                    onNavClick = onBackClick,
                    navIconColor = Color.White,
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    items(
                        key = {review -> review!!.hbtiReviewId},
                        items = reviews,
                        contentType = { _ -> ReviewResponseDto }
                    ){review ->
                        if (review != null){
                            val images = remember(review.hbtiPhotos){review.hbtiPhotos.map{it.photoUrl}}
                            ReviewItem(
                                isItemClickable = false,
                                profileImg = review.profileImgUrl,
                                nickname = review.author,
                                writtenAt = review.createdAt,
                                isLiked = review.isLiked,
                                heartNumber = review.heartCount,
                                content = review.content,
                                images = images,
                                category = review.orderTitle,
                                onHeartClick = { onHeartClick(review) },
                                onMenuClick = {
                                    selectedReview = review
                                    dialogOpen()
                                },
                                onItemClick = { /* 미사용 */}
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 24.dp, bottom = 18.dp),
                contentAlignment = Alignment.BottomEnd
            ){
                FloatingActionBtn(
                    options = orderInfos,
                    events = onFabItemClick,
                    width = 208.dp,
                    fontSize = 12.sp,
                    isAvailable = true,
                    isFabOpen = isFabOpen,
                    onFabClick = {
                        if (orderInfos.isEmpty()){handleNoDateError()}
                        else {isFabOpen = it}
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReviewUiTest(){
    ReviewScreen(
        uiState = ReviewUiState.Success(
            reviews = flow{
                emit(
                    PagingData.from(
                        data = listOf(
                            ReviewResponseDto(
                                hbtiReviewId = 0,
                                profileImgUrl = "",
                                author = "향수 러버",
                                content = "평소에 선호하는 향이 있었는데 그 향의 이름을 몰랐는데 향료 배송받고 시향해보니 통카 빈?이더라구요 제가 좋아했던 향수들은 다 통카 빈이 들어가있네요 ㅎ 저같은 분들한테 추천해요",
                                imagesCount = 3,
                                hbtiPhotos = listOf(
                                    Photo(
                                        photoId = 0,
                                        photoUrl = ""
                                    ),
                                    Photo(
                                        photoId = 0,
                                        photoUrl = ""
                                    ),
                                    Photo(
                                        photoId = 0,
                                        photoUrl = ""
                                    )
                                ),
                                createdAt = "10일 전",
                                isWrited = false,
                                heartCount = 12,
                                isLiked = true,
                                orderTitle = "시트러스"
                            ),
                            ReviewResponseDto(
                                hbtiReviewId = 0,
                                profileImgUrl = "",
                                author = "향수 러버",
                                content = "향수를 1회도 구매하지 않은 사람인데 향모아에서 인생향을 찾아어요..!",
                                imagesCount = 0,
                                hbtiPhotos = listOf(),
                                createdAt = "10일 전",
                                isWrited = false,
                                heartCount = 12,
                                isLiked = false,
                                orderTitle = "플로럴"
                            ),
                        )
                    )
                )
            },
            myOrderIds = listOf(0),
            myOrderInfos = listOf("테스트 1")
        ),
        errState = ErrorUiState.Loading,
        navBack = {},
        onHeartClick = {},
        navWriteReview = {},
        handleNoDateError = {},
        onReportClick = {},
        onDeleteClick = {},
        onEditClick = {}
    )
}