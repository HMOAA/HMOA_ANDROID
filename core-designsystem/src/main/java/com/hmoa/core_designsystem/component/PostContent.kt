package com.hmoa.core_designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostContent(
    modifier: Modifier = Modifier,
    width: Dp,
    onChangeBottomSheetState: () -> Unit,
    profile: String,
    nickname: String,
    dateDiff: String,
    title: String,
    content: String,
    heartCount: Int,
    isLiked: Boolean,
    onChangeLike: (isLiked: Boolean) -> Unit,
    pictures: List<String>
) {
    //pager state
    val state = rememberPagerState(
        initialPage = 0,
        pageCount = { pictures.size }
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.toFloat()
    val screenHeight = configuration.screenHeightDp.toFloat()

    var isLiked by remember{mutableStateOf(isLiked)}
    var heartCount by remember{mutableStateOf(heartCount)}

    val nicknameTextStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black,
        fontWeight = FontWeight.Normal, fontFamily = pretendard
    )
    val dateDiffTextStyle = TextStyle(
        fontSize = 12.sp,
        color = CustomColor.gray3,
        fontWeight = FontWeight.Normal, fontFamily = pretendard
    )
    val titleTextStyle = TextStyle(
        fontSize = 20.sp,
        color = Color.Black,
        fontWeight = FontWeight.Normal, fontFamily = pretendard
    )
    val contentTextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Black,
        fontWeight = FontWeight.Normal, fontFamily = pretendard
    )
    val viewNumberTextStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black,
        fontWeight = FontWeight.Normal, fontFamily = pretendard
    )

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
    ) {
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //profile
            CircleImageView(imgUrl = profile, height = 28, width = 28)

            Spacer(Modifier.width(8.dp))

            Text(text = nickname, style = nicknameTextStyle)

            Spacer(Modifier.width(7.dp))

            Text(text = dateDiff, style = dateDiffTextStyle)

            Spacer(Modifier.weight(1f))

            //menu (isWritten true >> 수정, 삭제, 취소 / false >> 신고하기, 취소
            IconButton(
                onClick = { onChangeBottomSheetState() }
            ) {
                Image(modifier=Modifier.size(20.dp),painter = painterResource(R.drawable.three_dot_menu_vertical), contentDescription = null)
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.question_ic),
                contentDescription = "Question Icon"
            )

            Spacer(Modifier.width(8.dp))

            Text(text = title, style = titleTextStyle)
        }

        Spacer(Modifier.height(15.dp))

        Text(text = content,style = contentTextStyle)

        if(pictures.isNotEmpty()){
            Spacer(Modifier.height(17.dp))

            HorizontalPager(
                modifier = Modifier
                    .width(274.dp)
                    .height(304.dp)
                    .align(Alignment.CenterHorizontally),
                state = state
            ) {
                Column(modifier = Modifier.fillMaxSize()){
                    ExpandableImage(
                        modifier = Modifier.requiredWidth(width),
                        picture = pictures[it],
                        width = screenWidth,
                        height = screenHeight
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally)
            ){
                for(i in pictures.indices){
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(color = if (i == state.currentPage) Color.Black else CustomColor.blackTrans30, shape = CircleShape)
                            .clip(CircleShape)
                    )
                }
            }
        }

        Spacer(Modifier.height(17.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if(isLiked) heartCount-- else heartCount++
                    onChangeLike(isLiked)
                    isLiked = !isLiked
                }
            ) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(R.drawable.ic_heart_filled),
                    tint = if (isLiked) CustomColor.red else CustomColor.gray2,
                    contentDescription = "Like"
                )
            }

            Text(text = if (heartCount > 999) "999+" else heartCount.toString(), style = viewNumberTextStyle)
        }

        Spacer(Modifier.height(14.dp))
    }
}

@Composable
private fun ExpandableImage(
    modifier: Modifier,
    width: Float,
    height: Float,
    picture: String
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false }
        ) {
            Box(
                modifier = modifier
                    .width(width.dp)
                    .height(height.dp),
                contentAlignment = Alignment.Center
            ) {
                ImageView(
                    imageUrl = picture,
                    width = 1f,
                    height = 1f,
                    backgroundColor = Color.Black,
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, top = 19.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { showDialog = false }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Dialog",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .size(274.dp)
            .background(color = CustomColor.gray1)
            .pointerInput(Unit) {
                detectTapGestures {
                    showDialog = !showDialog
                }
            },
        contentAlignment = Alignment.Center
    ) {
        //image view
        ImageView(
            imageUrl = picture,
            width = 1f,
            height = 1f,
            backgroundColor = CustomColor.gray1,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
fun PostContentPreview() {
    PostContent(
        width = 300.dp,
        onChangeBottomSheetState = {},
        profile = "?",
        nickname = "String",
        dateDiff = "76",
        title = "안녕하셈",
        content = "반갑습니다 ㅎㅎ",
        heartCount = 3,
        isLiked = false,
        onChangeLike = {},
        pictures = emptyList()
    )
}