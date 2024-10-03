package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchWord: String,
    onChangeWord: (String) -> Unit,
    onClearWord: () -> Unit,
    onClickSearch: () -> Unit,
    navBack: () -> Unit,
) {

    TopAppBar(
        title = {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp),
                value = searchWord,
                onValueChange = {
                    if(it == ""){
                        onClearWord()
                    }
                    onChangeWord(it)
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,fontFamily = pretendard
                ),
                keyboardActions = KeyboardActions(onDone = {onClickSearch()}, onGo = {onClickSearch()}, onSend = {onClickSearch()}, onSearch = {onClickSearch()})
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (searchWord.isEmpty()) {
                        Text(
                            text = "키워드를 검색해보세요",
                            fontSize = 16.sp,
                            style = TextStyle(fontWeight = FontWeight.Normal,fontFamily = pretendard),
                            color = CustomColor.gray3
                        )
                    } else {
                        it()
                    }
                }
            }
        },
        navigationIcon = {
            Spacer(Modifier.width(16.dp))

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = navBack
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Back Button"
                )
            }
        },
        actions = {

            if (searchWord.isNotEmpty()) {
                IconButton(
                    modifier = Modifier.size(20.dp)
                        .background(color = CustomColor.gray2, shape = CircleShape)
                        .clip(CircleShape),
                    onClick = onClearWord
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Button",
                        tint = Color.White
                    )
                }
            } else {
                Icon(
                    modifier = Modifier.size(20.dp).clickable { onClickSearch() },
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search Button",
                    tint = CustomColor.gray3
                )
            }
            Spacer(Modifier.width(16.dp))
        },
        colors = TopAppBarColors(
            containerColor = Color.White,
            actionIconContentColor = CustomColor.gray3,
            titleContentColor = Color.Black,
            scrolledContainerColor = Color.White,
            navigationIconContentColor = Color.Black
        )
    )

}

@Preview
@Composable
fun TestSearchBar() {

    var content by remember { mutableStateOf("test") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        SearchTopBar(
            searchWord = content,
            onChangeWord = {
                content = it
            },
            onClickSearch = {},
            onClearWord = { content = "" },
            navBack = {}
        )
    }
}