package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun LikeRowItem(
    brand: String,
    itemPicture: String,
    price: String,
    itemNameKo: String,
    itemNameEng: String,
    onClickClose: () -> Unit,
    onNavPerfumeDesc: () -> Unit,
    isCloseButtonExist:Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(color = Color.White, shape = RoundedCornerShape(size = 5.dp))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
            .shadow(
                elevation = 8.dp,
                spotColor = Color(0x26000000),
                ambientColor = Color(0x26000000)
            )
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x33000000),
                ambientColor = Color(0x33000000)
            ).clickable {
                onNavPerfumeDesc()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color.Black)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(isCloseButtonExist){
                IconButton(
                    modifier = Modifier.size(16.dp),
                    onClick = onClickClose
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Button",
                        tint = Color.White
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = brand,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = pretendard,
                color = Color.White
            )

            Spacer(Modifier.weight(1f))

            Spacer(Modifier.width(16.dp))
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier.size(120.dp)
        ) {
            ImageView(
                imageUrl = itemPicture,
                width = 1f,
                height = 1f,
                backgroundColor = Color.White,
                contentScale = ContentScale.Fit
            )
        }
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = itemNameKo,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = pretendard,
                lineHeight = 14.sp,
                color = Color.Black
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = itemNameEng,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = pretendard,
                color = Color.Black
            )

            Spacer(Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Price",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = pretendard,
                )

                Text(
                    text = "₩${price}~",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = pretendard,
                    color = Color.Black
                )
            }
        }
        Spacer(Modifier.weight(1f))
    }
}
