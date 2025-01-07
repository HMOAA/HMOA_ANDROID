package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.data.ColumnData

@Composable
fun MyActivityRoute(
    navMyFavoriteComment: () -> Unit,
    navMyComment : () -> Unit,
    navMyPost : () -> Unit,
    navBack : () -> Unit,
    navMyReview: () -> Unit,
){
    MyActivityPage(
        navMyFavoriteComment = navMyFavoriteComment,
        navMyComment = navMyComment,
        navMyPost = navMyPost,
        navBack = navBack,
        navMyReview = navMyReview
    )
}

@Composable
fun MyActivityPage(
    navMyFavoriteComment: () -> Unit,
    navMyComment: () -> Unit,
    navMyPost: () -> Unit,
    navBack: () -> Unit,
    navMyReview: () -> Unit,
){
    val columnData = listOf(
        ColumnData("좋아요 누른 댓글"){navMyFavoriteComment()},
        ColumnData("작성한 댓글"){navMyComment()},
        ColumnData("작성한 게시글"){navMyPost()},
        ColumnData("작성한 리뷰"){navMyReview()}
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "내 활동",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = navBack
        )
        LazyColumn{
            itemsIndexed(columnData){idx, data ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clickable{data.onNavClick()}
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = data.title,
                        fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
                        fontSize = 16.sp
                    )

                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_next),
                        contentDescription = "Nav Button",
                        tint = CustomColor.gray2
                    )
                }
                if (idx != columnData.lastIndex){ HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2) }
            }
        }
    }
}

@Preview
@Composable
fun TestMyActivity(){
    MyActivityPage(
        navMyFavoriteComment = {},
        navMyComment = {},
        navMyPost = {},
        navBack = {},
        navMyReview = {}
    )
}