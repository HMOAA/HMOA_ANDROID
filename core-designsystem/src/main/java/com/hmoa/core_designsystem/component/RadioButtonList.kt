package com.hmoa.core_designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun RadioButtonList(
    initIndex : Int? = null,
    radioOptions: List<String>,
    onButtonClick:(value:String)->Unit
) {
    val radioOptions = radioOptions
    val (selectedOption, onOptionSelected) = remember {
        val idx = if (initIndex == null) 0 else initIndex
        mutableStateOf(radioOptions[idx])
    }
    LazyRow {
        itemsIndexed(radioOptions){index, item ->
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                RadioButton(colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black,
                    unselectedColor = CustomColor.gray2),
                    selected = (item == selectedOption),
                    onClick = {
                        onOptionSelected(item)
                        onButtonClick(item)
                    },
                )
                Text(
                    text = item,
                    style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal,fontFamily = pretendard),
                )
            }
        }
    }
}

@Preview
@Composable
fun RadioButtonListPreview() {
    RadioButtonList(null,listOf("여성", "남성"),{})
}
