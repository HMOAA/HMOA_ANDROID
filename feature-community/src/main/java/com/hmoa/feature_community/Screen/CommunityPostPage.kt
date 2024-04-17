package com.hmoa.feature_community.Screen

import android.content.Context
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.BottomCameraBtn
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category
import com.hmoa.feature_community.ViewModel.CommunityPostViewModel

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

@Composable
fun PostCommunityPage(
    errState : String?,
    title : String,
    onTitleChanged : (String) -> Unit,
    content : String,
    onContentChanged : (String) -> Unit,
    category : Category,
    pictures : List<Uri>,
    onUpdatePictures : (List<Uri>) -> Unit,
    onDeletePictures : (Int) -> Unit,
    onNavBack: () -> Unit,
    onPostCommunity: () -> Unit,
) {
    val scrollableState = rememberScrollState()

    val context = LocalContext.current

    val isDataEmpty by remember{
        mutableStateOf(title.isNotEmpty() && content.isNotEmpty())
    }

    when(errState) {
        null -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                //unique top bar
                CommunityPostTopBar(
                    context = context,
                    title = category.name,
                    isDataEmpty = isDataEmpty,
                    onPostCommunity = onPostCommunity,
                    onNavBack = onNavBack
                )

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Black
                )

                TextFieldTitle(
                    title = title,
                    onTitleChanged = onTitleChanged
                )

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Black
                )

                TextFieldContent(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 33.dp, vertical = 27.dp)
                        .scrollable(state = scrollableState, orientation = Orientation.Horizontal),
                    content = content,
                    onContentChanged = onContentChanged,
                    pictures = pictures,
                    onDeletePictures = onDeletePictures
                )

                BottomCameraBtn(onUpdatePictures)
            }
        }
        else -> {
            /** err state에 대한 메세지를 띄우고 onNavBack() 하는 것이 좋을 듯 */
        }
    }
}

@Composable
fun CommunityPostTopBar(
    context : Context,
    title : String,
    isDataEmpty : Boolean,
    onPostCommunity : () -> Unit,
    onNavBack : () -> Unit
){
    val sideTopBarTextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Black
    )
    val mainTopBarTextStyle = TextStyle(
        fontSize = 18.sp,
        color = Color.Black
    )

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
            text = title,
            style = mainTopBarTextStyle
        )

        Spacer(Modifier.weight(1f))

        Text(
            modifier = Modifier.clickable{
                if (isDataEmpty){
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
}

@Composable
fun TextFieldTitle(
    title : String,
    onTitleChanged : (String) -> Unit,
){
    val titleTextIntroTextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Black
    )
    val titleInputTextStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black
    )
    val placeholderTextStyle = TextStyle(
        fontSize = 14.sp,
        color = CustomColor.gray3
    )

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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextFieldContent(
    modifier : Modifier,
    content : String,
    onContentChanged : (String) -> Unit,
    pictures : List<Uri>,
    onDeletePictures : (Int) -> Unit,
){
    //pager state
    val state = rememberPagerState(
        initialPage = 0,
        pageCount = { pictures.size }
    )

    val contentInputTextStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black
    )
    val placeholderTextStyle = TextStyle(
        fontSize = 14.sp,
        color = CustomColor.gray3
    )

    Column(
        modifier = modifier
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
}