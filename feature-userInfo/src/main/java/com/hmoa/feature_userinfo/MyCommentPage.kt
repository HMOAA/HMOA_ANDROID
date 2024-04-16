package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.feature_userinfo.viewModel.CommentUiState
import com.hmoa.feature_userinfo.viewModel.CommentViewModel
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Comment
import com.hmoa.feature_userinfo.NoDataPage
import com.hmoa.feature_userinfo.R

@Composable
fun MyCommentRoute(
    onNavBack : () -> Unit,
    onNavCommunity : () -> Unit,
    viewModel : CommentViewModel = hiltViewModel()
){
    //comment list
    val commentUiState by viewModel.uiState.collectAsStateWithLifecycle()

    //댓글 타입
    val commentType by viewModel.type.collectAsStateWithLifecycle()

    MyCommentPage(
        uiState = commentUiState,
        commentType = commentType,
        onNavBack = onNavBack,
        onNavCommunity = onNavCommunity,
        onAddPage = {
            viewModel.addPage()
        },
        onTypeChanged = {
            viewModel.changeType(it)
        }
    )
}

@Composable
fun MyCommentPage(
    uiState : CommentUiState,
    commentType : String,
    onNavBack : () -> Unit,
    onNavCommunity : () -> Unit, //Community로 이동 (Comment에서 사용),
    onAddPage : () -> Unit,
    onTypeChanged : (nweType : String) -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        //Toolbar
        TopBar(
            navIcon = painterResource(R.drawable.back_btn),
            title = "작성한 댓글",
            onNavClick = onNavBack //뒤로 가기
        )

        when(uiState) {
            CommentUiState.Loading -> {
                /** Loading 화면 띄우기 */
            }
            else -> {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    /** PL되면 TypeButton 2개 추가 */
                }
                when (uiState) {
                    is CommentUiState.Comments -> {
                        LazyColumn {
                            items(uiState.comments){ comment ->
                                /** Comment 클릭 시 해당 댓글이 있는 Community로 이동 */
                                Comment(
                                    profile = comment.profileImg,
                                    nickname = comment.nickname,
                                    dateDiff = comment.createAt,
                                    comment = comment.content,
                                    isFirst = false,
                                    viewNumber = if (comment.heartCount > 999) "999+" else comment.heartCount.toString(),
                                    onNavCommunity = onNavCommunity,
                                    onOpenBottomDialog = {/** Bottom Dialog 띄울 거면 사용 */}
                                )
                            }
                        }
                    }
                    CommentUiState.Empty -> {
                        NoDataPage(
                            mainMsg = "작성한 댓글이\n없습니다",
                            subMsg = "좋아하는 함수에 댓글을 작성해주세요"
                        )
                    }
                    else -> {

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TestMyCommentPage(){
    var commentType = "Perfume"
    MyCommentPage(
        uiState = CommentUiState.Empty,
        commentType = commentType,
        onNavBack = {},
        onNavCommunity = {},
        onAddPage = {

        },
        onTypeChanged = {
            commentType = it
        }
    )
}