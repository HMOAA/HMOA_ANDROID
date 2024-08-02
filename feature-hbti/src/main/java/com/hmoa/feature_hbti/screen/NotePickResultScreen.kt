package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.NoteSelectedDescription
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.Note
import com.hmoa.core_model.response.NoteProduct
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.feature_hbti.viewmodel.NotePickResultState
import com.hmoa.feature_hbti.viewmodel.NotePickResultViewModel

@Composable
fun NotePickResultRoute(
    productIds: List<Int>,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: NotePickResultViewModel = hiltViewModel()
){
    viewModel.setNoteIds(productIds)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    NotePickResultScreen(
        uiState = uiState.value,
        errState = errState.value,
        onBackClick = onBackClick,
        onNextClick = onNextClick
    )
}

@Composable
fun NotePickResultScreen(
    uiState: NotePickResultState,
    errState: ErrorUiState,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
){
    when(uiState){
        NotePickResultState.Loading -> AppLoadingScreen()
        is NotePickResultState.Success -> {
            NotePickResultMainContent(
                notes = uiState.result.noteProducts,
                totalPrice = uiState.result.totalPrice,
                onBackClick = onBackClick,
                onNextClick = onNextClick,
            )
        }
        is NotePickResultState.Error -> {
            ErrorUiSetView(
                onConfirmClick = onBackClick, /**여기 Hbti Screen으로 navigation 하는 건 어떨까*/
                errorUiState = errState,
                onCloseClick = onBackClick /**여기 Hbti Screen으로 navigation 하는 건 어떨까*/
            )
        }
    }
}

@Composable
private fun NotePickResultMainContent(
    notes: List<NoteProduct>,
    totalPrice: Int,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "향BTI",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onBackClick
        )
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            item{
                Text(
                    text = "선택한 향료",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold))
                )
                Spacer(Modifier.height(19.dp))
            }
            items(notes){note ->
                NoteSelectedDescription(
                    imgUrl = note.productPhotoUrl,
                    imgSize = 66,
                    productName = note.productName,
                    price = note.price,
                    categoryNumber = note.notesCount,
                    notes = note.notes
                )
            }
            item{
                Spacer(Modifier.height(6.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "총 금액 : ${totalPrice}",
                    textAlign = TextAlign.End,
                    fontSize = 15.sp,
                    fontFamily = CustomFont.regular
                )
                Spacer(Modifier.weight(1f).defaultMinSize(minHeight = 18.dp))
                Button(
                    buttonModifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    isEnabled = true,
                    btnText = "다음",
                    onClick = onNextClick,
                    radious = 5
                )
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UITest(){
    val testData = PostNoteSelectedResponseDto(
        noteProducts = listOf(
            NoteProduct(
                notes = listOf(
                    Note(
                        noteName = "통카빈",
                        noteContent = "열대 나무 씨앗을 말린 향신료"
                    ),
                    Note(
                        noteName = "넛맥",
                        noteContent = "넛맥이란 식물을 말린 향신료"
                    ),
                    Note(
                        noteName = "페퍼",
                        noteContent = "후추"
                    )
                ),
                notesCount = 3,
                price = 4800,
                productId = 0,
                productName = "프루트",
                productPhotoUrl = ""
            ),
            NoteProduct(
                notes = listOf(
                    Note(
                        noteName = "통카빈",
                        noteContent = "열대 나무 씨앗을 말린 향신료"
                    ),
                    Note(
                        noteName = "넛맥",
                        noteContent = "넛맥이란 식물을 말린 향신료"
                    ),
                    Note(
                        noteName = "페퍼",
                        noteContent = "후추"
                    )
                ),
                notesCount = 6,
                price = 4800,
                productId = 0,
                productName = "플로럴",
                productPhotoUrl = ""
            ),
            NoteProduct(
                notes = listOf(
                    Note(
                        noteName = "통카빈",
                        noteContent = "열대 나무 씨앗을 말린 향신료"
                    ),
                    Note(
                        noteName = "넛맥",
                        noteContent = "넛맥이란 식물을 말린 향신료"
                    ),
                    Note(
                        noteName = "페퍼",
                        noteContent = "후추"
                    )
                ),
                notesCount = 6,
                price = 6000,
                productId = 0,
                productName = "시트러스",
                productPhotoUrl = ""
            )
        ),
        totalPrice = 15600
    )
    NotePickResultScreen(
        uiState = NotePickResultState.Success(result = testData),
        errState = ErrorUiState.Loading,
        onBackClick = {},
        onNextClick = {}
    )
}