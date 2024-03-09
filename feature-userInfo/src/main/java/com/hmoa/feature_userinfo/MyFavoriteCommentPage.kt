package com.hmoa.feature_userinfo


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun MyFavoriteCommentRoute(
    onNavBack: () -> Unit,
    onNavCommunity: () -> Unit
) {
    MyFavoriteCommentPage(
        onNavBack = onNavBack,
        onNavCommunity = onNavCommunity
    )
}

@Composable
fun MyFavoriteCommentPage(
    onNavBack: () -> Unit,
    onNavCommunity: () -> Unit //Community로 이동?
) {

    //좋아요를 누른 comment 리스트
    val commentList = listOf<Any>()

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

        Spacer(Modifier.height(16.dp))

        //좋아요를 누른 댓글이 있으면 LazyColumn, 없으면 No Data Page
        if (commentList.isNotEmpty()) {
            LazyColumn {
                items(commentList) { comment ->
                    /** Comment에 데이터 매칭 */
                    /** Comment Click >> Community로 이동 */
//                Comment(
//                    profile = ,
//                    nickname = ,
//                    dateDiff = ,
//                    comment = ,
//                    isFirst = ,
//                    viewNumber =
//                )
                }
            }
        } else {
            NoDataPage(
                mainMsg = "좋아요를 누른 댓글이\n없습니다.",
                subMsg = "좋아하는 함수에 좋아요를 눌러주세요"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestMyFavoriteCommentPage() {
    MyFavoriteCommentPage(
        onNavBack = {},
        onNavCommunity = {},
    )
}