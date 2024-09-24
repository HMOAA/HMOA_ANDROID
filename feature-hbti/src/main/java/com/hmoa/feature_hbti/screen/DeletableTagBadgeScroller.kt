package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor


@Composable
private fun DeletableTagBadgeScroller(
    tags: List<String>,
    onDeleteAll: () -> Unit,
    onDeleteTag: (String) -> Unit
){
    LazyRow(
        modifier = Modifier
            .height(52.dp)
            .padding(bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        item{
            IconButton(
                modifier = Modifier.size(18.dp),
                onClick = onDeleteAll
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_close),
                    contentDescription = "Delete Button"
                )
            }
        }
        items(tags){tag ->
            DeletableTag(tag = tag,onDeleteTag = onDeleteTag)
        }
    }
}

@Composable
private fun DeletableTag(
    tag: String,
    onDeleteTag: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .height(32.dp)
            .wrapContentWidth()
            .background(color = CustomColor.gray3, shape = RoundedCornerShape(size = 14.dp))
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = tag,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
            color = Color.White
        )
        Spacer(Modifier.width(4.dp))
        IconButton(
            modifier = Modifier.size(12.dp),
            onClick = { onDeleteTag(tag) }
        ){
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.btn_close),
                contentDescription = "delete tag",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun DeletableTagBadgeScrollerPreview(){
    DeletableTagBadgeScroller(tags = listOf("수산화","화이트 로즈","귤","베르가못","라벤더","만다린"), onDeleteTag = {}, onDeleteAll = {})
}