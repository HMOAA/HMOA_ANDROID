package com.hmoa.feature_community.Screen

import android.util.Log
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
    onNavBack : () -> Unit,
    viewModel : CommunityDescViewModel = hiltViewModel()
){
    val id = _id ?: -1
    viewModel.setId(id)

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val profile = viewModel.profile.collectAsStateWithLifecycle()
    val isOpenBottomOptions = viewModel.isOpenBottomOptions.collectAsStateWithLifecycle()
    val isLiked = viewModel.isLiked.collectAsStateWithLifecycle()
    val comments = viewModel.commentPagingSource().collectAsLazyPagingItems()
    var type by remember{mutableStateOf("post")}

    CommunityDescriptionPage(
        isOpenBottomOptions = isOpenBottomOptions.value,
        changeBottomOptionState = {
            viewModel.updateBottomOptionsState(it)
        },
        type = type,
        onChangeType = {
            type = it
        },
        isLiked = isLiked.value,
        onChangeLike = {
            viewModel.updateLike()
        },
        uiState = uiState.value,
        commentList = comments,
        profile = profile.value,
        onNavBack = onNavBack,
        onReportCommunity = {
            viewModel.reportCommunity()
        },
        onReportComment = {
            /** 여기서 Comment 신고하기 */
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
        },
        onDeleteComment = {
            /** 여기서 Comment 삭제 */
        },
        onNavCommunityEdit = {
            onNavCommunityEdit(id)
        }
    )
}

@Composable
fun CommunityDescriptionPage(
    isOpenBottomOptions : Boolean,
    changeBottomOptionState : (Boolean) -> Unit,
    type : String,
    onChangeType : (String) -> Unit,
    uiState : CommunityDescUiState,
    commentList : LazyPagingItems<CommunityCommentWithLikedResponseDto>,
    isLiked : Boolean,
    onChangeLike : () -> Unit,
    profile : String?,
    onReportCommunity : () -> Unit,
    onReportComment: () -> Unit,
    onPostComment : (String) -> Unit,
    onChangeCommentLike : (Int, Boolean) -> Unit,
    onDeleteCommunity : () -> Unit,
    onDeleteComment : () -> Unit,
    onNavBack : () -> Unit,
    onNavCommunityEdit : () -> Unit,
){
    val scrollState = rememberScrollState()

    /** Text Style 정의 */
    val categoryTextStyle = TextStyle(
        fontSize = 14.sp,
        color = CustomColor.gray2
    )
    val infoTextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Black
    )
    val commentSizeTextStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.Black
    )

    when (uiState) {
        CommunityDescUiState.Loading -> {
            Column() {
                Text(
                    text = "Loading"
                )
            }
        }

        is CommunityDescUiState.CommunityDesc -> {

            val community = uiState.community

            if(isOpenBottomOptions){
                BottomOptionDialog(
                    changeBottomOptionState = changeBottomOptionState,
                    isWritten = community.writed,
                    type = type,
                    onDeleteComment = onDeleteComment,
                    onDeleteCommunity = onDeleteCommunity,
                    onReportCommunity = onReportCommunity,
                    onReportComment = onReportComment,
                    onNavCommunityEdit = onNavCommunityEdit
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
                        style = categoryTextStyle
                    )
                    Spacer(Modifier.height(18.dp))

                    PostContent(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                            style = infoTextStyle
                        )

                        Spacer(Modifier.width(4.dp))

                        Text(
                            text = "+${commentList.itemCount}",
                            style = commentSizeTextStyle
                        )
                    }

                    Spacer(Modifier.height(21.dp))

                    Comments(
                        commentList = commentList.itemSnapshotList,
                        changeBottomOptionState = changeBottomOptionState,
                        onChangeType = onChangeType,
                        onChangeCommentLike = onChangeCommentLike
                    )
                }
                CommentInputBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 16.dp)
                        .background(color = CustomColor.gray6, shape = RoundedCornerShape(5.dp)),
                    profile = profile,
                    onCommentApply = {
                        onPostComment(it)
                    }
                )
                Spacer(Modifier.height(7.dp))
            }
        }

        CommunityDescUiState.Error -> {
            Column {
                Text(
                    text = "Data is Error"
                )
            }
        }
    }
}

@Composable
fun Comments(
    commentList : ItemSnapshotList<CommunityCommentWithLikedResponseDto>,
    changeBottomOptionState : (Boolean) -> Unit,
    onChangeType: (String) -> Unit,
    onChangeCommentLike : (Int, Boolean) -> Unit
){
    val noDataTextStyle = TextStyle(
        fontSize = 20.sp,
        color = Color.Black
    )

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
                    onChangeSelect = {
                        onChangeCommentLike(comment.commentId, !comment.liked)
                    },
                    heartCount = comment.heartCount,
                    onNavCommunity = {/** 여기서는 아무 event도 없이 처리 */},
                    onOpenBottomDialog = {
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
            style = noDataTextStyle
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
    onNavCommunityEdit : () -> Unit
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
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            //게시글일 경우
                            if (type == "post") {
                                onNavCommunityEdit()
                            } else {
                                /** 댓글 수정 어떻게 해야하나> */
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = "수정",
                        textAlign = TextAlign.Center,
                        style = dialogDefaultTextStyle
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            //post 일 경우
                            if (type == "post") {
                                onDeleteCommunity()
                            }
                            //댓글 일 경우
                            else {
                                onDeleteComment()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = "삭제",
                        textAlign = TextAlign.Center,
                        style = dialogDefaultTextStyle
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            changeBottomOptionState(false)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = "취소",
                        textAlign = TextAlign.Center,
                        style = dialogRedTextStyle
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth()
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
                        modifier = Modifier.fillMaxSize(),
                        text = "신고하기",
                        textAlign = TextAlign.Center,
                        style = dialogDefaultTextStyle
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            changeBottomOptionState(false)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = "취소",
                        textAlign = TextAlign.Center,
                        style = dialogRedTextStyle
                    )
                }
            }
        }
    }
}