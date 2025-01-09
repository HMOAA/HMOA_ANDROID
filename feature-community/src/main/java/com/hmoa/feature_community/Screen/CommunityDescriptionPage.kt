package com.hmoa.feature_community.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.feature_community.ViewModel.CommunityDescUiState
import com.hmoa.feature_community.ViewModel.CommunityDescViewModel
import kotlinx.coroutines.launch

@Composable
fun CommunityDescriptionRoute(
    id: Int?,
    navCommunityEdit: (communityId: Int) -> Unit,
    navCommentEdit: (communityId: Int) -> Unit,
    navLogin: () -> Unit,
    navBack: () -> Unit,
    viewModel: CommunityDescViewModel = hiltViewModel()
) {
    viewModel.setId(id)

    val context = LocalContext.current
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val comments = viewModel.commentPagingSource().collectAsLazyPagingItems()
    val onHeartClick = remember<(commentId: Int, isLiked: Boolean) -> Unit> {
        { commentId, isLiked ->
            viewModel.updateCommentLike(
                commentId,
                isLiked
            )
        }
    }
    val onReportCommunityClick = remember<() -> Unit>{{
        viewModel.reportCommunity { Toast.makeText(context,"신고 완료",Toast.LENGTH_SHORT).show() }
    }}
    val onReportCommentClick = remember<(commentId: Int) -> Unit>{{
        viewModel.reportComment(it){ Toast.makeText(context, "신고 완료", Toast.LENGTH_SHORT).show() }
    }}
    val postComment = remember<(comment: String)-> Unit>{{
        viewModel.postComment(it)
        comments.refresh()
    }}
    val deleteCommunity = remember<() -> Unit>{{
        viewModel.delCommunity{
            Toast.makeText(context, "게시글 삭제 완료", Toast.LENGTH_SHORT).show()
            navBack()
        }
    }}
    val deleteComment = remember<(commentId: Int) -> Unit>{{
        viewModel.delComment(it){
            comments.refresh()
            Toast.makeText(context, "댓글 삭제", Toast.LENGTH_SHORT).show()
        }
    }}

    CommunityDescriptionPage(
        errState = errState.value,
        onChangeLike = viewModel::updateLike,
        uiState = uiState.value,
        commentList = comments,
        navBack = navBack,
        onReportCommunity = onReportCommunityClick,
        onReportComment = onReportCommentClick,
        onPostComment = postComment,
        onChangeCommentLike = onHeartClick,
        onDeleteCommunity = deleteCommunity,
        onDeleteComment = deleteComment,
        navCommunityEdit = { navCommunityEdit(id!!) },
        navCommentEdit = navCommentEdit,
        onErrorHandleLoginAgain = navLogin
    )
}

@Composable
fun CommunityDescriptionPage(
    errState: ErrorUiState,
    uiState: CommunityDescUiState,
    commentList: LazyPagingItems<CommunityCommentWithLikedResponseDto>,
    onChangeLike: (isLiked: Boolean) -> Unit,
    onReportCommunity: () -> Unit,
    onReportComment: (commentId: Int) -> Unit,
    onPostComment: (comment: String) -> Unit,
    onChangeCommentLike: (commentId: Int, isLiked: Boolean) -> Unit,
    onDeleteCommunity: () -> Unit,
    onDeleteComment: (commentId: Int) -> Unit,
    navBack: () -> Unit,
    navCommunityEdit: () -> Unit,
    navCommentEdit: (commentId: Int) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
) {
    when (uiState) {
        CommunityDescUiState.Loading -> AppLoadingScreen()
        is CommunityDescUiState.CommunityDesc -> {
            CommunityDescContent(
                community = uiState,
                commentList = commentList,
                onChangeLike = onChangeLike,
                onReportCommunity = onReportCommunity,
                onReportComment = onReportComment,
                onPostComment = onPostComment,
                onChangeCommentLike = onChangeCommentLike,
                onDeleteCommunity = onDeleteCommunity,
                onDeleteComment = onDeleteComment,
                navBack = navBack,
                navCommunityEdit = navCommunityEdit,
                navCommentEdit = navCommentEdit,
            )
        }
        CommunityDescUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = onErrorHandleLoginAgain,
                errorUiState = errState,
                onCloseClick = navBack,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CommunityDescContent(
    community: CommunityDescUiState.CommunityDesc,
    commentList: LazyPagingItems<CommunityCommentWithLikedResponseDto>,
    onChangeLike: (isLiked: Boolean) -> Unit,
    onReportCommunity: () -> Unit,
    onReportComment: (Int) -> Unit,
    onPostComment: (String) -> Unit,
    onChangeCommentLike: (Int, Boolean) -> Unit,
    onDeleteCommunity: () -> Unit,
    onDeleteComment: (Int) -> Unit,
    navBack: () -> Unit,
    navCommunityEdit: () -> Unit,
    navCommentEdit: (Int) -> Unit,
) {
    var type by remember { mutableStateOf("post") }
    val onChangeType: (String) -> Unit = { type = it }
    val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded })
    var comment by remember { mutableStateOf<CommunityCommentWithLikedResponseDto?>(null) }
    val scope = rememberCoroutineScope()
    val dialogOpen = { scope.launch { modalSheetState.show() } }
    val dialogClose = {
        scope.launch { modalSheetState.hide() }
        navBack()
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = modalSheetState,
        sheetContent = {
            if (type == "post" && community.writed) {
                EditModal(
                    onDeleteClick = onDeleteCommunity,
                    onEditClick = navCommunityEdit,
                    onCancelClick = dialogClose
                )
            } else if (type == "comment" && comment != null && comment!!.writed) {
                EditModal(
                    onDeleteClick = { onDeleteComment(comment!!.commentId) },
                    onEditClick = { navCommentEdit(comment!!.commentId) },
                    onCancelClick = dialogClose
                )
            } else {
                ReportModal(
                    onOkClick = {
                        if (type == "post") { onReportCommunity() }
                        else { if (comment != null) { onReportComment(comment!!.commentId) }}
                        dialogClose()
                    },
                    onCancelClick = dialogClose,
                )
            }
        },
        sheetBackgroundColor = CustomColor.gray2,
        sheetContentColor = Color.Transparent
    ) {
        CommunityDescMainContent(
            community = community,
            onDialogOpen = {
                dialogOpen()
                onChangeType("post")
            },
            onChangeLike = onChangeLike,
            commentList = commentList,
            onChangeCommentLike = onChangeCommentLike,
            onChangeType = onChangeType,
            onPostComment = onPostComment,
            setComment = { comment = it },
            onNavBack = navBack,
        )
    }
}

