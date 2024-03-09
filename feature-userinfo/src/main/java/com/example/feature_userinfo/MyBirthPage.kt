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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.feature_userinfo.viewModel.UserViewModel
import com.hmoa.component.Spinner
import com.hmoa.component.TopBar
import com.hmoa.component.YearPickerDialog
import com.hmoa.core_designsystem.component.Button
import com.hmoa.feature_userinfo.R

@Composable
fun MyBirthRoute(
    onNavBack : () -> Unit,
    viewModel : UserViewModel = hiltViewModel()
){
    MyBirthPage(
        onNavBack = onNavBack
    )
}
@Composable
fun MyBirthPage(
    onNavBack : () -> Unit
){
    //view model에서 출생연도를 가져와야 함
    //test
    var selectedYear by remember{mutableIntStateOf(2001)}
    
    //dialog state
    var showDialog by remember{mutableStateOf(false)}

    //initYear과 현재 spinner의 year가 다르다면 변경 버튼 활성화
    var isEnabled by remember{mutableStateOf(false)}

    val configuration = LocalConfiguration.current

    if (showDialog) {
        YearPickerDialog(
            yearList = (1950..2024).toList(),
            value = selectedYear,
            width = configuration.screenWidthDp.dp,
            height = 370.dp,
            onDismiss = {
                showDialog = false
            },
            onDoneClick = {
                /** view model에서 가지고 있는 데이터 값을 init value로 가지고 이를 교체 */
                selectedYear = it

                showDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            navIcon = painterResource(R.drawable.back_btn),
            onNavClick = onNavBack,
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
            Spinner(
                width = 152.dp,
                height = 46.dp,
                value = "${selectedYear}",
                onClick = {
                    showDialog = true
                }
            )
        }

        /** 데이터 update */
        Button(
            isEnabled = isEnabled,
            btnText = "변경",
            onClick = {
                /** 데이터 update */
            }
        )
    }
    LaunchedEffect(selectedYear){
        /** selected year과 user info로 가져온 view model의 year와 다르다면 isEnabled를 true로*/
        isEnabled = true
    }
}

@Preview(showBackground = true)
@Composable
fun TestMyBirthPage(){
    MyBirthPage(
        onNavBack = {}
    )
}