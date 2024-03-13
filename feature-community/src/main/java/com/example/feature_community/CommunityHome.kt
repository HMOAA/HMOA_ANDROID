package com.example.feature_community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.PostListItem
import com.hmoa.core_model.response.CommunityDefaultResponseDto

@Composable
fun CommunityHomeRoute(){
    
    //ui state를 전달 >> 여기에 community list를 가지고 이를 통해 LazyColumn 이용
    val uiState = null
    
    CommunityHome(
        communities = emptyList(),
        onNavCommunityByCategory = {},
        onNavCommunityDescription = {}
    )
}

@Composable
fun CommunityHome(
    communities : List<CommunityDefaultResponseDto>, //이거 uiState로 이전해서 uiState에서 데이터 가져오는 방식으로
    onNavCommunityByCategory : () -> Unit, //카테고리 별 Community 화면으로 이동
    onNavCommunityDescription : () -> Unit, //해당 Community Id를 가진 Description 화면으로 이동
){
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Community",
                fontSize = 16.sp,
                color = Color.Black
            )
            
            TextButton(
                onClick = {
                    /** 여기서 카테고리 별 조회 화면 (real community home)으로 이동 */
                    onNavCommunityByCategory()
                },
            ){
                Text(
                    text = "전체보기",
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn{
            items(communities){community ->
                PostListItem(
                    onPostClick = {
                        /** 여기서 해당 post description으로 이동 */
                        onNavCommunityDescription()
                    },
                    postType = "",
                    postTitle = ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestCommunityHome(){
    CommunityHome(
        communities = emptyList(),
        onNavCommunityDescription = {},
        onNavCommunityByCategory = {}
    )
}