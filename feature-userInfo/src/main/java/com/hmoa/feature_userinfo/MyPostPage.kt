package com.hmoa.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.component.TopBar

@Composable
fun MyPostRoute(
    onNavBack: () -> Unit,
    onNavEditPost: () -> Unit
) {
    MyPostPage(
        onNavBack = onNavBack,
        onNavEditPost = onNavEditPost
    )
}

@Composable
fun MyPostPage(
    onNavBack: () -> Unit,
    onNavEditPost: () -> Unit, //누르면 게시글 수정 화면으로?
) {

    /** view model에서 작성한 게시글 데이터를 받아옴 */
    val postList = listOf<Any>()

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

        Spacer(Modifier.height(23.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(postList) { post ->
                /** post에 따른 match */
//                PostListItem(
//                    onPostClick = { /*TODO*/ },
//                    onMenuClick = { /*TODO*/ },
//                    postType = ,
//                    postTitle =
//                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestMyPostPage() {
    MyPostPage(
        onNavBack = {},
        onNavEditPost = {}
    )
}
