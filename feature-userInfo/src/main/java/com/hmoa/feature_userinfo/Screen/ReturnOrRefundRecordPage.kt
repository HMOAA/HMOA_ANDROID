package com.hmoa.feature_userinfo.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.NoteListItem
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.Note

@Composable
fun ReturnOrRefundRecordRoute(){

}

@Composable
fun ReturnOrRefundRecordScreen(){
    val dummyData = listOf(0)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        TopBar(
            title = "환불 / 반품 내역",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = { /** 뒤로가기 navigation */ }
        )
        LazyColumn{
            items(dummyData){
                ReturnOrRefundRecordItem(
                    status = "취소완료",
                    requestAt = "2024/07/21"
                )
                ReturnOrRefundRecordItem(
                    status = "반품완료",
                    requestAt = "2024/07/01"
                )
            }
        }
    }
}

@Composable
private fun ReturnOrRefundRecordItem(
    status: String,
    requestAt: String,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = status,
                fontSize = 12.sp,
                fontFamily = CustomFont.bold,
            )
            Spacer(Modifier.width(16.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), thickness = 1.dp, color = CustomColor.gray1)
            Spacer(Modifier.width(16.dp))
            Text(
                text = requestAt,
                fontSize = 12.sp,
                fontFamily = CustomFont.bold,
                color = CustomColor.gray3
            )
        }
        Spacer(Modifier.height(20.dp))
        NoteListItem(
            noteUrl = "",
            productName = "플로럴",
            notes = listOf(
                Note(noteName = "네롤리", noteContent = ""),
                Note(noteName = "화이트로즈", noteContent = ""),
                Note(noteName = "핑크 로즈", noteContent = ""),
                Note(noteName = "화이트 로즈", noteContent = ""),
                Note(noteName = "바이올렛", noteContent = ""),
                Note(noteName = "피오니", noteContent = ""),
            ),
            noteCounts = 6,
            totalPrice = 1200
        )
    }
}

@Preview
@Composable
private fun ReturnOrRefundRecordUITest(){
    ReturnOrRefundRecordScreen()
}