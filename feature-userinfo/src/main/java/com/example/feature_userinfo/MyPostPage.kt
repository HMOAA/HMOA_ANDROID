package com.example.feature_userinfo

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MyPostPage(
    navController: NavController
){

}

@Preview(showBackground = true)
@Composable
fun TestMyPostPage(){
    MyPostPage(
        navController = rememberNavController()
    )
}