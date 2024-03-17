package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PostContent(
    modifier : Modifier = Modifier,
    onChangeBottomSheetState : (Boolean) -> Unit,
    profile : String,
    nickname : String,
    dateDiff : String,
    title : String,
    content : String,
    heartCount : String,
    isLiked : Boolean,
    pictures : List<String>
){
    val nicknameTextStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black
    )
    val dateDiffTextStyle = TextStyle(
        fontSize = 12.sp,
        color = CustomColor.gray3
    )
    val titleTextStyle = TextStyle(
        fontSize = 20.sp,
        color = Color.Black
    )
    val contentTextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Black
    )
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
    ){
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painterResource(R.drawable.question_ic),
                contentDescription = "Question Icon"
            )

            Spacer(Modifier.width(8.dp))

            //profile
            GlideImage(
                imageModel = profile,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                contentDescription = "Profile",
                loading = {
                    /** loading 화면을 따로 받을 수 있다면 추가 */
                },
                failure = {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(color = Color.White, shape = CircleShape)
                    ){
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Not Loading Profile",
                            tint = CustomColor.gray2
                        )
                    }
                }
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = nickname,
                style = nicknameTextStyle
            )

            Spacer(Modifier.width(7.dp))

            Text(
                text = dateDiff,
                style = dateDiffTextStyle
            )

            Spacer(Modifier.weight(1f))

            //menu (isWritten true >> 수정, 삭제, 취소 / false >> 신고하기, 취소
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = { onChangeBottomSheetState(true) }
            ) {
                Icon(
                    modifier = Modifier
                        .width(5.dp)
                        .height(16.dp),
                    painter = painterResource(R.drawable.three_dot_menu_vertical),
                    contentDescription = "Menu Button",
                    tint = CustomColor.gray2
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = title,
            style = titleTextStyle
        )

        Spacer(Modifier.height(15.dp))

        Text(
            text = content,
            style = contentTextStyle
        )

        Spacer(Modifier.height(17.dp))

        if (pictures.isNotEmpty()){
            PictureContainer(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 27.dp, vertical = 32.dp),
                pictures = pictures
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            TypeBadge(
                roundedCorner = 20.dp,
                type = heartCount,
                fontSize = 12.sp,
                fontColor = Color.Black,
                selected = isLiked,
                icon = if (isLiked) painterResource(R.drawable.fill_heart_ic)
                    else painterResource(R.drawable.ic_heart),
                iconColor = Color.Black,
                selectedColor = CustomColor.gray1,
                unSelectedColor = CustomColor.gray1
            )
        }

        Spacer(Modifier.height(14.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TestPostContent(){

    var isOpen by remember{mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        PostContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(width = 1.dp, color = CustomColor.gray3, shape = RoundedCornerShape(10.dp)),
            profile = "",
            nickname = "향수 러버",
            dateDiff = "10일 전",
            title = "여자친구한테 선물할 향수 뭐가 좋을까요?",
            content = "곧 있으면 여자친구 생일이라 향수를 선물해주고 싶은데, 요즘 20대 여성이 사용할 만한 향수 추천해주세요. 가격대는 10~20만원정도로 생각하고 있습니다.",
            heartCount = "10",
            isLiked = false,
            onChangeBottomSheetState = {
                isOpen = it
            },
            pictures = listOf("A","B")
        )
    }
}