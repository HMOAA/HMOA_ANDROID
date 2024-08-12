package com.hyangmoa.feature_perfume.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hyangmoa.component.TopBar
import com.hyangmoa.core_designsystem.R
import com.hyangmoa.core_designsystem.component.AppDesignDialog
import com.hyangmoa.core_designsystem.component.CommentItem
import com.hyangmoa.core_designsystem.component.ReportModal
import com.hyangmoa.core_designsystem.theme.CustomColor
import com.hyangmoa.core_model.data.SortType
import com.hyangmoa.core_model.response.PerfumeCommentResponseDto
import com.hyangmoa.feature_perfume.viewmodel.PerfumeCommentViewmodel
import kotlinx.coroutines.launch

@Composable
fun PerfumeCommentRoute(
    onBackClick: () -> Unit,
    onAddCommentClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
    perfumeId: Int?
) {
    if (perfumeId != null) {
        PerfumeCommentScreen(
            onBackClick = { onBackClick() },
            onAddCommentClick = { onAddCommentClick(it) },
            onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) },
            perfumeId = perfumeId
        )
    }
}

@Composable
fun PerfumeCommentScreen(
    onBackClick: () -> Unit,
    onAddCommentClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
    perfumeId: Int,
    viewModel: PerfumeCommentViewmodel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val unLoginErrorState by viewModel.unLoginedErrorState.collectAsStateWithLifecycle()
    val latestPerfumeComments = viewModel.getPagingLatestPerfumeComments(perfumeId)?.collectAsLazyPagingItems()
    val likePerfumeComments = viewModel.getPagingLikePerfumeComments(perfumeId)?.collectAsLazyPagingItems()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var isOpen by remember { mutableStateOf(true) }

    LaunchedEffect(perfumeId) {
        viewModel.savePerfumeId(perfumeId)
    }

    if (unLoginErrorState) {
        AppDesignDialog(isOpen = isOpen,
            modifier = Modifier.wrapContentHeight()
                .width(screenWidth - 88.dp),
            title = "로그인 후 이용가능한 서비스입니다",
            content = "입력하신 내용을 다시 확인해주세요",
            buttonTitle = "로그인 하러가기",
            onOkClick = {
                isOpen = false
            },
            onCloseClick = {
                isOpen = false
                onBackClick()
            })
    }


    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is PerfumeCommentViewmodel.PerfumeCommentUiState.Loading -> {}
            is PerfumeCommentViewmodel.PerfumeCommentUiState.CommentData -> {
                PerfumeCommentContent(
                    latestPerfumeComments = latestPerfumeComments,
                    likePerfumeComments = likePerfumeComments,
                    sortType = (uiState as PerfumeCommentViewmodel.PerfumeCommentUiState.CommentData).sortType,
                    onBackClick = { onBackClick() },
                    onSortLikeClick = { viewModel.onClickSortLike() },
                    onSortLatestClick = { viewModel.onClickSortLatest() },
                    onAddCommentClick = { if (viewModel.getHasToken()) onAddCommentClick(perfumeId) else viewModel.notifyLoginNeed() },
                    onPerfumeCommentReportClick = { viewModel.onClickReport() },
                    saveReportTargetId = { viewModel.saveTargetId(it) },
                    onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) },
                    onSpecificCommentLikeClick = { commentId, isLike, index ->
                        if (viewModel.getHasToken()) {
                            viewModel.updatePerfumeCommentLike(commentId = commentId, like = isLike)
                            when ((uiState as PerfumeCommentViewmodel.PerfumeCommentUiState.CommentData).sortType) {
                                SortType.LATEST -> latestPerfumeComments?.refresh()
                                SortType.LIKE -> likePerfumeComments?.refresh()
                            }
                        } else {
                            viewModel.notifyLoginNeed()
                        }
                    }
                )
            }

            is PerfumeCommentViewmodel.PerfumeCommentUiState.Error -> {}
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PerfumeCommentContent(
    latestPerfumeComments: LazyPagingItems<PerfumeCommentResponseDto>?,
    likePerfumeComments: LazyPagingItems<PerfumeCommentResponseDto>?,
    sortType: SortType,
    onBackClick: () -> Unit,
    onSortLikeClick: () -> Unit,
    onSortLatestClick: () -> Unit,
    onAddCommentClick: () -> Unit,
    onPerfumeCommentReportClick: () -> Unit,
    saveReportTargetId: (commentId: String) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
    onSpecificCommentLikeClick: (commentId: Int, isLike: Boolean, index: Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )


    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxHeight(),
        sheetState = modalSheetState,
        sheetContent = {
            ReportModal(
                onOkClick = {
                    scope.launch {
                        onPerfumeCommentReportClick()
                        modalSheetState.hide()
                    }
                },
                onCancelClick = {
                    scope.launch { modalSheetState.hide() }
                },
            )
        },
        sheetBackgroundColor = CustomColor.gray2,
        sheetContentColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Color.White),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Top
            ) {
                TopBar(
                    title = "댓글",
                    iconSize = 25.dp,
                    navIcon = painterResource(R.drawable.ic_back),
                    onNavClick = { onBackClick() },
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.padding(16.dp)
                ) {
                    CommentAndSortText(
                        commentCount = latestPerfumeComments?.itemCount ?: 0,
                        onSortLikeClick = { onSortLikeClick() },
                        onSortLatestClick = { onSortLatestClick() },
                        sortType = sortType
                    )
                    if (sortType == SortType.LIKE) {
                        PerfumeCommentList(
                            likePerfumeComments,
                            {
                                saveReportTargetId(it)
                                scope.launch { modalSheetState.show() }
                            },
                            { id, isWrited -> onSpecificCommentClick(id, isWrited) },
                            { commentId, isLike, index -> onSpecificCommentLikeClick(commentId, isLike, index) }
                        )
                    } else {
                        PerfumeCommentList(
                            latestPerfumeComments,
                            {
                                saveReportTargetId(it)
                                scope.launch { modalSheetState.show() }
                            },
                            { id, isWrited -> onSpecificCommentClick(id, isWrited) },
                            { commentId, isLike, index -> onSpecificCommentLikeClick(commentId, isLike, index) }
                        )
                    }
                }
            }
            BottomCommentAddBar(onAddCommentClick = { onAddCommentClick() })
        }
    }
}

