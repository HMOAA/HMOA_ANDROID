package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
        modifier = Modifier.fillMaxWidth().height(60.dp),
        verticalAlignment = Alignment.Bottom
    ){
        CircleImageView(
            imgUrl = noteUrl,
            width = 60,
            height = 60
        )
        Spacer(Modifier.width(20.dp))
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.Center
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

@Preview
@Composable
private fun NoteListItemUITest(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        NoteListItem(
            noteUrl = "https://hmoa-note.s3.ap-northeast-2.amazonaws.com/%EC%8B%9C%ED%8A%B8%EB%9F%AC%EC%8A%A4/note-1/110d8e9b-994d-42ff-96be-7e7bab50c073.jpeg",
            productName = "시트러스",
            notes = listOf(
                Note(noteName = "라임 만다린", noteContent = ""),
                Note(noteName = "베르가뭇", noteContent = ""),
                Note(noteName = "비터오렌지", noteContent = ""),
                Note(noteName = "자몽", noteContent = ""),
            ),
            noteCounts = 4,
            totalPrice = 4800
        )
    }
}