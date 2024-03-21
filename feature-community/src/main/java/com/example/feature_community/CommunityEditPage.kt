package com.example.feature_community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_community.ViewModel.CommunityEditUiState
import com.example.feature_community.ViewModel.CommunityEditViewModel
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category

@Composable
fun CommunityEditRoute(
    id : Int?,
    onNavBack : () -> Unit,
    viewModel : CommunityEditViewModel = hiltViewModel()
){

    if (id != null) {

        //id가 null이 아니면 view model에 setting
        viewModel.setId(id)

        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        CommunityEditPage(
            uiState = uiState.value,
            onTitleChanged = {
                viewModel.updateTitle(it)
            },
            onContentChanged = {
                viewModel.updateContent(it)
            },
            onNavBack = onNavBack,
            onPostCommunity = {
                //view model의 update community 사용
                viewModel.updateCommunity()
            }
        )

    } else {
        /** 여기서 id가 null일 때 처리 */
    }
}

@Composable
fun CommunityEditPage(
    uiState : CommunityEditUiState,
    onTitleChanged : (String) -> Unit,
    onContentChanged : (String) -> Unit,
    //뒤로가기
    onNavBack : () -> Unit,
    //해당 게시글 Post
    onPostCommunity : () -> Unit,
){
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

    when (uiState) {
        is CommunityEditUiState.Loading -> {

        }
        is CommunityEditUiState.Community -> {
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
                        text = uiState.category!!.name,
                        style = mainTopBarTextStyle
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        modifier = Modifier.clickable{
                            onPostCommunity()
                            onNavBack()
                        },
                        text = "확인",
                        style = sideTopBarTextStyle
                    )
                }

                Divider(
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

                Divider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Black
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 33.dp, vertical = 27.dp)
                ){
                    //content input
                    BasicTextField(
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

                /** 사진 추가가 된다면 선택된 사진을 받아올 View
                 * 글이 길어지게 된다면 Scroll을 사용해서 사진은 최하단에 맞춰 추가 */

                /** 사진 추가가 된다면 선택된 사진을 받아올 View
                 * 글이 길어지게 된다면 Scroll을 사용해서 사진은 최하단에 맞춰 추가 */
                Box(

                ){

                }

                /** 원래는 키보드에 달려있었는데
                 * 안드로이드에서 기본 옵션에 카메라를 넣는 것은 불가능
                 * 따라서 카메라를 따로 추가하는 방향으로 진행할 것 같음 */

                /** 원래는 키보드에 달려있었는데
                 * 안드로이드에서 기본 옵션에 카메라를 넣는 것은 불가능
                 * 따라서 카메라를 따로 추가하는 방향으로 진행할 것 같음 */

            }
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

        }
    )
}