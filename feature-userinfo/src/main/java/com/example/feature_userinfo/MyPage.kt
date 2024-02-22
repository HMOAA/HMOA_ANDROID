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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.feature_userinfo.Screens
import com.hmoa.feature_userinfo.R

//인증이 되어 있는 My Page
@Composable
fun MyPage(
    navController : NavController,
    navLoginPage : () -> Unit,
){

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
                    // 프로필 수정 화면으로 이동
                    navController.navigate(Screens.EditProfilePage.name)
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

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    // 내 활동 화면으로 이동
                    navController.navigate(Screens.MyActivityPage.name)
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Navigation Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
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
                text = "내 정보관리",
                fontSize = 16.sp
            )

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    // 내 정보 화면으로 이동
                    navController.navigate(Screens.MyInfoPage.name)
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Navigation Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))

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

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    /** 오픈소스 라이센스 화면으로 이동 */
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Navigation Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
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
                text = "개인정보 처리방침",
                fontSize = 16.sp
            )

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    /** 개인정보 처리방침 화면으로 이동 */
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Navigation Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

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

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    /** 버전 정보 화면으로 이동? */
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Navigation Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))

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

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    /** 1대1 문의에 맞는 화면으로 이동 */
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Navigation Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
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
                text = "로그아웃",
                fontSize = 16.sp
            )

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    /** logout 로직 */

                    // 로그인 화면으로 이동
                    navLoginPage()
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Navigation Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
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

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    /** 계정 삭제 로직 후 로그인 화면으로 이동? */

                    navLoginPage()
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Navigation Button",
                    tint = Color(0xFFBBBBBB)
                )
            }
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
        MyPage(
            navController = rememberNavController(),
            navLoginPage = {}
        )
    }
}