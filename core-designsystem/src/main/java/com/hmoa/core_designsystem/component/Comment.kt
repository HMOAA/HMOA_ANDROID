package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Comment(
    profile : String,
    nickname : String,
    dateDiff : String,
    comment : String,
    isFirst : Boolean,
    viewNumber : String,
    onNavCommunity : () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .border(
                width = 0.5.dp,
                color = Color(0xFFBBBBBB),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable{
                onNavCommunity()
            }
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ){
        Spacer(Modifier.height(11.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            GlideImage(
                modifier = Modifier.size(28.dp),
                imageModel = profile,
                success = { imageState ->
                    imageState.drawable?.let {
                        Image(
                            bitmap = it.toBitmap().asImageBitmap(),
                            contentDescription = null
                        )
                    }
                },
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                },
                failure = {
                    Text(
                        text = "Image Loading is Failed"
                    )
                }
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = nickname,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight(400),
                color = Color.Black
            )

            Spacer(Modifier.width(2.dp))

            if (isFirst) {

                Spacer(Modifier.width(2.dp))

                Icon(
                    modifier = Modifier
                        .size(12.dp),
                    painter = painterResource(R.drawable.badge),
                    contentDescription = "Bedge",
                    tint = CustomColor.blue
                )

                Spacer(Modifier.width(5.dp))
            }

            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = dateDiff,
                fontSize = 12.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight(300),
                color = CustomColor.gray3
            )

            Spacer(Modifier.weight(1f))

            if (viewNumber != "not"){
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(22.dp)
                        .background(
                            color = Color(0xFFF4F4F4),
                            shape = RoundedCornerShape(size = 20.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 3.dp),
                    contentAlignment = Alignment.CenterStart
                ){

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite Button"
                        )

                        if (viewNumber != "1"){
                            Spacer(Modifier.width(2.dp))

                            Text(
                                modifier = Modifier.padding(bottom = 2.dp),
                                text = viewNumber,
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                fontWeight = FontWeight(300),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(11.dp))

        Text(
            text = comment,
            fontSize = 14.sp,
            lineHeight = 19.6.sp,
            fontWeight = FontWeight(300),
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestComment(){
    Comment(
        profile = "",
        nickname = "nickname",
        dateDiff = "2일 전",
        comment = "아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ",
        isFirst = true,
        viewNumber = "999+",
        onNavCommunity = {},
    )
}