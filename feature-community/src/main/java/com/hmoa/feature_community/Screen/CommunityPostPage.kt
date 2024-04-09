package com.hmoa.feature_community.Screen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category
import com.hmoa.feature_community.ViewModel.CommunityPostViewModel
import java.io.File

@Composable
fun CommunityPostRoute(
    onNavBack : () -> Unit,
    _category : String?,
    viewModel : CommunityPostViewModel = hiltViewModel()
){
    viewModel.setCategory(_category ?: "")

    val title = viewModel.title.collectAsStateWithLifecycle()
    val content = viewModel.content.collectAsStateWithLifecycle()
    val category = viewModel.category.collectAsStateWithLifecycle()
    val pictures = viewModel.pictures.collectAsStateWithLifecycle()
    val errState = viewModel.errState.collectAsStateWithLifecycle()

    PostCommunityPage(
        errState = errState.value,
        title = title.value,
        onTitleChanged = {
            viewModel.updateTitle(it)
        },
        content = content.value,
        onContentChanged = {
            viewModel.updateContent(it)
        },
        category = category.value,
        pictures = pictures.value,
        onUpdatePictures = {
            viewModel.updatePictures(it)
        },
        onDeletePictures = {
            viewModel.deletePicture(it)
        },
        onNavBack = onNavBack,
        onPostCommunity = {
            //view model의 post 사용
            viewModel.postCommunity()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun PostCommunityPage(
    errState : String?,
    title : String,
    onTitleChanged : (String) -> Unit,
    content : String,
    onContentChanged : (String) -> Unit,
    //Floating Button에서 받아와야 함
    category : Category,
    pictures : List<Uri>,
    onUpdatePictures : (List<Uri>) -> Unit,
    onDeletePictures : (Int) -> Unit,
    //뒤로가기
    onNavBack: () -> Unit,
    //해당 게시글 Post
    onPostCommunity: () -> Unit,
) {

    //갤러리에서 사진 가져오기
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {uris ->
            Log.d("TAG TEST", "uris : ${uris}")
            onUpdatePictures(uris)
        }
    )

    //pager state
    val state = rememberPagerState(
        initialPage = 0,
        pageCount = { pictures.size }
    )

    val scrollableState = rememberScrollState()

    val context = LocalContext.current

    val sideTopBarTextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Black
    )
    val mainTopBarTextStyle = TextStyle(
        fontSize = 18.sp,
        color = Color.Black
    )
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

    when(errState) {
        null -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                //unique top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.clickable{
                            onNavBack()
                        },
                        text = "취소",
                        style = sideTopBarTextStyle
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = category.name,
                        style = mainTopBarTextStyle
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        modifier = Modifier.clickable{
                            if (title.isNotEmpty() && content.isNotEmpty()){
                                onPostCommunity()
                                onNavBack()
                            } else {
                                Toast.makeText(context, "제목이나 내용을 빈 칸 없이 채워주세요",Toast.LENGTH_SHORT).show()
                            }
                        },
                        text = "확인",
                        style = sideTopBarTextStyle
                    )
                }

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
                        value = title,
                        onValueChange = {
                            //글자 수 제한 20
                            if (title.length < 20) {
                                onTitleChanged(it)
                            }
                        },
                        textStyle = titleInputTextStyle,
                        maxLines = 1,
                        singleLine = true,
                        cursorBrush = SolidColor(CustomColor.gray3)
                    ){
                        //placeholder
                        if (title.isEmpty()){
                            Text(
                                text = "제목을 입력해주세요",
                                style = placeholderTextStyle
                            )
                        }
                        it()
                    }

                    Text(
                        text = "${title.length}/20",
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
                        .scrollable(state = scrollableState, orientation = Orientation.Horizontal)
                ){
                    //content input
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        value = content,
                        onValueChange = {
                            onContentChanged(it)
                        },
                        textStyle = contentInputTextStyle,
                    ){
                        //placeholder
                        if (content.isEmpty()){
                            Text(
                                text = "내용을 입력해주세요",
                                style = placeholderTextStyle
                            )
                        }
                        it()
                    }

                    if(pictures.isNotEmpty()){

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
                                            onDeletePictures(it)
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
        else -> {
            /** err state에 대한 메세지를 띄우고 onNavBack() 하는 것이 좋을 듯 */
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPostCommunityPage() {

    var title by remember{mutableStateOf("")}
    var content by remember{mutableStateOf("")}
    var test by remember{ mutableStateOf("") }

    PostCommunityPage(
        errState = null,
        title = title,
        onTitleChanged = {
            title = it
        },
        content = content,
        onContentChanged = {
            content = it
        },
        category = Category.추천,
        onNavBack = {},
        pictures = arrayListOf(),
        onUpdatePictures = {},
        onDeletePictures = {},
        onPostCommunity = {}
    )
}