package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.feature_userinfo.viewModel.CommentUiState
import com.example.feature_userinfo.viewModel.CommentViewModel
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Comment
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
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

    MyCommentPage(
        uiState = commentUiState,
        onNavBack = onNavBack,
        onNavCommunity = onNavCommunity
    )
}

@Composable
fun MyCommentPage(
    uiState : CommentUiState,
    onNavBack : () -> Unit,
    onNavCommunity : () -> Unit, //Community로 이동 (Comment에서 사용),
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
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
            is CommentUiState.Comments -> {
                LazyColumn {
                    items(uiState.comments){ comment ->
                        /** Comment 클릭 시 해당 댓글이 있는 Community로 이동 */
//                        Comment(
//                            profile = comment.profileImg,
//                            nickname = comment.author,
//                            dateDiff = comment.writed,
//                            comment = ,
//                            isFirst = ,
//                            viewNumber =
//                        )
                    }
                }
            }
            CommentUiState.Empty -> {
                NoDataPage(
                    mainMsg = "작성한 댓글이\n없습니다",
                    subMsg = "좋아하는 함수에 댓글을 작성해주세요"
                )
            }

            else -> {}
        }
    }
}

@Preview
@Composable
fun TestMyCommentPage(){
    MyCommentPage(
        uiState = CommentUiState.Empty,
        onNavBack = {},
        onNavCommunity = {}
    )
}