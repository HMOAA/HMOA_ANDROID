package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import kotlinx.coroutines.delay

@Composable
fun ReviewItem(
    isItemClickable: Boolean,
    reviewId: Int,
    profileImg: String,
    nickname: String,
    writtenAt: String,
    isLiked: Boolean,
    heartNumber: Int,
    content: String,
    images: List<String>,
    category: String,
    onHeartClick: (reviewId: Int, isLiked: Boolean) -> Unit,
    onMenuClick: () -> Unit,
    onItemClick: () -> Unit,
){
    var isLiked by remember{mutableStateOf(isLiked)}
    var heartNumber by remember{mutableStateOf(heartNumber)}
    var isDelayed by remember{mutableStateOf(true)}
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = CustomColor.gray5, shape = RoundedCornerShape(size = 5.dp))
            .clickable { if (isItemClickable) onItemClick() }
            .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 10.dp),
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            CircleImageView(
                imgUrl = profileImg,
                width = 28,
                height = 28
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = nickname,
                fontSize = 14.sp,
                color = CustomColor.gray1,
                fontFamily = CustomFont.regular
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = writtenAt,
                fontSize = 10.sp,
                color = CustomColor.gray3,
                fontFamily = CustomFont.medium
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = {
                    if (isLiked) heartNumber-- else heartNumber++
                    onHeartClick(reviewId, isLiked)
                    isLiked = !isLiked
                    isDelayed = false
                },
                enabled = isDelayed
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(if(isLiked) R.drawable.ic_heart_selectable_selected else R.drawable.ic_heart_selectable_not_selected),
                    contentDescription = "Heart Icon"
                )
            }
            Spacer(Modifier.width(5.dp))
            Text(
                text = heartNumber.toString(),
                fontSize = 14.sp,
                fontFamily = CustomFont.regular
            )
            Spacer(Modifier.width(10.dp))
            IconButton(
                modifier = Modifier.width(10.dp),
                onClick = onMenuClick
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_vertical_three_dot_menu),
                    tint = CustomColor.gray2,
                    contentDescription = "Menu Button"
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = content,
            fontSize = 12.sp,
            fontFamily = CustomFont.regular,
            color = Color.White,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 17.sp,
        )
        Spacer(Modifier.height(20.dp))
        if (images.isNotEmpty()){Images(images = images)}
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "향BTI 시향카드 [${category}]",
                fontSize = 12.sp,
                fontFamily = CustomFont.regular,
                color = Color.White
            )
        }
    }
    LaunchedEffect(isDelayed){
        if(!isDelayed){
            delay(200)
            isDelayed = true
        }
    }
}

@Composable
private fun Images(images: List<String>){
    LazyRow(
        modifier = Modifier.padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        items(images){ image -> ExpandableImage(picture = image)}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandableImage(
    picture : String
){
    var showDialog by remember{mutableStateOf(false)}
    val configuration = LocalConfiguration.current

    if (showDialog) {
        BasicAlertDialog(
            modifier = Modifier
                .height(configuration.screenHeightDp.dp)
                .width(configuration.screenWidthDp.dp),
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
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
                ){
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {showDialog = false}
                    ){
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
            .size(80.dp)
            .background(color = CustomColor.gray1)
            .pointerInput(Unit) {
                detectTapGestures {
                    showDialog = !showDialog
                }
            },
        contentAlignment = Alignment.Center
    ){
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

@Composable
@Preview
private fun ReviewItemUITest(){
    var isLiked by remember{mutableStateOf(false)}
    var text by remember{mutableStateOf("")}
    var curN by remember{mutableStateOf(0)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        ReviewItem(
            isItemClickable = false,
            reviewId = 0,
            profileImg = "",
            nickname = "향수 러버",
            writtenAt = "10일 전",
            isLiked = isLiked,
            heartNumber = 12,
            content = "평소에 선호하는 향이 있었는데 그 향의 이름을 몰랐는데 향료 배송받고 시향해보니 통카 빈?이더라구요 제가 좋아했던 향수들은 다 통카 빈이 들어가있네요 ㅎ 저같은 분들한테 추천합니다",
            images = listOf("","","","",""),
            category = "시트러스",
            onHeartClick = {a,b -> },
            onMenuClick = {},
            onItemClick = {
                curN += 1
                text = "item click : ${curN}"
            }
        )
        Text(
            text = text,
            fontSize = 30.sp
        )
    }
}
