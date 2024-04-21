package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
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
import com.hmoa.component.TopBar
import com.hmoa.feature_userinfo.R

@Composable
fun MyActivityRoute(
    onNavMyFavoriteComment: () -> Unit,
    onNavMyComment : () -> Unit,
    onNavMyPost : () -> Unit,
    onNavBack : () -> Unit,
){
    MyActivityPage(
        onNavMyFavoriteComment = onNavMyFavoriteComment,
        onNavMyComment = onNavMyComment,
        onNavMyPost = onNavMyPost,
        onNavBack = onNavBack
    )
}

@Composable
fun MyActivityPage(
    onNavMyFavoriteComment : () -> Unit,
    onNavMyComment : () -> Unit,
    onNavMyPost : () -> Unit,
    onNavBack : () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "내 활동",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack //뒤로 가기
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "좋아요 누른 댓글",
                fontSize = 16.sp
            )

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onNavMyFavoriteComment // 좋아요 누른 댓글
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Nav Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "작성한 댓글",
                fontSize = 16.sp
            )

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onNavMyComment // 작성한 댓글로 이동
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Nav Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "작성한 게시글",
                fontSize = 16.sp
            )

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onNavMyPost //작성한 게시글로 이동
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Nav Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))
    }
}

@Preview
@Composable
fun TestMyActivity(){
    MyActivityPage(
        onNavMyFavoriteComment = {},
        onNavMyComment = {},
        onNavMyPost = {},
        onNavBack = {}
    )
}