package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

//여기 Component가 자세히 나와 있지 않아서 일단 감으로 합니다
@Composable
fun TypeBadge(
    onClickItem: () -> Unit = {},
    roundedCorner: Dp,
    type: String,
    fontSize: TextUnit,
    fontColor: Color,
    selected: Boolean? = null,
    selectedIcon: Painter? = null,
    unSelectedIcon: Painter? = null,
    iconColor: Color? = null,
    selectedColor: Color = Color.Black,
    unSelectedColor: Color = CustomColor.gray2,
) {
    val fontStyle = TextStyle(
        fontSize = fontSize,
        color = fontColor,
        fontWeight = FontWeight.Normal,
        fontFamily = pretendard
    )
    val backgroundColor = if (selected != null && selected) selectedColor else unSelectedColor
    val icon = if (selected != null && selected) selectedIcon else unSelectedIcon

    Row(
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(size = roundedCorner))
            .clip(RoundedCornerShape(size = roundedCorner))
            .clickable {
                onClickItem()
            }
            .padding(horizontal = 6.dp).padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null && iconColor != null) {
            Icon(painter = icon, contentDescription = "아이콘", modifier = Modifier.size(12.dp), tint = iconColor)
            Spacer(modifier = Modifier.padding(start = 4.dp))
        }
        Text(
            text = type,
            style = fontStyle,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestTypeButton() {
    TypeBadge(
        roundedCorner = 20.dp,
        type = "추천",
        fontSize = 14.sp,
        fontColor = Color.White,
        selected = false,
    )
}