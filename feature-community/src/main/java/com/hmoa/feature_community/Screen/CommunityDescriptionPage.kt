package com.hmoa.feature_community.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppDefaultDialog
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Comment
import com.hmoa.core_designsystem.component.CommentInputBar
import com.hmoa.core_designsystem.component.PostContent
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.feature_community.ViewModel.CommunityDescUiState
import com.hmoa.feature_community.ViewModel.CommunityDescViewModel

@Composable
fun CommunityDescriptionRoute(
    _id: Int?,
    onNavCommunityEdit: (Int) -> Unit,
    onNavCommentEdit : (Int) -> Unit,
    onNavBack : () -> Unit,
    viewModel : CommunityDescViewModel = hiltViewModel()
){
    val id = _id ?: -1
    viewModel.setId(id)

    val errState = viewModel.errState.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val isOpenBottomOptions = viewModel.isOpenBottomOptions.collectAsStateWithLifecycle()
    val isLiked = viewModel.isLiked.collectAsStateWithLifecycle()
    val comments = viewModel.commentPagingSource().collectAsLazyPagingItems()
    var type by remember{mutableStateOf("post")}

    val context = LocalContext.current

    CommunityDescriptionPage(
        errState = errState.value,
        isOpenBottomOptions = isOpenBottomOptions.value,
        changeBottomOptionState = {viewModel.updateBottomOptionsState(it)},
        type = type,
        onChangeType = {type = it},
        isLiked = isLiked.value,
        onChangeLike = {viewModel.updateLike()},
        uiState = uiState.value,
        commentList = comments,
        onNavBack = onNavBack,
        onReportCommunity = {
            viewModel.reportCommunity()
            viewModel.updateBottomOptionsState(false)
            Toast.makeText(context, "신고 완료", Toast.LENGTH_SHORT).show()
        },
        onReportComment = {
            viewModel.reportComment(it)
            viewModel.updateBottomOptionsState(false)
            Toast.makeText(context, "신고 완료", Toast.LENGTH_SHORT).show()
        },
        onPostComment = {
            viewModel.postComment(it)
            comments.refresh()
        },
        onChangeCommentLike = { commentId , isSelected ->
            viewModel.updateCommentLike(commentId, isSelected)
            comments.refresh()
        },
        onDeleteCommunity = {
            viewModel.delCommunity()
            onNavBack()
            Toast.makeText(context, "게시글 삭제 완료", Toast.LENGTH_SHORT).show()
        },
        onDeleteComment = { commentId ->
            viewModel.delComment(commentId)
            comments.refresh()
            Toast.makeText(context, "댓글 삭제", Toast.LENGTH_SHORT).show()
        },
        onNavCommunityEdit = {onNavCommunityEdit(id)},
        onNavCommentEdit = onNavCommentEdit
    )
}

@Composable
fun CommunityDescriptionPage(
    errState : String,
    isOpenBottomOptions : Boolean,
    changeBottomOptionState : (Boolean) -> Unit,
    type : String,
    onChangeType : (String) -> Unit,
    uiState : CommunityDescUiState,
    commentList : LazyPagingItems<CommunityCommentWithLikedResponseDto>,
    isLiked : Boolean,
    onChangeLike : () -> Unit,
    onReportCommunity : () -> Unit,
    onReportComment: (Int) -> Unit,
    onPostComment : (String) -> Unit,
    onChangeCommentLike : (Int, Boolean) -> Unit,
    onDeleteCommunity : () -> Unit,
    onDeleteComment : (Int) -> Unit,
    onNavBack : () -> Unit,
    onNavCommunityEdit : () -> Unit,
    onNavCommentEdit : (Int) -> Unit
){
    val scrollState = rememberScrollState()

    var commentId by remember{mutableStateOf(0)}

    val configuration = LocalConfiguration.current

    when (uiState) {
        CommunityDescUiState.Loading -> {
            AppLoadingScreen()
        }

        is CommunityDescUiState.CommunityDesc -> {

            val community = uiState.community

            if(isOpenBottomOptions){
                BottomOptionDialog(
                    changeBottomOptionState = changeBottomOptionState,
                    isWritten = community.writed,
                    type = type,
                    onDeleteComment = { onDeleteComment(commentId) },
                    onDeleteCommunity = onDeleteCommunity,
                    onReportCommunity = onReportCommunity,
                    onReportComment = { onReportComment(commentId) },
                    onNavCommunityEdit = onNavCommunityEdit,
                    onNavCommentEdit = { onNavCommentEdit(commentId) }
                )
            }
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
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState)
                ){
                    Spacer(Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = uiState.community.category,
                        fontSize = 14.sp,
                        color = CustomColor.gray2
                    )
                    Spacer(Modifier.height(18.dp))

                    PostContent(
                        modifier = Modifier
                            .fillMaxWidth(),
                        width = configuration.screenWidthDp.dp,
                        onChangeBottomSheetState = {
                            changeBottomOptionState(it)
                            onChangeType("post")
                        },
                        profile = community.category,
                        nickname = community.author,
                        dateDiff = community.time,
                        title = community.title,
                        content = community.content,
                        heartCount = if (community.heartCount > 999) "999+" else community.heartCount.toString(),
                        isLiked = isLiked,
                        onChangeLike = onChangeLike,
                        pictures = uiState.photoList
                    )
                    HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)

                    Spacer(Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "답변",
                            fontSize = 16.sp,
                            color = Color.Black
                        )

                        Spacer(Modifier.width(4.dp))

                        Text(
                            text = "+${commentList.itemCount}",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(Modifier.height(21.dp))

                    Comments(
                        commentList = commentList.itemSnapshotList,
                        changeBottomOptionState = changeBottomOptionState,
                        onChangeType = onChangeType,
                        onChangeCommentLike = onChangeCommentLike,
                        setCommentId = {
                            commentId = it
                        }
                    )
                }
                CommentInputBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 16.dp)
                        .background(color = CustomColor.gray6, shape = RoundedCornerShape(5.dp)),
                    profile = community.myProfileImgUrl,
                    onCommentApply = {
                        onPostComment(it)
                    }
                )
                Spacer(Modifier.height(7.dp))
            }
        }
        CommunityDescUiState.Error -> {
            var isOpen by remember{mutableStateOf(true)}
            AppDefaultDialog(
                isOpen = isOpen,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.8f),
                title = "오류",
                content = errState,
                onDismiss = {
                    isOpen = false
                    changeBottomOptionState(false)
                    onNavBack()
                }
            )
        }
    }
}

