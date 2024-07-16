package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun OnAndOffBtn(
    isChecked: Boolean,
    onChangeChecked: (Boolean) -> Unit
){
    Switch(
        checked = isChecked,
        onCheckedChange = onChangeChecked,
        colors = SwitchDefaults.colors(
            checkedTrackColor = CustomColor.gray2,
            uncheckedBorderColor = CustomColor.gray2,
            uncheckedThumbColor = CustomColor.gray3
        )
    )
}

@Preview
@Composable
fun TestOnAndOffBtn(){
    var checked by remember{mutableStateOf(false)}
    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        contentAlignment = Alignment.Center
    ){
        Column{
            OnAndOffBtn(
                isChecked = checked,
                onChangeChecked = {checked = it}
            )
            Spacer(Modifier.height(20.dp))
            Text(text = checked.toString())
        }
    }
}