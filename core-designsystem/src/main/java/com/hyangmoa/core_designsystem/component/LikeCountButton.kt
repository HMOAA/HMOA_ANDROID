package com.hyangmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.hyangmoa.core_designsystem.theme.CustomColor

@Composable
fun LikeCountButton(
    onClickItem: () -> Unit = {},
    count: Int,
    fontSize: TextUnit,
    fontColor: Color,
    selected: Boolean,
    iconSize: Int = 18,
    selectedColor: Color = CustomColor.red,
    unSelectedColor: Color = CustomColor.gray2
) {
    val fontStyle = TextStyle(
        fontSize = fontSize,
        color = fontColor
    )
    val backgroundColor = if (selected) selectedColor else unSelectedColor

    Row(
        modifier = Modifier.background(Color.White)
            .clickable {
                onClickItem()
            }
            .padding(horizontal = 6.dp).padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_heart_filled),
            contentDescription = "아이콘",
            modifier = Modifier.size(iconSize.dp),
            tint = backgroundColor
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(
            text = "${count}",
            style = fontStyle,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LikeCountButtonPreview() {
    LikeCountButton(
        onClickItem = {},
        count = 12,
        fontSize = TextUnit(value = 14f, type = TextUnitType.Sp),
        fontColor = Color.Black,
        selected = true,
        iconSize = 18
    )
}