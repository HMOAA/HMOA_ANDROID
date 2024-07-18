package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.TagBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.data.Spice
import com.hmoa.feature_hbti.viewmodel.SelectSpiceViewModel

@Composable
fun SelectSpiceRoute(
    onNavBack: () -> Unit,
    onNavNext: () -> Unit,
    viewModel: SelectSpiceViewModel = hiltViewModel()
){
    val spices = listOf(
        Spice("스파이스", listOf("통카빈","페퍼")),
        Spice("플로럴", listOf("네롤리","라벤더","핑크 로즈","수선화","화이트 로즈")),
        Spice("시트러스", listOf("만다린","귤","베르가못")),
    )
    // 선택된 태그들에 대한 리스트
    val selectedTags = viewModel.selectedSpices.collectAsStateWithLifecycle()
    val isEnabledBtn = viewModel.isEnabledBtn.collectAsStateWithLifecycle(false)

    SelectSpiceScreen(
        spices = spices,
        selectedTags = selectedTags.value,
        isEnabledBtn = isEnabledBtn.value,
        onDeleteAllTags = { viewModel.deleteAllTags() },
        onDeleteTag = {viewModel.deleteTag(it)},
        onClickTag = {
            if (it in selectedTags.value){
                viewModel.deleteTag(it)
            } else {
                viewModel.addTag(it)
            }
        },
        onNavBack = onNavBack,
        onNavNext = {
            viewModel.postSurveyResult()
            onNavNext()
        }
    )
}

@Composable
fun SelectSpiceScreen(
    spices: List<Spice>,
    selectedTags: MutableList<String>,
    isEnabledBtn: Boolean,
    onDeleteAllTags: () -> Unit,
    onDeleteTag: (String) -> Unit,
    onClickTag: (tag: String) -> Unit,
    onNavBack: () -> Unit,
    onNavNext: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(bottom = 40.dp)
    ){
        TopBar(
            title = "향BTI",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){
            Spacer(Modifier.height(15.dp))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black, shape = RoundedCornerShape(size = 100.dp)),
                thickness = 5.dp,
                color = Color.Black
            )
            Spacer(Modifier.height(24.dp))
            Column{
                if(selectedTags.isNotEmpty()){
                    TagBadges(
                        tags = selectedTags,
                        onDeleteAll = onDeleteAllTags,
                        onDeleteTag = onDeleteTag
                    )
                }
                Text(
                    text = "시향 후 마음에 드는 향료를\n골라주세요",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold))
                )
                Spacer(Modifier.height(16.dp))
                spices.forEach{
                    SpiceDescContent(
                        title = it.spiceTitle,
                        itemNames = it.itemNames,
                        selectedTags = selectedTags,
                        onClickTag = onClickTag
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            Button(
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                isEnabled = isEnabledBtn,
                btnText = "다음",
                onClick = onNavNext
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SpiceDescContent(
    title: String,
    itemNames: List<String>,
    selectedTags: MutableList<String>,
    onClickTag: (tag: String) -> Unit,
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
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            itemNames.forEach{
                TagBadge(
                    backgroundColor = if(it in selectedTags) Color.Black else Color.White,
                    textColor = if(it in selectedTags) Color.White else Color.Black,
                    tag = it,
                    isClickable = true,
                    onClick = onClickTag,
                    height = 32.dp
                )
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TagBadges(
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
private fun TestScreen(){
    val spices = listOf(
        Spice("스파이스", listOf("통카빈","페퍼")),
        Spice("플로럴", listOf("네롤리","라벤더","핑크 로즈","수선화","화이트 로즈")),
        Spice("시트러스", listOf("만다린","귤","베르가못")),
    )
    val selectedTags = remember{mutableStateListOf<String>()}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        SelectSpiceScreen(
            spices = spices,
            selectedTags = selectedTags,
            isEnabledBtn = false,
            onDeleteAllTags = {selectedTags.clear()},
            onDeleteTag = {selectedTags.remove(it)},
            onClickTag = {
                if (it in selectedTags){
                    selectedTags.remove(it)
                } else {
                    selectedTags.add(it)
                }
            },
            onNavBack = {},
            onNavNext = {}
        )
    }
}