package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//인증이 되어 있는 My Page
@Composable
fun MyPage(){

    var test by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "마이페이지",
                fontSize = 20.sp,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(horizontal = 16.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            /** user 데이터 받아오기 (이미지 링크 기반) */
            Icon(
                modifier = Modifier.size(44.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile"
            )

            Column(
                modifier = Modifier.weight(1f)
            ){
                /** user 이름 */
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "김가은",
                    fontSize = 20.sp
                )

                Spacer(Modifier.weight(1f))

                /** 로그인 방식 */
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "카카오톡 로그인",
                    fontSize = 12.sp,
                )
            }
            
            Spacer(Modifier.weight(1f))

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    /** 수정 버튼 이벤트 */
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))

        /** 내 활동으로 navigation */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "내 활동",
                fontSize = 16.sp
            )

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Nav Button",
                tint = Color(0xFFBBBBBB)
            )
        }

        /** 내 정보로 navigation */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "내 정보관리",
                fontSize = 16.sp
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Nav Button",
                tint = Color(0xFFBBBBBB)
            )
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))

        /** 오픈소스라이센스로 navigation */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "오픈소스라이센스",
                fontSize = 16.sp
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Nav Button",
                tint = Color(0xFFBBBBBB)
            )
        }

        /** 개인정보 처림방침으로 navigation */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "개인정보 처리방침",
                fontSize = 16.sp
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Nav Button",
                tint = Color(0xFFBBBBBB)
            )
        }

        /** 버전 정보로 navigation */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            /** 버전 정보를 받아와야 함 */
            Text(
                text = "버전정보 1.00 (128)",
                fontSize = 16.sp
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Nav Button",
                tint = Color(0xFFBBBBBB)
            )
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))

        /** 1대1 문의로 navigation */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "1대1 문의",
                fontSize = 16.sp
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Nav Button",
                tint = Color(0xFFBBBBBB)
            )
        }

        /** 로그아웃 >> Login 화면으로 navigation? */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "로그아웃",
                fontSize = 16.sp
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Nav Button",
                tint = Color(0xFFBBBBBB)
            )
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
                text = "계정삭제",
                fontSize = 16.sp
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Nav Button",
                tint = Color(0xFFBBBBBB)
            )
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun TestMyPage(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        MyPage()
    }
}