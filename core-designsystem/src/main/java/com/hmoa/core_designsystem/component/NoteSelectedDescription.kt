package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.Note

@Composable
fun NoteSelectedDescription(
    imgUrl: String,
    productName: String,
    price: Int,
    categoryNumber: Int,
    notes: List<Note>,
    noteSize: Int,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = CustomColor.gray1, shape = RoundedCornerShape(size = 5.dp))
            .padding(start = 16.dp, end = 12.dp, top = 20.dp, bottom = 16.dp)
    ){
        CircleImageView(
            imgUrl = imgUrl,
            width = noteSize,
            height = noteSize
        )
        Spacer(Modifier.width(19.dp))
        Column(
            modifier = Modifier.weight(1f)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = productName,
                    fontSize = 14.sp,
                    fontFamily = CustomFont.bold
                )
                Text(
                    text = "${price}원",
                    fontSize = 14.sp,
                    fontFamily = CustomFont.regular
                )
            }
            Text(
                text = "(${categoryNumber}가지 향료)",
                fontSize = 12.sp,
                fontFamily = CustomFont.regular
            )
            Spacer(Modifier.height(8.dp))
            notes.forEach{ note ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "· ",
                        fontSize = 16.sp,
                        fontFamily = CustomFont.extraBold
                    )
                    Text(
                        text = "${note.noteName} : ${note.noteContent}",
                        fontSize = 12.sp,
                        fontFamily = CustomFont.regular
                    )
                }
                if (notes.indexOf(note) != notes.lastIndex){
                    Spacer(Modifier.height(3.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun TestUI(){
    val sample = listOf(
        Note(
            noteName = "통카빈",
            noteContent = "열대 나무 씨앗을 말린 향신료"
        ),
        Note(
            noteName = "넛맥",
            noteContent = "넛맥이란 식물을 말린 향신료"
        ),
        Note(
            noteName = "페퍼",
            noteContent = "후추"
        )
    )
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ){
        NoteSelectedDescription(
            imgUrl = "",
            productName = "프루트",
            price = 4800,
            categoryNumber = 3,
            notes = sample,
            noteSize = 66
        )
    }
}