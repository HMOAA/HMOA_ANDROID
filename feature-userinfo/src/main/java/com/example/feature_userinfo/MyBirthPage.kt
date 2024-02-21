package com.example.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.BottomButton
import com.hmoa.component.TopBar

@Composable
fun MyBirthPage(){

    //view model에서 출생연도를 가져와야 함
    //test
    var initYear by remember{mutableIntStateOf(2001)}
    
    //dialog state
    var showDialog by remember{mutableStateOf(false)}

    //initYear과 현재 spinner의 year가 다르다면 변경 버튼 활성화
    var isEnabled by remember{mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            navIcon = Icons.Filled.KeyboardArrowLeft,
            onNavClick = {
                /** 뒤로가기 navigation */
            },
            title = "출생연도"
        )

        Spacer(Modifier.height(36.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ){
            Text(
                text = "출생연도",
                fontSize = 16.sp,
                color = Color(0xFF414141)
            )

            Spacer(Modifier.height(8.dp))

            /** Spinner 이용 dialog 띄우는 곳 */
        }

        BottomButton(
            isEnabled = isEnabled,
            btnText = "변경",
            onClick = {
                /** 데이터 update */
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestMyBirthPage(){
    MyBirthPage()
}