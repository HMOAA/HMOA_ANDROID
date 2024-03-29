package com.hmoa.app

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.component.MainBottomBar
import com.hmoa.core_designsystem.component.MainTopBar

@Composable
fun MainRoute(
    onNavCommunity: () -> Unit
) {
    MainScreen(
        onNavCommunity = onNavCommunity
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    onNavCommunity: () -> Unit
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        backgroundColor = Color.White,
        scaffoldState = scaffoldState,
        topBar = { MainTopBar({}, {}, {}, true) },
        drawerContent = { Text(text = "drawerContent") },
        bottomBar = {
            MainBottomBar(
                initValue = BottomScreen.Home,
                onClickHome = {
                    Log.d("TAG TEST", "Bottom Nav Home Click")
                },
                onClickHPedia = {
                    Log.d("TAG TEST", "Bottom Nav HPedia Click")
                    onNavCommunity()
                },
                onClickLike = {
                    Log.d("TAG TEST", "Bottom Nav Like Click")
                },
                onClickMyPage = {
                    Log.d("TAG TEST", "Bottom Nav MyPage Click")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            //TODO("네비게이션이 들어가야 할 거 같음")
        }
    }
}

@Preview
@Composable
fun ScaffoldDemoPreview() {
    MainScreen({})
}