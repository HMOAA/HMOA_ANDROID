package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun ReportModal(onOkClick: () -> Unit, onCancelClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().background(color = Color.Transparent),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp).background(color = Color.Transparent)) {

            ModalButton(
                "신고",
                { onOkClick() },
                textColor = Color.Black,
                textSize = 20,
                radious = 10
            )
            ModalButton(
                "취소",
                { onCancelClick() },
                textColor = Color.Black,
                textSize = 20,
                radious = 10
            )
        }
    }
}

@Composable
private fun ModalButton(
    btnText: String, //버튼 메세지
    onClick: () -> Unit, //버튼 이벤트
    textColor: Color = Color.White,
    textSize: Int = 20,
    radious: Int,
) {
    Row(
        modifier = Modifier.padding(bottom = 10.dp).background(color = Color.Transparent)
            .clip(RoundedCornerShape(size = radious.dp))
            .border(width = 1.dp, color = CustomColor.gray2, shape = RoundedCornerShape(radious.dp))
    ){
        Row(
            modifier = Modifier
                .background(
                    color = Color.White,
                )
                .clickable {
                    onClick()
                }.clip(RoundedCornerShape(size = radious.dp)).border(
                    width = 1.dp, color = CustomColor.gray2, shape = RoundedCornerShape(
                        radious.dp
                    )
                ).fillMaxWidth().height(60.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = btnText,
                fontSize = textSize.sp,
                color = textColor,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun ReportModalPreview() {
    ReportModal({}, {})
}