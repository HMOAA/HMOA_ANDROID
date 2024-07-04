package com.hmoa.core_designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun PerfumeWithCountItemView(
    imageUrl: String,
    perfumeName: String,
    brandName: String,
    containerWidth: Int,
    containerHeight: Int,
    imageWidth: Float,
    imageHeight: Float,
    imageBackgroundColor: Color,
    heartCount: Int,
    isLikedPerfume: Boolean,
    onPerfumeClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 4.dp).padding(bottom = 30.dp).clickable { onPerfumeClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.width(containerWidth.dp).height(containerHeight.dp)
                .background(imageBackgroundColor)
                .border(border = BorderStroke(width = 1.dp, color = CustomColor.gray3)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            ImageView(
                imageUrl = imageUrl,
                backgroundColor = imageBackgroundColor,
                width = imageWidth,
                height = imageHeight,
                contentScale = ContentScale.Fit
            )
        }
        Row(
            modifier = Modifier.width(containerWidth.dp).padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = brandName, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal,
                    fontFamily = pretendard),
            )
            TypeBadge(
                roundedCorner = 20.dp,
                type = "${heartCount}",
                fontColor = Color.Black,
                unSelectedIcon = painterResource(R.drawable.ic_heart),
                selectedIcon = painterResource(R.drawable.ic_heart_filled),
                iconColor = Color.Black,
                fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
                selected = isLikedPerfume,
                unSelectedColor = CustomColor.gray1,
                selectedColor = CustomColor.gray1
            )
        }
        Text(
            text = perfumeName,
            style = TextStyle(
                fontSize = 12.sp, fontWeight = FontWeight.Normal,
                fontFamily = pretendard, textAlign = TextAlign.Start
            ),
            modifier = Modifier.width(containerWidth.dp).padding(end = 4.dp).padding(bottom = 2.dp),
            softWrap = true
        )
    }
}