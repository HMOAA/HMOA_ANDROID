package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun ReportModal(onOkClick: () -> Unit, onCancelClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().background(color = Color.Gray),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column {
            Button(true, "신고", {onOkClick()}, buttonModifier = Modifier.background(color = Color.White).border(width = 1.dp, color = CustomColor.gray2).fillMaxWidth().height(60.dp), textColor = CustomColor.blue, textSize = 20, radious = 15)
            Button(true, "취소", {onCancelClick()}, buttonModifier = Modifier.background(color = Color.White).fillMaxWidth().height(60.dp), textColor = CustomColor.blue, textSize = 20, radious = 15)
        }
    }
}

@Preview
@Composable
fun ReportModalPreview() {
    ReportModal({},{})
}