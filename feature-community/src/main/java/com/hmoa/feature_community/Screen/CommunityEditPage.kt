package com.hmoa.feature_community.Screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.TopBarWithEvent
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_community.ViewModel.CommunityEditUiState
import com.hmoa.feature_community.ViewModel.CommunityEditViewModel

@Composable
fun CommunityEditRoute(
    id : Int?,
    onNavBack : () -> Unit,
    viewModel : CommunityEditViewModel = hiltViewModel()
){

    //id가 null이 아니면 view model에 setting
    viewModel.setId(id ?: -1)

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    CommunityEditPage(
        uiState = uiState.value,
        onTitleChanged = {
            viewModel.updateTitle(it)
        },
        onContentChanged = {
            viewModel.updateContent(it)
        },
        onUpdatePictures = {
            viewModel.updatePictures(it)
        },
        onDeletePictures = {
            viewModel.deletePicture(it)
        },
        onNavBack = onNavBack,
        onPostCommunity = {
            //view model의 update community 사용
            viewModel.updateCommunity()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommunityEditPage(
    uiState : CommunityEditUiState,
    onTitleChanged : (String) -> Unit,
    onContentChanged : (String) -> Unit,
    onUpdatePictures : (List<Uri>) -> Unit,
    onDeletePictures : (Uri) -> Unit,
    onNavBack : () -> Unit,
    onPostCommunity : () -> Unit,
){
    //갤러리에서 사진 가져오기
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {uris ->
            onUpdatePictures(uris)
        }
    )

    val scrollableState = rememberScrollState()

    val titleTextIntroTextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Black
    )
    val titleInputTextStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black
    )
    val contentInputTextStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black
    )
    val placeholderTextStyle = TextStyle(
        fontSize = 14.sp,
        color = CustomColor.gray3
    )

    when (uiState) {
        is CommunityEditUiState.Loading -> {

        }
        is CommunityEditUiState.Community -> {
            //pager state
            val state = rememberPagerState(
                initialPage = 0,
                pageCount = { uiState.pictures.size }
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ){
                TopBarWithEvent(
                    onCancelClick = onNavBack,
                    onConfirmClick = {
                        onPostCommunity()
                        onNavBack()
                    },
                    title = uiState.category!!.name
                )

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Black
                )

                //title input
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(start = 33.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "제목:",
                        style = titleTextIntroTextStyle
                    )

                    Spacer(Modifier.width(6.dp))

                    BasicTextField(
                        modifier = Modifier.weight(1f),
                        value = uiState.title,
                        onValueChange = {
                            //글자 수 제한 20
                            if (uiState.title.length <= 20) {
                                onTitleChanged(it)
                            }
                        },
                        textStyle = titleInputTextStyle,
                        maxLines = 1,
                        singleLine = true,
                    ){
                        //placeholder
                        if (uiState.content.isEmpty()){
                            Text(
                                text = "제목을 입력해주세요",
                                style = placeholderTextStyle
                            )
                        }
                    }

                    Text(
                        text = "${uiState.title.length}/20",
                        style = titleInputTextStyle
                    )
                }

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Black
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 33.dp, vertical = 27.dp)
                        .scrollable(state = scrollableState, orientation = Orientation.Vertical)
                ){
                    //content input
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        value = uiState.content,
                        onValueChange = {
                            onContentChanged(it)
                        },
                        textStyle = contentInputTextStyle,
                    ){
                        //placeholder
                        if (uiState.content.isEmpty()){
                            Text(
                                text = "내용을 입력해주세요",
                                style = placeholderTextStyle
                            )
                        }
                    }
                }

                if(uiState.pictures.isNotEmpty()){

                    Spacer(Modifier.height(10.dp))

                    HorizontalPager(
                        modifier = Modifier.size(274.dp),
                        state = state
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ){
                            //image view
                            ImageView(
                                imageUrl = uiState.pictures[it].toString(),
                                width = 274f,
                                height = 274f,
                                backgroundColor = CustomColor.gray1,
                                contentScale = ContentScale.Fit
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
                                        onDeletePictures(uiState.pictures[it])
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier.fillMaxSize(),
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Delete Button",
                                        tint = CustomColor.red
                                    )
                                }
                            }
                        }
                    }

                    /** keyboard 옵션으로 보이도록 View 변경해야 함 */
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {
                            multiplePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomColor.gray1
                        ),
                        shape = RectangleShape
                    ){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "사진을 추가하려면 눌러주세요!",
                            textAlign = TextAlign.Center,
                            color = Color.Blue,
                            fontSize = 20.sp
                        )
                    }

                }
            }
        }
        is CommunityEditUiState.Error -> {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestCommunityEditPage(){
    CommunityEditPage(
        uiState = CommunityEditUiState.Loading,
        onTitleChanged = {

        },
        onContentChanged = {

        },
        onNavBack = {

        },
        onPostCommunity = {

        },
        onUpdatePictures = {},
        onDeletePictures = {}
    )
}