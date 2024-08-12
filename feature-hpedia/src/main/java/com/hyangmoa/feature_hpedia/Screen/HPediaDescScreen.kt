package com.hyangmoa.feature_hpedia.Screen

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyangmoa.component.TopBar
import com.hyangmoa.core_model.data.HpediaType
import com.hyangmoa.feature_hpedia.ViewModel.HPediaDescUiState
import com.hyangmoa.feature_hpedia.ViewModel.HPediaDescViewModel

@Composable
fun HPediaDescRoute(
    id : Int?,
    type : String?,
    onNavBack : () -> Unit,
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
        onNavBack = onNavBack,
    )
}

@Composable
fun HPediaDescScreen(
    type : HpediaType,
    uiState : HPediaDescUiState,
    onNavBack : () -> Unit,
){

    when(uiState){
        HPediaDescUiState.Error -> {

        }
        HPediaDescUiState.Loading -> {

        }
        is HPediaDescUiState.HPediaDesc -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ){
                TopBar(
                    title = type.title,
                    navIcon = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_back),
                    onNavClick = onNavBack
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
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = ": ${uiState.subTitle}",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(60.dp))
                    Text(
                        text = "사전 정의",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(19.dp))
                    Text(
                        text = uiState.content,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}