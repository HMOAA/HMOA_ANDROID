package com.example.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hmoa.component.TopBar
import com.hmoa.feature_userinfo.R

@Composable
fun EditProfilePage(
    navController : NavController
){

    //nick name 초반 값을 viewModel로 collectAsState()로 받아오고 그것을 변경
    //test 값
    var nickname by remember{mutableStateOf("test")}

    //중복 확인에 따른 boolean 값
    var isChecked by remember{mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            navIcon = Icons.Filled.KeyboardArrowLeft,
            onNavClick = {
                /** 뒤로가기 nvaigation */
            },
            title = "프로필 수정"
        )

        Spacer(Modifier.height(38.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Box(
                modifier = Modifier.size(72.dp),
                contentAlignment = Alignment.BottomEnd
            ){
                /** profile 이미지 정보 match */
                Icon(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color(0xFFBBBBBB), shape = CircleShape),
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile"
                )

                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(color = Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(
                        modifier = Modifier.size(16.dp),
                        onClick = {
                            /** Camera App 연동해서 처리 */
                        }
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.profile_edit_btn),
                            contentDescription = "Profile Edit Button",
                            tint = Color(0xFFBBBBBB)
                        )
                    }

                }
            }
        }

        Spacer(Modifier.height(72.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 16.dp)
        ){
            Text(
                text = "닉네임",
                color = Color(0xFF414141)
            )

            Spacer(Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
            ){
                BasicTextField(
                    modifier = Modifier.weight(1f)
                        .fillMaxHeight(),
                    value = nickname,
                    onValueChange = {
                        nickname = it
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                    decorationBox = {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ){
                            Row(
                                modifier = Modifier.height(45.dp)
                                    .padding(start = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = nickname,
                                )
                            }

                            Divider()
                        }
                    }
                )

                Spacer(Modifier.width(8.dp))

                Button(
                    shape = RoundedCornerShape(size = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isChecked) Color.Black else Color(0xFFBBBBBB)
                    ),
                    onClick = {
                        /** 중복확인 API */
                    }
                ) {
                    Text(
                        text = "중복확인",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = if(isChecked) "중복하는 아이디가 있습니다" else "사용할 수 있는 닉네임입니다",
                color = if (isChecked) Color(0xFFEE5D5D) else Color(0xFF3596EF),
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestEditProfilePage(){
    EditProfilePage(
        navController = rememberNavController()
    )
}