package com.hmoa.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_userinfo.viewModel.PostUiState
import com.example.feature_userinfo.viewModel.PostViewModel
import com.hmoa.component.PostListItem
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun MyPostRoute(
    onNavBack: () -> Unit,
    onNavEditPost: () -> Unit,
    viewModel : PostViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val type = viewModel.type.collectAsStateWithLifecycle()

    MyPostPage(
        uiState = uiState.value,
        postType = type.value,
        onAddPage = {
            viewModel.addPage()
        },
        onTypeChanged = {
            viewModel.changeType(it)
        },
        onNavBack = onNavBack,
        onNavEditPost = onNavEditPost,
    )
}

@Composable
fun MyPostPage(
    uiState : PostUiState,
    postType : String,
    onAddPage : () -> Unit,
    onTypeChanged : (String) -> Unit,
    onNavBack: () -> Unit,
    onNavEditPost: () -> Unit, //누르면 게시글 수정 화면으로?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(R.drawable.back_btn),
            title = "작성한 게시글",
            onNavClick = onNavBack
        )

        when(uiState) {
            PostUiState.Loading -> {
                /** Loading 화면 */
            }
            else -> {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    /** PL되면 TypeButton 2개 추가 */
                }
                when(uiState){
                    is PostUiState.Posts -> {
                        Spacer(Modifier.height(23.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            items(uiState.posts) { post ->
                                /** post에 따른 match */
                                PostListItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .border(width = 1.dp, color = CustomColor.gray2, shape = RoundedCornerShape(10.dp)),
                                    onPostClick = onNavEditPost,
                                    postType = post.category,
                                    postTitle = post.title,
                                    heartCount = post.heartCount,
                                    commentCount = post.commentCount
                                )
                            }
                        }
                    }
                    PostUiState.Empty -> {
                        NoDataPage(
                            mainMsg = "작성한 게시글이\n없습니다.",
                            subMsg = "게시글을 올려주세요"
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
fun TestMyPostPage() {
    MyPostPage(
        onNavBack = {},
        onNavEditPost = {},
        uiState = PostUiState.Loading,
        postType = "시향기",
        onAddPage = {

        },
        onTypeChanged = {

        }
    )
}
