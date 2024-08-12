package com.hyangmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(onClickDrawer: () -> Unit, onClickSearch: () -> Unit, onClickNotification: () -> Unit,isNotificationOn:Boolean) {

    TopAppBar(
        title = {
            Row(modifier = Modifier.fillMaxWidth().background(color = Color.White), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(
                    onClick = {onClickDrawer()},
                    modifier = Modifier.background(Color.White),
                    content = {
                    Image(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_drawer),
                        contentDescription = "drawer페이지 버튼",
                    )
                })
                Text("HMOA")
                Row {
                    IconButton(
                        onClick = {onClickSearch()},
                        modifier = Modifier.background(Color.White),
                        content = {
                            Image(
                                modifier = Modifier.size(25.dp),
                                painter = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_search),
                                contentDescription = "검색 버튼",
                            )
                        }
                    )
                    Column {
                        IconButton(
                            onClick = {onClickNotification()},
                            modifier = Modifier.background(Color.White),
                            content = {
                                Image(
                                    modifier = Modifier.size(25.dp),
                                    painter = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_bell),
                                    contentDescription = "알람 on/off 표시아이콘",
                                )
                            }
                        )
                    }
                }
            }
        },
        modifier = Modifier.background(color = Color.White).fillMaxWidth(),
    )
}

@Composable
@Preview
fun MainTopBarPreview() {
    MainTopBar({},{},{},true)
}