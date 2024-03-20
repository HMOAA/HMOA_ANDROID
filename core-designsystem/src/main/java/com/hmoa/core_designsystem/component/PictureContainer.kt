package com.hmoa.core_designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.theme.CustomColor
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PictureContainer(
    modifier : Modifier = Modifier,
    pictures : List<String>,
){
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(
        pageCount = { pictures.size },
        initialPage = 0
    )

    Column(
        modifier = modifier,
    ){
        HorizontalPager(
            state = pagerState
        ) {
            GlideImage(
                imageModel = pictures[pagerState.currentPage],
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = "Pictures",
                loading = {
                    /** loading 화면을 따로 받을 수 있다면 추가 */
                },
                failure = {
                    Box(
                        modifier = Modifier.size(28.dp)
                            .clip(CircleShape)
                            .background(color = Color.White, shape = CircleShape)
                    ){
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Not Loading Profile",
                            tint = CustomColor.gray2
                        )
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally)
        ){
            for (i: Int in pictures.indices){

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = if (i == pagerState.currentPage) Color.Black else CustomColor.gray5,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPictureContainer(){
    PictureContainer(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 27.dp, vertical = 32.dp),
        pictures = listOf("A","B","C","D")
    )
}