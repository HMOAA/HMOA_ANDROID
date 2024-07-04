package com.hmoa.core_designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.BottomNavItem
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun MainBottomBar(
    initValue : String,
    onClickHome: () -> Unit,
    onClickHPedia: () -> Unit,
    onClickLike: () -> Unit,
    onClickMyPage: () -> Unit
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            name = BottomScreen.Home,
            route = onClickHome,
            icon = if(initValue == "Home") painterResource(R.drawable.ic_nav_home_selected)
            else painterResource(R.drawable.ic_home)
        ),
        BottomNavItem(
            name = BottomScreen.HPedia,
            route = onClickHPedia,
            icon = if (initValue == "HPedia") painterResource(R.drawable.ic_nav_hpedia_selected)
            else painterResource(R.drawable.ic_hpedia)
        ),
        BottomNavItem(
            name = BottomScreen.Magazine,
            route = onClickLike,
            icon = if (initValue == "Magazine") painterResource(R.drawable.ic_magazine_selected)
            else painterResource(R.drawable.ic_magazine_not_selected)
        ),
        BottomNavItem(
            name = BottomScreen.MyPage,
            route = onClickMyPage,
            icon = if (initValue == "MyPage") painterResource(R.drawable.ic_nav_my_page_selected)
            else painterResource(R.drawable.ic_person)
        ),
    )

    NavigationBar(containerColor = Color.Black) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            bottomNavItems.forEach{item ->
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        item.route()
                    },
                    icon = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Icon(
                                modifier = Modifier.size(25.dp),
                                painter = item.icon,
                                contentDescription = "${item.name}아이템",
                                tint = Color.White
                            )

                            Spacer(Modifier.height(5.dp))

                            if (initValue == item.name.name) {
                                Text(text = item.name.name, fontSize = 12.sp, fontWeight = FontWeight.Medium, fontFamily = pretendard, color = Color.White)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun MainBottomBarPreview() {
    var selectedScreen = BottomScreen.Home.name
    MainBottomBar(selectedScreen, {selectedScreen = BottomScreen.Home.name}, {selectedScreen = BottomScreen.HPedia.name}, {}, {})
}