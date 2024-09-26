package com.hmoa.feature_home.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.PerfumeItemView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_domain.entity.data.AllPerfumeScreenId
import com.hmoa.feature_home.viewmodel.AllPerfumeViewModel

fun String?.mapToAllPerfumeScreenId(): AllPerfumeScreenId {
    var result: AllPerfumeScreenId? = null
    when (this) {
        AllPerfumeScreenId.First.name -> result = AllPerfumeScreenId.First
        AllPerfumeScreenId.Second.name -> result = AllPerfumeScreenId.Second
        AllPerfumeScreenId.Third.name -> result = AllPerfumeScreenId.Third
        else -> {
            Log.e("AllPerfumeScreen", "Wrong AllPerfumeScreen value")
            throw Exception("Wrong AllPerfumeScreen value")
        }
    }
    return result
}

@Composable
fun AllPerfumeRoute(
    screenId: String?,
    onNavBack: () -> Unit,
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onNavLogin: () -> Unit
) {
    AllPerfumeScreen(
        onNavBack = onNavBack,
        onPerfumeClick = { onPerfumeClick(it) },
        onErrorHandleLoginAgain = onNavLogin,
        screenId = screenId.mapToAllPerfumeScreenId()
    )
}

@Composable
fun AllPerfumeScreen(
    onNavBack: () -> Unit,
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
    screenId: AllPerfumeScreenId,
    viewmodel: AllPerfumeViewModel = hiltViewModel()
) {
    val isOpen by remember { mutableStateOf(true) }
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewmodel.errorUiState.collectAsStateWithLifecycle()

    LaunchedEffect(screenId) {
        viewmodel.getPerfumeByScreenId(screenId)
    }

    when (uiState) {
        is AllPerfumeViewModel.AllPerfumeUiState.Data -> {
            AllPerfumeContent(
                onNavBack = onNavBack,
                perfumeList = (uiState as AllPerfumeViewModel.AllPerfumeUiState.Data).perfumeList,
                onPerfumeClick = { onPerfumeClick(it) }
            )
        }

        AllPerfumeViewModel.AllPerfumeUiState.Error -> {
            ErrorUiSetView(
                isOpen = isOpen,
                onConfirmClick = { onErrorHandleLoginAgain() },
                errorUiState = errorUiState,
                onCloseClick = { onNavBack() }
            )
        }

        AllPerfumeViewModel.AllPerfumeUiState.Loading -> {
            AppLoadingScreen()
        }
    }

}

@Composable
fun AllPerfumeContent(
    onNavBack: () -> Unit,
    perfumeList: List<HomeMenuAllResponseDto>?,
    onPerfumeClick: (perfumeId: Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth().padding(start = 16.dp)) {
            TopBar(
                title = "전체보기",
                iconSize = 25.dp,
                navIcon = painterResource(R.drawable.ic_back),
                onNavClick = { onNavBack() },
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray2))
        Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp).padding(vertical = 10.dp)) {
            PerfumeAllList(perfumeList = perfumeList, onClickPerfume = { onPerfumeClick(it) })
        }
    }
}


@Composable
fun PerfumeAllList(
    perfumeList: List<HomeMenuAllResponseDto>?,
    onClickPerfume: (perfumeId: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center
    ) {
        items(perfumeList ?: emptyList()) {
            Column(modifier = Modifier.clickable { onClickPerfume(it.perfumeId) }
                .padding(bottom = 16.dp)) {
                PerfumeItemView(
                    imageUrl = it.imgUrl ?: "",
                    perfumeName = it.perfumeName ?: "",
                    brandName = it.brandName ?: "",
                    containerWidth = 160,
                    containerHeight = 160,
                    imageWidth = 0.7f,
                    imageHeight = 0.7f,
                    imageBackgroundColor = CustomColor.gray1,
                    imageBorderStroke = BorderStroke(width = 1.dp, color = CustomColor.gray1)
                )
            }
        }
    }
}
