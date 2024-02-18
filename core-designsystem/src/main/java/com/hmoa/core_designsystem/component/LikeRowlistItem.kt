package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LikeRowItem(
    brand : String,
    itemPicture : ImageVector,
    categories : List<String>,
    price : String,
    itemNameKo : String,
    itemNameEng : String,
){
    Column(
        modifier = Modifier
            .height(354.dp)
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
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color.Black)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = {

                }
            ){
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Button",
                    tint = Color.White
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = brand,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight(500),
                color = Color.White
            )

            Spacer(Modifier.weight(1f))

            Spacer(Modifier.width(16.dp))
        }

        Spacer(Modifier.height(32.dp))

        Image(
            modifier = Modifier.size(120.dp),
            imageVector = itemPicture,
            contentDescription = "Item Picture"
        )

        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(18.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ){
                categories.forEach{ category ->
                    Box(
                        modifier = Modifier.width(50.dp).fillMaxHeight()
                            .border(width = 1.dp, color = Color(0xFF9C9C9C), shape = RoundedCornerShape(size = 20.dp)),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = category,
                            fontSize = 10.sp,
                            lineHeight = 10.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9C9C9C)
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = itemNameKo,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight(400),
                color = Color.Black
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = itemNameEng,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight(400),
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
                    fontWeight = FontWeight(400)
                )

                Text(
                    text = "₩${price}~",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestLikeItem(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        LikeRowItem(
            brand = "조말론 런던",
            itemPicture = Icons.Filled.Build,
            categories = listOf("우디한", "자연한"),
            itemNameKo = "우드 세이지 앤 씨 솔트 코롱",
            itemNameEng = "Wood Sage & Sea Salt Cologne",
            price = "218,000"
        )
    }
}