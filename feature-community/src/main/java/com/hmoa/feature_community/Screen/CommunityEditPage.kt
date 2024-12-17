package com.hmoa.feature_community.Screen

import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.BottomCameraBtn
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.TopBarWithEvent
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.navigation.CommunityRoute
import com.hmoa.core_model.Category
import com.hmoa.feature_community.ViewModel.CommunityEditUiState
import com.hmoa.feature_community.ViewModel.CommunityEditViewModel

@Composable
fun CommunityEditRoute(
    id: Int?,
    navBack: () -> Unit,
    navCommunityDesc: (befRoute: CommunityRoute, communityId: Int) -> Unit,
    navLogin: () -> Unit,
    viewModel: CommunityEditViewModel = hiltViewModel()
) {
    //id가 null이 아니면 view model에 setting
    viewModel.setId(id)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errState by viewModel.errorUiState.collectAsStateWithLifecycle()
    val pictures = viewModel.newPictures.collectAsStateWithLifecycle()
    val onPostClick = remember{ { navCommunityDesc(CommunityRoute.CommunityEditRoute, id!!) }}

    CommunityEditPage(
        uiState = uiState,
        errState = errState,
        pictures = pictures.value,
        onUpdatePictures = viewModel::updatePictures,
        onDeletePictures = viewModel::deletePicture,
        onPostCommunity = viewModel::updateCommunity,
        navBack = navBack,
        navCommunityDesc = onPostClick,
        navLogin = navLogin
    )
}

@Composable
fun CommunityEditPage(
    uiState: CommunityEditUiState,
    errState: ErrorUiState,
    pictures: List<Uri>,
    onUpdatePictures: (List<Uri>) -> Unit,
    onDeletePictures: (Uri) -> Unit,
    onPostCommunity: (context: Context, title: String, content: String, onSuccess: () -> Unit ) -> Unit,
    navBack: () -> Unit,
    navCommunityDesc: () -> Unit,
    navLogin: () -> Unit
) {
    when (uiState) {
        CommunityEditUiState.Loading -> AppLoadingScreen()
        is CommunityEditUiState.Success -> {
            CommunityEditContent(
                category = uiState.category,
                initTitle = uiState.title,
                initContent = uiState.content,
                pictures = pictures,
                onDeletePictures = onDeletePictures,
                onUpdatePictures = onUpdatePictures,
                onPostCommunity = onPostCommunity,
                navCommunityDesc = navCommunityDesc,
                navBack = navBack
            )
        }

        CommunityEditUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navLogin,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
    }
}

@Composable
fun CommunityEditContent(
    category: Category,
    initTitle: String,
    initContent: String,
    pictures: List<Uri>,
    onDeletePictures: (Uri) -> Unit,
    onUpdatePictures: (List<Uri>) -> Unit,
    onPostCommunity: (context: Context, title: String, content: String, onSuccess: () -> Unit) -> Unit,
    navCommunityDesc: () -> Unit,
    navBack: () -> Unit,
){
    var title by remember{mutableStateOf(initTitle)}
    var content by remember{mutableStateOf(initContent)}
    val context = LocalContext.current
    val scrollableState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarWithEvent(
            onCancelClick = navBack,
            onConfirmClick = {onPostCommunity(context, title, content, navCommunityDesc)},
            title = category.name
        )
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Black)
        //title input
        EditTitleTextField(
            title = title,
            onTitleChanged = { title = it }
        )
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Black)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 33.dp, vertical = 27.dp)
                .scrollable(state = scrollableState, orientation = Orientation.Vertical)
        ) {
            EditContentTextField(
                content = content,
                onContentChanged = { content = it }
            )
        }

        if (pictures.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            EditImageViewPager(
                pictures = pictures,
                onDeletePictures = onDeletePictures
            )
        }
        BottomCameraBtn(onUpdatePictures = onUpdatePictures)
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