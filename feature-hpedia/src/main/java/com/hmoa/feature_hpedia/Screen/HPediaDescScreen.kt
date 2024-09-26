package com.hmoa.feature_hpedia.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_model.data.HpediaType
import com.hmoa.feature_hpedia.ViewModel.HPediaDescUiState
import com.hmoa.feature_hpedia.ViewModel.HPediaDescViewModel

@Composable
fun HPediaDescRoute(
    id : Int?,
    type : String?,
    navBack : () -> Unit,
    viewModel : HPediaDescViewModel = hiltViewModel()
){
    LaunchedEffect(true){
        viewModel.setInfo(
            type = type,
            id = id
        )
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val type = viewModel.type.collectAsStateWithLifecycle()

    HPediaDescScreen(
        type = type.value,
        uiState = uiState.value,
        navBack = navBack,
    )
}

@Composable
fun HPediaDescScreen(
    type : HpediaType,
    uiState : HPediaDescUiState,
    navBack : () -> Unit,
){
    when(uiState){
        HPediaDescUiState.Error -> {}
        HPediaDescUiState.Loading -> AppLoadingScreen()
        is HPediaDescUiState.HPediaDesc -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ){
                TopBar(
                    title = type.title,
                    navIcon = painterResource(R.drawable.ic_back),
                    onNavClick = navBack
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ){
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = uiState.title,
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = ": ${uiState.subTitle}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        color = Color.Black
                    )
                    Spacer(Modifier.height(60.dp))
                    Text(
                        text = "사전 정의",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(19.dp))
                    Text(
                        text = uiState.content,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        color = Color.Black
                    )
                }
            }
        }
    }
}