package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyActivity(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            /** 뒤로 가기 navigation */
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {

                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back Button",
                    tint = Color.Black
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "내 활동",
                fontSize = 20.sp
            )

            Spacer(Modifier.weight(1f))

            Spacer(Modifier.width(20.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "좋아요 누른 댓글",
                fontSize = 16.sp
            )
            
            /** 좋아요 누른 댓글 화면으로 navigation */
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {

                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Nav Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(Modifier.fillMaxWidth().height(1.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "작성한 댓글",
                fontSize = 16.sp
            )

            /** 작성한 댓글로 navigation */
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {

                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Nav Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(Modifier.fillMaxWidth().height(1.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "작성한 게시글",
                fontSize = 16.sp
            )

            /** 작성한 게시글 navigation */
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {

                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Nav Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(Modifier.fillMaxWidth().height(1.dp))
    }
}

@Preview
@Composable
fun TestMyActivity(){
    MyActivity()
}