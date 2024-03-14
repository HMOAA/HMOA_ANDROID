package com.example.feature_community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.PostListItem
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.FloatingActionBtn
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.R

@Composable
fun CommunityRoute(
    onNavBack : () -> Unit,
    onNavCommunityDescription : () -> Unit,
){

    /** 선택된 type을 Page에 넘기고 이에 따라 isSelect 값을 선택 */
    //view model의 ui state에서 type, list 를 받아서 사용하는 방식

    var type = Category.추천
    val list = listOf<CommunityByCategoryResponseDto>()

    CommunityPage(
        selectedType = type,
        onTypeChanged = {
            type = it
        },
        communities = list,
        onNavBack = onNavBack,
        onNavCommunityDescription = onNavCommunityDescription
    )
}

@Composable
fun CommunityPage(
    selectedType : Category,
    onTypeChanged : (Category) -> Unit,
    communities : List<CommunityByCategoryResponseDto>,
    onNavBack : () -> Unit,
    onNavCommunityDescription: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            TopBar(
                title = "Community",
                navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                onNavClick = onNavBack,
            )

            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = CustomColor.gray2)
            )

            /** search bar (여기서는 Search 모듈을 사용해야 하지 않을까?) */
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )

            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = CustomColor.gray2)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(start = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                TypeBadge(
                    roundedCorner = 20.dp,
                    type = Category.추천.name,
                    fontSize = 14.sp,
                    fontColor = Color.White,
                    selected = selectedType == Category.추천
                )

                Spacer(Modifier.width(8.dp))

                TypeBadge(
                    roundedCorner = 20.dp,
                    type = Category.시향기.name,
                    fontSize = 14.sp,
                    fontColor = Color.White,
                    selected = selectedType == Category.시향기
                )

                Spacer(Modifier.width(8.dp))

                TypeBadge(
                    roundedCorner = 20.dp,
                    type = Category.자유.name,
                    fontSize = 14.sp,
                    fontColor = Color.White,
                    selected = selectedType == Category.자유
                )
            }

            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = CustomColor.gray2)
            )

            LazyColumn{
                items(communities){community ->
                    PostListItem(
                        onPostClick = {
                            /** 여기서 Description으로 이동 */
                            /** id를 어떤 방식으로 전달할지 */
                            onNavCommunityDescription()
                        },
                        postType = community.category,
                        postTitle =community.title,
                    )
                }
            }
        }
        FloatingActionBtn(

        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestCommunity(){
    var type = Category.시향기
    val testList = listOf(
        CommunityByCategoryResponseDto(
            category = "시향기",
            commentCount = 0,
            communityId = 0,
            heartCount = 10,
            liked = true,
            title = "여자친구한테 선물할 향수 뭐가 좋을까요?"
        ),
        CommunityByCategoryResponseDto(
            category = "시향기",
            commentCount = 0,
            communityId = 0,
            heartCount = 10,
            liked = true,
            title = "여자친구한테 선물할 향수 뭐가 좋을까요?"
        )
    )
    CommunityPage(
        selectedType = type,
        onTypeChanged = {
            type = it
        },
        communities = testList,
        onNavBack = {},
        onNavCommunityDescription = {}
    )
}