package com.hmoa.feature_community.Screen

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.BottomCameraBtn
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.TopBarWithEvent
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_community.ViewModel.CommunityEditUiState
import com.hmoa.feature_community.ViewModel.CommunityEditViewModel

@Composable
fun CommunityEditRoute(
    id: Int?,
    navBack: () -> Unit,
    navCommunityDesc: (Int) -> Unit,
    viewModel: CommunityEditViewModel = hiltViewModel()
) {
    //id가 null이 아니면 view model에 setting
    viewModel.setId(id)

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val title = viewModel.title.collectAsStateWithLifecycle()
    val content = viewModel.content.collectAsStateWithLifecycle()
    val pictures = viewModel.newPictures.collectAsStateWithLifecycle()
    val category = viewModel.category.collectAsStateWithLifecycle()

    CommunityEditPage(
        uiState = uiState.value,
        category = category.value?.name,
        title = title.value,
        onTitleChanged = {
            viewModel.updateTitle(it)
        },
        content = content.value,
        onContentChanged = {
            viewModel.updateContent(it)
        },
        pictures = pictures.value,
        onUpdatePictures = {
            viewModel.updatePictures(it)
        },
        onDeletePictures = {
            viewModel.deletePicture(it)
        },
        onPostCommunity = {
            //view model의 update community 사용
            viewModel.updateCommunity()
        },
        navBack = navBack,
        navCommunityDesc = { navCommunityDesc(id!!) }
    )
}

@Composable
fun CommunityEditPage(
    uiState: CommunityEditUiState,
    category: String?,
    title: String,
    onTitleChanged: (String) -> Unit,
    content: String,
    onContentChanged: (String) -> Unit,
    pictures: List<Uri>,
    onUpdatePictures: (List<Uri>) -> Unit,
    onDeletePictures: (Uri) -> Unit,
    onPostCommunity: () -> Unit,
    navBack: () -> Unit,
    navCommunityDesc: () -> Unit
) {
    when (uiState) {
        CommunityEditUiState.Loading -> AppLoadingScreen()
        CommunityEditUiState.Success -> {
            val scrollableState = rememberScrollState()

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBarWithEvent(
                    onCancelClick = navBack,
                    onConfirmClick = {
                        onPostCommunity()
                        navCommunityDesc()
                    },
                    title = category!!
                )
                HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Black)
                //title input
                EditTitleTextField(
                    title = title,
                    onTitleChanged = onTitleChanged
                )
                HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Black)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 33.dp, vertical = 27.dp)
                        .scrollable(state = scrollableState, orientation = Orientation.Vertical)
                ) {
                    //content input
                    EditContentTextField(
                        content = content,
                        onContentChanged = onContentChanged
                    )
                }

                if (pictures.isNotEmpty()) {

                    Spacer(Modifier.height(10.dp))

                    EditImageViewPager(
                        pictures = pictures,
                        onDeletePictures = onDeletePictures
                    )
                }
                BottomCameraBtn(
                    onUpdatePictures = onUpdatePictures
                )
            }
        }

        CommunityEditUiState.Error -> {
            Text("Error")
        }
    }
}

@Composable
private fun EditTitleTextField(
    title: String,
    onTitleChanged: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(start = 33.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "제목:",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )

        Spacer(Modifier.width(6.dp))

        BasicTextField(
            modifier = Modifier.weight(1f),
            value = title,
            onValueChange = {
                //글자 수 제한 20
                if (title.length <= 20) {
                    onTitleChanged(it)
                }
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            ),
            maxLines = 1,
            singleLine = true,
        ) {
            //placeholder
            if (title.isEmpty()) {
                Text(
                    text = "제목을 입력해주세요",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    color = CustomColor.gray3
                )
            }
            it()
        }

        Text(
            text = "${title.length}/20",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black,
        )
    }
}

@Composable
private fun EditContentTextField(
    content: String,
    onContentChanged: (String) -> Unit
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxSize(),
        value = content,
        onValueChange = {
            onContentChanged(it)
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
    ) {
        //placeholder
        if (content.isEmpty()) {
            Text(
                text = "내용을 입력해주세요",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                color = CustomColor.gray3
            )
        }
        it()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EditImageViewPager(
    pictures: List<Uri>,
    onDeletePictures: (Uri) -> Unit
) {
    //pager state
    val state = rememberPagerState(
        initialPage = 0,
        pageCount = { pictures.size }
    )

    HorizontalPager(
        modifier = Modifier.size(274.dp),
        state = state
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            //image view
            ImageView(
                imageUrl = pictures[it].toString(),
                width = 274f,
                height = 274f,
                backgroundColor = CustomColor.gray1,
                contentScale = ContentScale.FillWidth
            )

            //삭제 버튼
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .background(color = Color.White, shape = CircleShape)
                        .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                        .clip(CircleShape),
                    onClick = {
                        onDeletePictures(pictures[it])
                    }
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Delete Button",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}