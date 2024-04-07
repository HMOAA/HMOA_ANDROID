package com.hmoa.feature_perfume.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.CommentItem
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.data.SortType
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.feature_perfume.viewmodel.PerfumeCommentViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PerfumeCommentScreen(
    onBackClick: () -> Unit,
    onAddCommentClick: (perfumeId: Int?) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
    perfumeId: Int?,
    viewModel: PerfumeCommentViewmodel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val latestPerfumeComments =
        viewModel.getPagingLatestPerfumeComments(perfumeId)?.collectAsLazyPagingItems()

    LaunchedEffect(perfumeId) {
        if (perfumeId != null) {
            viewModel.savePerfumeId(perfumeId)
        }
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
                    data = latestPerfumeComments,
                    sortType = (uiState as PerfumeCommentViewmodel.PerfumeCommentUiState.CommentData).sortType,
                    onBackClick = { onBackClick() },
                    onSortLikeClick = { viewModel.onClickSortLike() },
                    onSortLatestClick = { viewModel.onClickSortLatest() },
                    onAddCommentClick = { onAddCommentClick(perfumeId) },
                    onReportClick = { viewModel.onClickReport() },
                    saveReportTarget = { viewModel.saveTargetId(it) },
                    onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) }
                )
            }

            is PerfumeCommentViewmodel.PerfumeCommentUiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PerfumeCommentContent(
    data: LazyPagingItems<PerfumeCommentResponseDto>?,
    sortType: SortType,
    onBackClick: () -> Unit,
    onSortLikeClick: () -> Unit,
    onSortLatestClick: () -> Unit,
    onAddCommentClick: () -> Unit,
    onReportClick: () -> Unit,
    saveReportTarget: (commentId: String) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit
) {
    val scope = CoroutineScope(Dispatchers.IO)
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    fun showReportModal(id: String) {
        scope.launch { modalSheetState.show() }
        saveReportTarget(id)
    }
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
                navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                onNavClick = { onBackClick() },
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                CommentAndSortText(
                    commentCount = data?.itemCount ?: 0,
                    onSortLikeClick = { onSortLikeClick() },
                    onSortLatestClick = { onSortLatestClick() },
                    sortType = sortType
                )
                LazyColumn(
                    userScrollEnabled = true,
                ) {

                    items(items = data?.itemSnapshotList ?: emptyList()) {
                        CommentItem(
                            count = it?.heartCount ?: 0,
                            isCommentLiked = it!!.liked,
                            userImgUrl = it.profileImg ?: "",
                            userName = it.nickname,
                            content = it.content,
                            createdDate = it.createdAt ?: "",
                            onReportClick = { showReportModal(it.id.toString()) },
                            onCommentItemClick = { onSpecificCommentClick(it.id.toString(), it.writed) },
                            onCommentLikedClick = {}
                        )
                    }

                }
            }
        }
        BottomCommentAddBar(onAddCommentClick = { onAddCommentClick() })
    }


//    ModalBottomSheetLayout(
//        sheetState = modalSheetState,
//        sheetContent = {
//            ReportModal(onOkClick = {
//                scope.launch {
//                    onReportClick()
//                    modalSheetState.hide()
//                }
//            }, onCancelClick = { scope.launch { modalSheetState.hide() } })
//        }
//    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth().fillMaxHeight()
//                .background(color = Color.White),
//            verticalArrangement = Arrangement.Top
//        ) {
//            TopBar(
//                title = "댓글",
//                iconSize = 25.dp,
//                navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
//                onNavClick = { onBackClick() },
//            )
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier.padding(16.dp)
//            ) {
//                CommentAndSortText(
//                    commentCount = data.itemCount,
//                    onSortLikeClick = { onSortLikeClick() },
//                    onSortLatestClick = { onSortLatestClick() },
//                    sortType = sortType
//                )
//                LazyColumn(
//                    userScrollEnabled = true,
//                ) {
//
//                    items(items = data.itemSnapshotList) {
//                        CommentItem(
//                            count = it?.heartCount ?: 0,
//                            isCommentLiked = it!!.liked,
//                            userImgUrl = it.profileImg ?: "",
//                            userName = it.nickname,
//                            content = it.content,
//                            createdDate = it.createdAt ?: "",
//                            onReportClick = { showReportModal(it.id.toString()) },
//                            onCommentItemClick = { onSpecificCommentClick(it.id.toString(), it.writed) },
//                            onCommentLikedClick = {}
//                        )
//                    }
//
//                }
//            }
//            BottomCommentAddBar(onAddCommentClick = { onAddCommentClick() })
//        }
//    }
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