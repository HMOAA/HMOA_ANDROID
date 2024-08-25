package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.gson.GsonBuilder
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.NoteImageView
import com.hmoa.core_designsystem.theme.pretendard
import com.hmoa.core_model.data.NoteProductIds
import com.hmoa.core_model.data.NoteSelect
import com.hmoa.core_model.response.ProductListResponseDto
import com.hmoa.feature_hbti.viewmodel.NotePickUiState
import com.hmoa.feature_hbti.viewmodel.NotePickViewmodel

@Composable
fun NotePickRoute(
    onBackClick: () -> Unit,
    onNextClick: (productIdsToJson: String) -> Unit,
    noteOrderQuantity: Int?,
    onErrorHandleLoginAgain: () -> Unit,
) {
    NotePickScreen(
        onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
        onBackClick = { onBackClick() },
        onNextClick = onNextClick,
        noteOrderQuantity
    )
}

@Composable
fun NotePickScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: (productIdsToJson: String) -> Unit,
    noteOrderQuantity: Int?,
    viewmodel: NotePickViewmodel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val isNextAvailable by viewmodel.isCompletedNoteSelected.collectAsStateWithLifecycle()
    val errorUiState by viewmodel.errorUiState.collectAsStateWithLifecycle()
    val selectedProductIds by viewmodel.selectedIds.collectAsStateWithLifecycle(emptyList())

    ErrorUiSetView(
        onConfirmClick = { onErrorHandleLoginAgain() },
        errorUiState = errorUiState,
        onCloseClick = { onBackClick() }
    )

    LaunchedEffect(isNextAvailable) {
        if (isNextAvailable) {
            val navigationDto = NoteProductIds(productIds = selectedProductIds)
            val gson = GsonBuilder().create()
            val productIdsToJson = gson.toJson(navigationDto)
            onNextClick(productIdsToJson)
        }
    }

    when (uiState) {
        NotePickUiState.Loading -> AppLoadingScreen()
        is NotePickUiState.NotePickData -> NoteContent(
            topRecommendedNote = (uiState as NotePickUiState.NotePickData).topRecommendedNote,
            noteList = (uiState as NotePickUiState.NotePickData).noteProductList,
            noteOrderQuantity = noteOrderQuantity ?: 0,
            selectedNotesOrderQuantity = (uiState as NotePickUiState.NotePickData).noteOrderIndex,
            isNoteSelectedList = (uiState as NotePickUiState.NotePickData).noteSelectData,
            onBackClick = { onBackClick() },
            onClickItem = { index: Int, value: Boolean, data: NoteSelect, noteOrderQuantity: Int, selectedNotesOrderQuantity: Int ->
                viewmodel.handleNoteSelectData(index, value, data, noteOrderQuantity, selectedNotesOrderQuantity)
            },
            onNextClick = { viewmodel.postNoteSelected() }
        )
    }
}

@Composable
fun NoteContent(
    topRecommendedNote: String,
    noteList: ProductListResponseDto?,
    noteOrderQuantity: Int,
    selectedNotesOrderQuantity: Int,
    isNoteSelectedList: List<NoteSelect>,
    onClickItem: (index: Int, value: Boolean, data: NoteSelect, noteOrderQuantity: Int, selectedNotesOrderQuantity: Int) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "향BTI",
            titleColor = Color.Black,
            navIcon = painterResource(R.drawable.ic_back),
            onNavClick = { onBackClick() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    "추천받은 카테고리는 '${topRecommendedNote}'입니다.\n원하는 카테고리 배송 수량을\n선택해주세요",
                    modifier = Modifier.padding(bottom = 32.dp, top = 36.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = pretendard
                    )
                )
                NotePickGridWindow(
                    notes = noteList,
                    noteOrderQuantity = noteOrderQuantity,
                    selectedNotesOrderQuantity = selectedNotesOrderQuantity,
                    isNoteSelectedList = isNoteSelectedList,
                    onClickItem = onClickItem
                )
            }
            Button(
                isEnabled = true,
                btnText = "다음",
                onClick = { onNextClick() },
                buttonModifier = Modifier
                    .fillMaxWidth(1f)
                    .height(52.dp)
                    .background(color = Color.Black),
                textSize = 18,
                textColor = Color.White,
                radious = 5
            )
        }
    }
}

@Composable
fun NotePickGridWindow(
    notes: ProductListResponseDto?,
    noteOrderQuantity: Int,
    selectedNotesOrderQuantity: Int,
    isNoteSelectedList: List<NoteSelect>,
    onClickItem: (index: Int, value: Boolean, data: NoteSelect, noteOrderQuantity: Int, selectedNotesOrderQuantity: Int) -> Unit
) {
    if (notes?.data == null) {
        Text("데이터가 없습니다")
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(3), verticalArrangement = Arrangement.SpaceBetween) {
            itemsIndexed(notes?.data!!) { index, item ->
                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NoteImageView(
                        imageUrl = item.productPhotoUrl,
                        width = 74f,
                        height = 74f,
                        backgroundColor = Color.Transparent,
                        contentScale = ContentScale.Crop,
                        onClicked = {
                            onClickItem(
                                index,
                                !isNoteSelectedList[index].isSelected,
                                isNoteSelectedList[index],
                                noteOrderQuantity,
                                selectedNotesOrderQuantity
                            )

                        },
                        isRecommanded = item.isRecommended,
                        index = isNoteSelectedList[index].selectedIndex,
                        isSelected = isNoteSelectedList[index].isSelected
                    )
                    Text(
                        text = item.productName,
                        style = TextStyle(fontFamily = pretendard, fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                        modifier = Modifier.padding(top = 12.dp, bottom = 5.dp)
                    )
                    Text(
                        text = item.productDetails,
                        style = TextStyle(fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 12.sp)
                    )
                }
            }
        }
    }
}
