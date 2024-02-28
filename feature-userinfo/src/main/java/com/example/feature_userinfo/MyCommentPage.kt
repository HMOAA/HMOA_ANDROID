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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hmoa.component.TopBar
import com.hmoa.feature_userinfo.R

@Composable
fun MyCommentRoute(
    onNavBack : () -> Unit,
    onNavCommunity : () -> Unit
){
    MyCommentPage(
        onNavBack = onNavBack,
        onNavCommunity = onNavCommunity
    )
}

@Composable
fun MyCommentPage(
    onNavBack : () -> Unit,
    onNavCommunity : () -> Unit, //Community로 이동 (Comment에서 사용)
){

    /** view model에서 받아온 댓글 데이터 */
    val commentList = listOf<Any>()

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

        //data가 있으면 comment list, 없으면 no data page
        if (commentList.isNotEmpty()){
            LazyColumn {
                items(commentList){ comment ->
                    /** 받은 데이터에 match */
                    /** Comment 클릭 시 해당 댓글이 있는 Community로 이동 */
//                    Comment(
//                        profile = ,
//                        nickname = ,
//                        dateDiff = ,
//                        comment = ,
//                        isFirst = ,
//                        viewNumber =
//                    )
                }
            }
        } else {
            NoDataPage(
                mainMsg = "작성한 댓글이\n없습니다",
                subMsg = "좋아하는 함수에 댓글을 작성해주세요"
            )
        }
    }
}

@Preview
@Composable
fun TestMyCommentPage(){
    MyCommentPage(
        onNavBack = {},
        onNavCommunity = {}
    )
}