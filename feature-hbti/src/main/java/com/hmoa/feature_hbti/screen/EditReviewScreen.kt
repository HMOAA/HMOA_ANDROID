package com.hmoa.feature_hbti.screen

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.BottomCameraBtn
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.feature_hbti.viewmodel.EditReviewUiState
import com.hmoa.feature_hbti.viewmodel.EditReviewViewModel
import kotlinx.coroutines.launch

@Composable
fun EditReviewRoute(
    reviewId: Int?,
    navBack: () -> Unit,
    viewModel: EditReviewViewModel = hiltViewModel()
){
    val isDone by viewModel.isDone.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()
    val onOkClick = remember<(images: List<String>, content: String) -> Unit>{
        { images, content -> viewModel.editReview(images = images, content = content) }
    }
    val context = LocalContext.current
    val uriToString = remember<(uri: Uri) -> String>{
        { uri -> viewModel.transformUriToString(context, uri)}
    }
    EditReviewScreen(
        uiState = uiState,
        errState = errorUiState,
        navBack = navBack,
        uriToString = uriToString,
        onCancelClick = navBack,
        onOkClick = onOkClick
    )
    LaunchedEffect(isDone){if(isDone) navBack()}
}

@Composable
fun EditReviewScreen(
    uiState: EditReviewUiState,
    errState: ErrorUiState,
    navBack: () -> Unit,
    uriToString: (uri: Uri) -> String,
    onCancelClick: () -> Unit,
    onOkClick: (images: List<String>, content: String) -> Unit,
){
    when(uiState){
        EditReviewUiState.Loading -> AppLoadingScreen()
        EditReviewUiState.Error -> {
            ErrorUiSetView(
                isOpen = true,
                onConfirmClick = navBack,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
        is EditReviewUiState.Success -> {
            EditReviewContent(
                pictures = uiState.photoUris,
                content = uiState.content,
                onCancelClick = onCancelClick,
                onOkClick = onOkClick,
                uriToString = uriToString
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EditReviewContent(
    pictures: List<String>,
    content: String,
    onCancelClick: () -> Unit,
    onOkClick: (images: List<String>, content: String) -> Unit,
    uriToString: (uri: Uri) -> String
){
    val pictures = remember{ mutableStateListOf(*pictures.toTypedArray()) }
    val state = rememberPagerState(initialPage = 0, pageCount = {pictures.size})
    val coroutine = rememberCoroutineScope()
    var content by remember{ mutableStateOf(content) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextTopBar(
            onCancelClick  = onCancelClick,
            onOkClick = { onOkClick(pictures, content) }
        )
        Spacer(Modifier.height(24.dp))
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            value = content,
            onValueChange = {content = it},
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = CustomFont.regular,
                fontSize = 14.sp
            ),
            cursorBrush = SolidColor(Color.White)
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ){
                if(content.isEmpty()){
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = "리뷰를 작성해주세요",
                        color = Color.White,
                        fontFamily = CustomFont.regular,
                        fontSize = 14.sp
                    )
                } else {
                    it()
                }
            }
        }
        Spacer(Modifier.weight(1f))

        if(pictures.isNotEmpty()){

            Spacer(Modifier.height(10.dp))

            HorizontalPager(
                modifier = Modifier.size(274.dp),
                state = state,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    //image view
                    ImageView(
                        imageUrl = pictures[it].toString(),
                        width = 274f,
                        height = 274f,
                        backgroundColor = CustomColor.gray1,
                        contentScale = ContentScale.Crop
                    )

                    //삭제 버튼
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 15.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top
                    ){
                        IconButton(
                            modifier = Modifier.size(24.dp),
                            onClick = {
                                coroutine.launch{
                                    state.animateScrollToPage(it-1)
                                    pictures.remove(pictures[it])
                                }
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.Black, shape = CircleShape),
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Delete Button",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .width(274.dp)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally)
            ){
                pictures.map{
                    val selected = pictures[state.currentPage] == it
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (selected) Color.White else CustomColor.gray6,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
        Spacer(Modifier.height(27.dp))
        BottomCameraBtn(
            isColorInverted = false,
            onUpdatePictures = { newPictures ->
                newPictures.map{
                    val uri = uriToString(it)
                    if (uri !in pictures){
                        pictures.add(uri)
                    }
                }
            }
        )
    }
}

@Composable
private fun TextTopBar(
    onCancelClick: () -> Unit,
    onOkClick: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = CustomColor.gray4),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextButton(
            onClick = onCancelClick
        ){
            Text(
                text = "취소",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = CustomFont.regular
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = "향BTI 후기",
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = CustomFont.regular
        )
        Spacer(Modifier.weight(1f))
        TextButton(
            onClick = onOkClick
        ){
            Text(
                text = "확인",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = CustomFont.regular
            )
        }
    }
}