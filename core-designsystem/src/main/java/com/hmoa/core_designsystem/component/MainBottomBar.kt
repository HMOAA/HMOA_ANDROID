package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.BottomNavItem
import com.hmoa.core_designsystem.R

@Composable
fun MainBottomBar(
    initValue : Int,
    onClickHome: () -> Unit,
    onClickHPedia: () -> Unit,
    onClickLike: () -> Unit,
    onClickMyPage: () -> Unit
) {
    var selectedNavIdx by remember{mutableIntStateOf(initValue)}

    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = onClickHome,
            icon = if(selectedNavIdx == 0) painterResource(R.drawable.ic_nav_home_selected)
            else painterResource(R.drawable.ic_home)
        ),
        BottomNavItem(
            name = "HPedia",
            route = onClickHPedia,
            icon = if (selectedNavIdx == 1) painterResource(R.drawable.ic_nav_hpedia_selected)
            else painterResource(R.drawable.ic_hpedia)
        ),
        BottomNavItem(
            name = "Like",
            route = onClickLike,
            icon = if (selectedNavIdx == 2) painterResource(R.drawable.ic_nav_like_selected)
            else painterResource(R.drawable.ic_heart)
        ),
        BottomNavItem(
            name = "My",
            route = onClickMyPage,
            icon = if (selectedNavIdx == 3) painterResource(R.drawable.ic_nav_my_page_selected)
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
                        selectedNavIdx = bottomNavItems.indexOf(item)
                    },
                    icon = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                modifier = Modifier.size(25.dp),
                                painter = item.icon,
                                contentDescription = "${item.name}아이템",
                            )

                            Spacer(Modifier.height(5.dp))

                            if (bottomNavItems[selectedNavIdx] == item) {
                                Text(text = item.name, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White)
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
    MainBottomBar(0, {}, {}, {}, {})
}