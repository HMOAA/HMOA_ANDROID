package com.hmoa.core_designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_common.formatWon
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.Note

@Composable
fun NoteListItem(
    noteUrl: String,
    productName: String,
    notes: List<Note>,
    noteCounts: Int,
    totalPrice: Int
){
    val notes = notes.joinToString { it.noteName }
    val price = totalPrice / noteCounts
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ){
        CircleImageView(
            imgUrl = noteUrl,
            width = 60,
            height = 60
        )
        Spacer(Modifier.width(20.dp))
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = productName,
                fontSize = 14.sp,
                fontFamily = CustomFont.semiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = notes,
                fontSize = 10.sp,
                fontFamily = CustomFont.regular
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "수량 ${noteCounts}개",
                fontSize = 10.sp,
                fontFamily = CustomFont.regular,
                color = CustomColor.gray3
            )
        }
        Spacer(Modifier.width(10.dp))
        Column(
            modifier = Modifier.wrapContentWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${formatWon(price)}원/개",
                fontSize = 10.sp,
                fontFamily = CustomFont.regular,
                color = CustomColor.gray3,
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = "${formatWon(totalPrice)}원",
                fontSize = 14.sp,
                fontFamily = CustomFont.semiBold
            )
        }
    }
}