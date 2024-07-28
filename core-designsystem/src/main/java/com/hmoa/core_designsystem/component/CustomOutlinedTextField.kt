package com.hmoa.core_designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier,
    value: String,
    onValueChanged: (value: String) -> Unit,
    fontSize: TextUnit,
    color: Color = Color.Black,
    fontFamily: FontFamily,
    placeHolder: String? = null,
    borderWidth: Dp,
    borderColor: Color,
    borderShape: Shape,
    padding: PaddingValues,
){
    BasicTextField(
        modifier = modifier.border(width = borderWidth, color = borderColor, shape = borderShape)
            .padding(paddingValues = padding),
        value = value,
        onValueChange = onValueChanged,
        textStyle = TextStyle(
            color = color,
            fontSize = fontSize,
            fontFamily = fontFamily
        ),
        singleLine = true,
        maxLines = 1,
        decorationBox = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                if (placeHolder != null && value.isEmpty()){
                    Text(
                        text = placeHolder,
                        fontSize = fontSize,
                        color = CustomColor.gray1,
                        fontFamily = CustomFont.medium
                    )
                }
            }
        }
    )
}