package com.hmoa.core_designsystem.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.BottomNavItem
import org.w3c.dom.Text

@Composable
fun MainBottomBar(
    onClickHome: () -> Unit,
    onClickHPedia: () -> Unit,
    onClickLike: () -> Unit,
    onClickMyPage: () -> Unit
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = onClickHome,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_home)
        ),
        BottomNavItem(
            name = "HPedia",
            route = onClickHPedia,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_hpedia)
        ),
        BottomNavItem(
            name = "Like",
            route = onClickLike,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_heart)
        ),
        BottomNavItem(
            name = "My",
            route = onClickMyPage,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_person)
        ),
    )
    NavigationBar(containerColor = Color.Black) {
        bottomNavItems.forEach{item ->
            NavigationBarItem(
                selected = false,
                onClick = {},
                label = { Text(text = item.name, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White) },
                icon = {
                    Image(
                        modifier = Modifier.size(25.dp),
                        painter = item.icon,
                        contentDescription = "${item.name}아이템",
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun MainBottomBarPreview() {
    MainBottomBar({}, {}, {}, {})
}