package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.*
import com.hmoa.feature_hbti.viewmodel.HbtiProcessUiState
import com.hmoa.feature_hbti.viewmodel.HbtiProcessViewmodel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HbtiProcessRoute(navLogin: () -> Unit, onBackClick: () -> Unit, onNextClick: () -> Unit) {
    HbtiProcessScreen(
        navLogin = navLogin,
        onBackClick = { onBackClick() },
        onNextClick = { onNextClick() })
}

@Composable
private fun HbtiProcessScreen(
    navLogin: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: HbtiProcessViewmodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorState by viewModel.errorUiState.collectAsStateWithLifecycle()

    ErrorUiSetView(
        onLoginClick = navLogin,
        errorUiState = errorState,
        onCloseClick = onBackClick
    )

    when (uiState) {
        HbtiProcessUiState.Error -> {}
        HbtiProcessUiState.Loading -> {
            AppLoadingScreen()
        }

        is HbtiProcessUiState.Success -> {
            HbtiProcessContent(
                onBackClick,
                onNextClick,
                (uiState as HbtiProcessUiState.Success).titles.toImmutableList(),
                (uiState as HbtiProcessUiState.Success).contents.toImmutableList(),
                (uiState as HbtiProcessUiState.Success).descriptionUrl
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HbtiProcessContent(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    titles: ImmutableList<String>,
    contents: ImmutableList<String>,
    imgUrl: String,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White).padding(bottom = 40.dp),
    ) {
        LazyColumn {
            stickyHeader {
                TopBar(
                    color = Color.White,
                    title = "향BTI",
                    titleColor = Color.Black,
                    navIcon = painterResource(R.drawable.ic_back),
                    onNavClick = { onBackClick() }
                )
            }
            itemsIndexed(listOf("OrderSteps", "ImgUrl")) { idx, item ->
                when (idx) {
                    0 -> {
                        Column(modifier = Modifier.padding(top = 22.dp).padding(horizontal = 16.dp)) {
                            VerticalStepBar(
                                titles = titles,
                                contents = contents
                            )
                        }
                    }

                    1 -> {
                        Column(modifier = Modifier.padding(top = 17.dp).padding(horizontal = 22.dp)) {
                            ImageView(
                                imageUrl = imgUrl,
                                backgroundColor = Color.White,
                                contentScale = ContentScale.Inside,
                                width = 1f,
                                height = 1f
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                .background(color = Color.White)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                isEnabled = true,
                btnText = "추천받은 향료를 시향해보세요",
                onClick = { onNextClick() },
                buttonModifier = Modifier.fillMaxWidth(1f).height(52.dp).background(color = Color.Black),
                textSize = 18,
                textColor = Color.White,
                radious = 5
            )
        }
    }
}

@Preview
@Composable
private fun HbtiProcessScreenPreview() {
    HbtiProcessScreen(navLogin = {}, onBackClick = {}, onNextClick = {})
}
