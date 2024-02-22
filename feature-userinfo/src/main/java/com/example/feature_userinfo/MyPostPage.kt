package com.example.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hmoa.component.PostListItem
import com.hmoa.component.TopBar

@Composable
fun MyPostPage(
    navController: NavController
){

    /** view model에서 작성한 게시글 데이터를 받아옴 */
    val postList = listOf<Any>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            navIcon = Icons.Filled.KeyboardArrowLeft,
            title = "작성한 게시글",
            onNavClick = {
                navController.navigateUp()
            }
        )

        Spacer(Modifier.height(23.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
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
fun TestMyPostPage(){
    MyPostPage(
        navController = rememberNavController()
    )
}