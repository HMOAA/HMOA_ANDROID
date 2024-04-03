package com.hmoa.feature_perfume.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.paging.PagingSource.LoadResult.Page
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.CommentItem
import com.hmoa.core_designsystem.component.ReportModal
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.data.SortType
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
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
    val result = Page(
        data = listOf(),
        prevKey = null,
        nextKey = null
    )

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is PerfumeCommentViewmodel.PerfumeCommentUiState.Loading -> {}
            is PerfumeCommentViewmodel.PerfumeCommentUiState.CommentData -> {
                PerfumeCommentContent(
                    data = (uiState as PerfumeCommentViewmodel.PerfumeCommentUiState.CommentData).sortedComments,
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
    data: PerfumeCommentGetResponseDto?,
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
    val verticalScrollState = rememberScrollState()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    fun showReportModal(id: String) {
        scope.launch { modalSheetState.show() }
        saveReportTarget(id)
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            ReportModal(onOkClick = {
                scope.launch {
                    onReportClick()
                    modalSheetState.hide()
                }
            }, onCancelClick = { scope.launch { modalSheetState.hide() } })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(verticalScrollState)
                .background(color = Color.White)
        ) {
            TopBar(
                title = "댓글",
                iconSize = 25.dp,
                navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                onNavClick = { onBackClick() },
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                CommentAndSortText(
                    commentCount = 0,
                    onSortLikeClick = { onSortLikeClick() },
                    onSortLatestClick = { onSortLatestClick() },
                    sortType = sortType
                )
                LazyColumn {
                    items(data?.comments ?: emptyArray()) {
                        CommentItem(
                            count = it.heartCount,
                            isCommentLiked = it.liked,
                            userImgUrl = it.profileImg ?: "",
                            userName = it.nickname,
                            content = it.content,
                            createdDate = it.createdAt ?: "",
                            onReportClick = { showReportModal(it.id.toString()) },
                            onCommentItemClick = { onSpecificCommentClick(it.id.toString(), it.writed) }
                        )
                    }
                }
            }
            BottomCommentAddBar(onAddCommentClick = { onAddCommentClick() })
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
        modifier = Modifier.padding(bottom = 4.dp).padding(top = 48.dp)
    ) {
        Text(
            "댓글",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            "+${commentCount}",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
        )
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
            modifier = Modifier.size(32.dp)
        )
        Text(
            "댓글달기",
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 20.sp)
        )
    }
}