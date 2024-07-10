package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.component.TagBadge
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun SelectSpiceScreen(
    // 여기서 향료에 대한 데이터를 받는게 좋을 것 같음
){
    val spices = listOf<TmpSpiceDto>(
        TmpSpiceDto("스파이스", listOf("통카빈","페퍼")),
        TmpSpiceDto("플로럴", listOf("네롤리","라벤더","핑크 로즈","수선화","화이트 로즈")),
        TmpSpiceDto("시트러스", listOf("만다린","귤","베르가못")),
    )
    /** 저 향료들이 서버에서 정해주는 향료인지 아니면 고정되어 있는 것인지를 아직 잘 모르겠넴.. */
    /** 서버에서 주는 거라고 생각하고 해볼까? */
    Column{
        Text(
            text = "시향 후 마음에 드는 향료를\n골라주세요",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold))
        )
        Spacer(Modifier.height(16.dp))
        spices.forEach{
            SpiceDescContent(
                title = it.spiceTitle,
                itemNames = it.itemNames
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SpiceDescContent(
    title : String,
    itemNames : List<String>
){
    Column{
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = CustomColor.gray1
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold))
        )
        Spacer(Modifier.height(16.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            itemNames.forEach{TagBadge(tag = it)}
        }
    }
}

data class TmpSpiceDto(
    val spiceTitle : String,
    val itemNames : List<String>
)

@Preview
@Composable
private fun TestScreen(){
    SelectSpiceScreen()
}