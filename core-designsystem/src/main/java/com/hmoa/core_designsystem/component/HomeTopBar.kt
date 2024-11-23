package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hmoa.core_designsystem.R

@Composable
fun HomeTopBar(
    navBackStackEntry: NavBackStackEntry?,
    onDrawerClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    drawerIcon: Painter,
    searchIcon: Painter,
    notificationIcon: Painter,
) {
    var currentRoute by remember{ mutableStateOf<String?>(null) }
    val isVisibleTopBar by remember{derivedStateOf {
        currentRoute == "Home"
    }}
    LaunchedEffect(navBackStackEntry?.destination?.route){
        if (navBackStackEntry?.destination?.route != null){
            currentRoute = navBackStackEntry.destination.route
        }
    }
    if(isVisibleTopBar){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .clickable { onDrawerClick() }
                    .size(20.dp),
                painter = drawerIcon,
                contentDescription = "Drawer Button"
            )

            Row{
                Image(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(R.drawable.ic_alphabet_h),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.padding(end= 14.dp))
                Image(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(R.drawable.ic_alphabet_m),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.padding(end= 14.dp))
                Image(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(R.drawable.ic_alphabet_o),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.padding(end= 14.dp))
                Image(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(R.drawable.ic_alphabet_a),
                    contentDescription = null,
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .clickable { onSearchClick() }
                        .padding(end = 24.dp)
                        .size(20.dp),
                    painter = searchIcon,
                    contentDescription = "Search Button",
                )
                Icon(
                    modifier = Modifier
                        .clickable { onNotificationClick() }
                        .size(24.dp),
                    painter = notificationIcon,
                    contentDescription = "Notification Button"
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    val navBackStackEntry by rememberNavController().currentBackStackEntryAsState()
    HomeTopBar(
        navBackStackEntry = navBackStackEntry,
        onDrawerClick = { },
        onSearchClick = {},
        onNotificationClick = {},
        drawerIcon = painterResource(R.drawable.ic_drawer),
        searchIcon = painterResource(R.drawable.ic_search),
        notificationIcon = painterResource(R.drawable.ic_bell)
    )
}