@Composable
fun PerfumeCommentList(
    latestPerfumeComments: LazyPagingItems<PerfumeCommentResponseDto>?,
    onShowReportModal: (id: String) -> Unit,
    onSpecificCommentClick: (id: String, isWrited: Boolean) -> Unit,
    onSpecificCommentLikeClick: (commentId: Int, isLike: Boolean, index: Int) -> Unit,
) {
    LazyColumn(
        userScrollEnabled = true,
    ) {
        val length = latestPerfumeComments?.itemSnapshotList?.size ?: 0
        itemsIndexed(items = latestPerfumeComments?.itemSnapshotList ?: emptyList()) { index, it ->
            CommentItem(
                count = it?.heartCount ?: 0,
                isCommentLiked = it!!.liked,
                userImgUrl = it.profileImg ?: "",
                userName = it.nickname,
                content = it.content,
                createdDate = it.createdAt ?: "",
                onReportClick = { onShowReportModal(it.id.toString()) },
                onCommentItemClick = { onSpecificCommentClick(it.id.toString(), it.writed) },
                onCommentLikedClick = { onSpecificCommentLikeClick(it.id, !it.liked, it.id) }
            )
            if (index < length) {
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray2)
                )
            }
        }
    }
}

@Composable
fun CommentAndSortText(
    commentCount: Int,
    onSortLikeClick: () -> Unit,
    onSortLatestClick: () -> Unit,
    sortType: SortType
) {
    val likeColor = if (sortType == SortType.LIKE) Color.Black else CustomColor.gray2
    val latestColor = if (sortType == SortType.LATEST) Color.Black else CustomColor.gray2


    Row(
        verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "댓글",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                "+${commentCount}",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "좋아요순",
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light),
                modifier = Modifier.padding(end = 4.dp).clickable { onSortLikeClick() },
                color = likeColor
            )
            Text(
                "최신순",
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light),
                modifier = Modifier.padding(end = 4.dp).clickable { onSortLatestClick() },
                color = latestColor
            )
        }
    }
}

@Composable
fun BottomCommentAddBar(onAddCommentClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().height(82.dp).background(color = Color.Black)
            .clickable { onAddCommentClick() }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add_coment),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color.White
        )
        Text(
            "댓글작성",
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 20.sp, color = Color.White)
        )
    }
}
