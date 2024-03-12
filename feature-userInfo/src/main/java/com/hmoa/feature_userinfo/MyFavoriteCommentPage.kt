package com.hmoa.feature_userinfo


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.feature_userinfo.viewModel.FavoriteCommentUiState
import com.example.feature_userinfo.viewModel.FavoriteCommentViewModel
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Comment
import com.hmoa.feature_userinfo.viewModel.CommentUiState

@Composable
fun MyFavoriteCommentRoute(
    onNavBack: () -> Unit,
    onNavCommunity: () -> Unit,
    viewModel : FavoriteCommentViewModel = hiltViewModel()
) {

    //comment list
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    //댓글 타입
    val commentType by viewModel.type.collectAsStateWithLifecycle()


    MyFavoriteCommentPage(
        onNavBack = onNavBack,
        onNavCommunity = onNavCommunity,
        uiState = uiState,
        commentType = commentType,
        onAddPage = {
            viewModel.addPage()
        },
        onTypeChanged = {
            viewModel.changeType(it)
        }
    )
}

@Composable
fun MyFavoriteCommentPage(
    onNavBack: () -> Unit,
    onNavCommunity: () -> Unit, //Community로 이동?
    uiState : FavoriteCommentUiState,
    commentType : String,
    onAddPage: () -> Unit,
    onTypeChanged: (newType: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(R.drawable.back_btn),
            title = "좋아요 누른 댓글",
            onNavClick = onNavBack //뒤로 가기
        )

        when(uiState) {
            FavoriteCommentUiState.Loading -> {
                /** Loading 화면 띄우기 */
            }
            else -> {
                Spacer(Modifier.height(16.dp))

                /** PL되면 TypeButton 2개 추가 */
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    /** PL되면 TypeButton 2개 추가 */
                }

                when (uiState) {
                    is FavoriteCommentUiState.Comments -> {
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
                                    onNavCommunity = onNavCommunity
                                )
                            }
                        }
                    }
                    FavoriteCommentUiState.Empty -> {
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

@Preview(showBackground = true)
@Composable
fun TestMyFavoriteCommentPage() {
    MyFavoriteCommentPage(
        onNavBack = {},
        onNavCommunity = {},
        uiState = FavoriteCommentUiState.Loading,
        commentType = "Perfume",
        onAddPage = {

        },
        onTypeChanged = {

        }
    )
}