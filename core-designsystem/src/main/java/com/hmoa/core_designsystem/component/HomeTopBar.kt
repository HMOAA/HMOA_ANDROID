package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R

@Composable
fun HomeTopBar(
    onDrawerClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    drawerIcon: Painter,
    searchIcon: Painter,
    notificationIcon: Painter,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier.clickable { onDrawerClick() }.size(20.dp),
            painter = drawerIcon,
            contentDescription = "Drawer Button"
        )

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.clickable { onSearchClick() }.padding(end = 24.dp).size(20.dp),
                painter = searchIcon,
                contentDescription = "Search Button",
            )
            Icon(
                modifier = Modifier.clickable { onNotificationClick() }.size(24.dp),
                painter = notificationIcon,
                contentDescription = "Notification Button"
            )
        }
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar(
        title = "H M O A",
        onDrawerClick = { },
        onSearchClick = {},
        onNotificationClick = {},
        drawerIcon = painterResource(R.drawable.ic_drawer),
        searchIcon = painterResource(R.drawable.ic_search),
        notificationIcon = painterResource(R.drawable.ic_bell)
    )
}