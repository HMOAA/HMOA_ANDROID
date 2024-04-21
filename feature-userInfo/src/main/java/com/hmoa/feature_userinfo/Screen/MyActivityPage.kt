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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.ColumnData
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
    val columnData = listOf(
        ColumnData("좋아요 누른 댓글"){onNavMyFavoriteComment()},
        ColumnData("작성한 댓글"){onNavMyComment()},
        ColumnData("작성한 게시글"){onNavMyPost()}
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "내 활동",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack
        )
        LazyColumn{
            itemsIndexed(columnData){idx, data ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = data.title,
                        fontSize = 16.sp
                    )

                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = data.onNavClick
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_next),
                            contentDescription = "Nav Button",
                            tint = CustomColor.gray2
                        )
                    }
                }
                if (idx != columnData.lastIndex){
                    HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
                }
            }
        }
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