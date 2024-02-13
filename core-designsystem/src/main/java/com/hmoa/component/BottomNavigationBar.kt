package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.DesignModel.Menu

@Composable
fun BottomNavigationBar(
    selectedMenu : Menu,
    onMenuClick : (Menu) -> Unit,
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color.Black)
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable {
                    if (selectedMenu != Menu.Home) {
                        onMenuClick(Menu.Home)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Filled.Home,
                contentDescription = "Home Menu",
                tint = Color.White
            )

            if (selectedMenu == Menu.Home){
                Spacer(Modifier.height(2.dp))

                Text(
                    text = Menu.Home.name,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable {
                    if (selectedMenu != Menu.HPedia) {
                        onMenuClick(Menu.HPedia)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Filled.DateRange,
                contentDescription = "HPedia Menu",
                tint = Color.White
            )

            if (selectedMenu == Menu.HPedia){
                Spacer(Modifier.height(2.dp))

                Text(
                    text = Menu.HPedia.name,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable {
                    if (selectedMenu != Menu.Home) {
                        onMenuClick(Menu.Favorite)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "Home Menu",
                tint = Color.White
            )

            if (selectedMenu == Menu.Favorite){
                Spacer(Modifier.height(2.dp))

                Text(
                    text = Menu.Home.name,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable {
                    if (selectedMenu != Menu.MyPage) {
                        onMenuClick(Menu.MyPage)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = "Home Menu",
                tint = Color.White
            )

            if (selectedMenu == Menu.MyPage){
                Spacer(Modifier.height(2.dp))

                Text(
                    text = Menu.MyPage.name,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestBottomNavBar(){

    var selectedMenu by remember{mutableStateOf(Menu.Home)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        BottomNavigationBar(
            selectedMenu = selectedMenu,
            onMenuClick = {
                selectedMenu = it
            }
        )
    }
}