@Composable
fun Comments(
    commentList : ItemSnapshotList<CommunityCommentWithLikedResponseDto>,
    changeBottomOptionState : (Boolean) -> Unit,
    onChangeType: (String) -> Unit,
    onChangeCommentLike : (Int, Boolean) -> Unit,
    setCommentId : (Int) -> Unit,
){
    if (commentList.isNotEmpty()) {
        commentList.reversed().forEachIndexed { index, comment ->
            if (comment != null){
                Comment(
                    profile = comment.profileImg,
                    nickname = comment.author,
                    dateDiff = comment.time,
                    comment = comment.content,
                    isFirst = false,
                    isSelected = comment.liked,
                    onChangeSelect = {onChangeCommentLike(comment.commentId, !comment.liked)},
                    heartCount = comment.heartCount,
                    onNavCommunity = {/** 여기서는 아무 event도 없이 처리 */},
                    onOpenBottomDialog = {
                        setCommentId(comment.commentId)
                        changeBottomOptionState(true)
                        onChangeType("comment")
                    }
                )
                if (index != commentList.size - 1) {
                    Spacer(Modifier.height(15.dp))
                    HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
                }
            }
        }
    } else {
        Spacer(Modifier.height(40.dp))
        Text(
            text = "아직 작성한 댓글이 없습니다",
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(30.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomOptionDialog(
    changeBottomOptionState : (Boolean) -> Unit,
    isWritten : Boolean,
    type : String,
    onDeleteComment: () -> Unit,
    onDeleteCommunity: () -> Unit,
    onReportCommunity: () -> Unit,
    onReportComment: () -> Unit,
    onNavCommunityEdit : () -> Unit,
    onNavCommentEdit : () -> Unit
){
    val dialogDefaultTextStyle = TextStyle(
        fontSize = 20.sp,
        color = CustomColor.blue
    )
    val dialogRedTextStyle = TextStyle(
        fontSize = 20.sp,
        color = CustomColor.red
    )

    ModalBottomSheet(
        onDismissRequest = { changeBottomOptionState(false) },
        containerColor = Color.White,
        scrimColor = Color.Black.copy(alpha = 0.3f),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .padding(vertical = 16.dp)
        ) {
            if (isWritten) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            //게시글일 경우
                            if (type == "post") {
                                onNavCommunityEdit()
                            } else {
                                onNavCommentEdit()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "수정",
                        textAlign = TextAlign.Center,
                        style = dialogDefaultTextStyle
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            //post 일 경우
                            if (type == "post") {
                                onDeleteCommunity()
                            }
                            //댓글 일 경우
                            else {
                                onDeleteComment()
                                changeBottomOptionState(false)
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "삭제",
                        textAlign = TextAlign.Center,
                        style = dialogDefaultTextStyle
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            changeBottomOptionState(false)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "취소",
                        textAlign = TextAlign.Center,
                        style = dialogRedTextStyle
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            //게시글
                            if (type == "post") {
                                onReportCommunity()
                            }
                            //댓글
                            else {
                                onReportComment()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "신고하기",
                        textAlign = TextAlign.Center,
                        style = dialogDefaultTextStyle
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            changeBottomOptionState(false)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "취소",
                        textAlign = TextAlign.Center,
                        style = dialogRedTextStyle
                    )
                }
            }
        }
    }
}