@Composable
private fun CommunityDescMainContent(
    community: CommunityDescUiState.CommunityDesc,
    onChangeLike: (Boolean) -> Unit,
    commentList: LazyPagingItems<CommunityCommentWithLikedResponseDto>,
    onChangeType: (String) -> Unit,
    onChangeCommentLike: (Int, Boolean) -> Unit,
    onPostComment: (String) -> Unit,
    setComment: (CommunityCommentWithLikedResponseDto) -> Unit,
    onDialogOpen: () -> Unit,
    onNavBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            title = "Community",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = community.category,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
                color = CustomColor.gray2
            )
            PostContent(
                modifier = Modifier
                    .fillMaxWidth(),
                width = configuration.screenWidthDp.dp,
                onChangeBottomSheetState = onDialogOpen,
                profile = community.category,
                nickname = community.author,
                dateDiff = community.time,
                title = community.title,
                content = community.content,
                heartCount = community.heartCount,
                isLiked = community.liked,
                onChangeLike = onChangeLike,
                pictures = community.communityPhotos
            )
            Column(modifier = Modifier.padding(16.dp)) {
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
                Spacer(Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "답변",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
                        color = Color.Black
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "+${commentList.itemCount}",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
                        color = Color.Black
                    )
                }
            }
            Spacer(Modifier.height(21.dp))
            Comments(
                commentList = commentList,
                changeBottomOptionState = { onDialogOpen() },
                onChangeType = onChangeType,
                onChangeCommentLike = onChangeCommentLike,
                setComment = { setComment(it) }
            )
        }

        CommentInputBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .background(color = CustomColor.gray6, shape = RoundedCornerShape(5.dp)),
            profile = community.myProfileImgUrl,
            onCommentApply = { onPostComment(it) }
        )
        Spacer(Modifier.height(7.dp))
    }
}

@Composable
private fun Comments(
    commentList: LazyPagingItems<CommunityCommentWithLikedResponseDto>,
    changeBottomOptionState: (Boolean) -> Unit,
    onChangeType: (String) -> Unit,
    onChangeCommentLike: (Int, Boolean) -> Unit,
    setComment: (CommunityCommentWithLikedResponseDto) -> Unit,
) {
    val comments = commentList.itemSnapshotList
    if (comments.isNotEmpty()) {
        comments.reversed().forEachIndexed { index, comment ->
            if (comment != null) {
                Comment(
                    isEditable = true,
                    profile = comment.profileImg,
                    nickname = comment.author,
                    dateDiff = comment.time,
                    comment = comment.content,
                    isFirst = false,
                    isSelected = comment.liked,
                    onHeartClick = { onChangeCommentLike(comment.commentId, it) },
                    heartCount = comment.heartCount,
                    navCommunity = {/* 여기서는 아무 event도 없이 처리 */ },
                    onOpenBottomDialog = {
                        setComment(comment)
                        changeBottomOptionState(true)
                        onChangeType("comment")
                    }
                )
                if (index != comments.size - 1) {
                    Spacer(Modifier.height(15.dp))
                    HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2, modifier = Modifier.padding(16.dp))
                }
            }
        }
    } else {
        Spacer(Modifier.height(40.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "아직 작성한 댓글이 없습니다",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
            color = Color.Black
        )
        Spacer(Modifier.height(30.dp))
    }
}