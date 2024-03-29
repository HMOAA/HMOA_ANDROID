package com.example.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.feature_userinfo.viewModel.UserInfoUiState
import com.example.feature_userinfo.viewModel.UserViewModel
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Button
import com.hmoa.feature_userinfo.R

@Composable
fun MyGenderRoute(
    onNavBack : () -> Unit,
    viewModel : UserViewModel = hiltViewModel()
) {

    val isEnabled = viewModel.isEnabled.collectAsStateWithLifecycle()

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MyGenderPage(
        uiState = uiState.value,
        updateGender = {
            viewModel.updateGender(it)
        },
        saveGender = {
            viewModel.saveGender()
        },
        isEnabled = isEnabled.value,
        onNavBack = onNavBack
    )
}

@Composable
fun MyGenderPage(
    uiState : UserInfoUiState,
    updateGender : (String) -> Unit,
    saveGender : () -> Unit,
    isEnabled : Boolean,
    onNavBack : () -> Unit,
){

    when(uiState){
        is UserInfoUiState.Loading -> {

        }
        is UserInfoUiState.UserInfo -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ){
                TopBar(
                    navIcon = painterResource(R.drawable.back_btn),
                    onNavClick = onNavBack, //뒤로 가기
                    title = "성별"
                )

                Spacer(Modifier.height(38.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(
                        modifier = Modifier.size(28.dp),
                        onClick = {
                            updateGender("female")
                        }
                    ) {
                        Icon(
                            painter = if(uiState.gender == "female") painterResource(R.drawable.checked_btn) else painterResource(R.drawable.not_checked_btn),
                            contentDescription = "Radio Button Female"
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = "여성",
                        fontSize = 16.sp,
                        color = Color(0xFF414141)
                    )

                    Spacer(Modifier.width(16.dp))

                    IconButton(
                        modifier = Modifier.size(28.dp),
                        onClick = {
                            updateGender("male")
                        }
                    ) {
                        Icon(
                            painter = if (uiState.gender == "male") painterResource(R.drawable.checked_btn) else painterResource(R.drawable.not_checked_btn),
                            contentDescription = "Radio Button Female"
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = "남성",
                        fontSize = 16.sp,
                        color = Color(0xFF414141)
                    )
                }

                Spacer(Modifier.weight(1f))

                Button(
                    isEnabled = isEnabled,
                    btnText = "변경",
                    onClick = {
                        //변경 클릭 >> 서버 업데이트
                        saveGender()
                        
                        //뒤로가기 실행
                        onNavBack()
                    }
                )
            }
        }
    }
}