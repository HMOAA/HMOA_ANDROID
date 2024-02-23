package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R

@Composable
fun FloatingActionBtn(
    onRecommendClick : () -> Unit,
    onPresentClick : () -> Unit,
    onFreeClick : () -> Unit
){

    var expanded by remember{mutableStateOf(false)}

    Column(
        modifier = Modifier.width(135.dp)
            .height(338.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        DropdownMenu(
            modifier = Modifier.width(135.dp)
                .height(137.dp)
                .background(color = Color.Black, shape = RoundedCornerShape(size = 10.dp)),
            expanded = expanded,
            offset = DpOffset(0.dp, (-201).dp),
            onDismissRequest = {
                expanded = false
            }
        ) {
            DropdownMenuItem(
                modifier = Modifier.weight(1f),
                text = {
                    Text(
                        text = "추천",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                },
                onClick = onRecommendClick
            )
            DropdownMenuItem(
                modifier = Modifier.weight(1f),
                text = {
                    Text(
                        text = "선물",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                },
                onClick = onPresentClick
            )
            DropdownMenuItem(
                modifier = Modifier.weight(1f),
                text = {
                    Text(
                        text = "자유",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                },
                onClick = onFreeClick
            )
        }
        IconButton(
            modifier = Modifier.size(56.dp),
            onClick = {
                expanded = !expanded
            }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.fab_icon),
                contentDescription = "FAB",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestFAB(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ){
        FloatingActionBtn(
            onFreeClick = {},
            onPresentClick = {},
            onRecommendClick = {}
        )
    }
}