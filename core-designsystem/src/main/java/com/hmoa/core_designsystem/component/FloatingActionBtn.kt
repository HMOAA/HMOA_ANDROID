package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FloatingActionBtn(){

    var isOpen by remember{mutableStateOf(false)}

    Column {

    }
    IconButton(
        modifier = Modifier.size(56.dp),
        onClick = {
            isOpen = !isOpen
        }
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "FAB"
        )
    }

    DropdownMenu(

        expanded = isOpen,
        onDismissRequest = {
            isOpen = false
        }
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "클릭"
                )
            },
            onClick = {
                //추천 클릭
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = "선물"
                )
            },
            onClick = {
                //추천 클릭
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = "자유"
                )
            },
            onClick = {
                //추천 클릭
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestFAB(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        FloatingActionBtn()
    }
}