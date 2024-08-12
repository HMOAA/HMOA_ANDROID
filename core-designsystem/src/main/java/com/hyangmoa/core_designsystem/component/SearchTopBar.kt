package com.hyangmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyangmoa.core_designsystem.R
import com.hyangmoa.core_designsystem.theme.CustomColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchWord: String,
    onChangeWord: (String) -> Unit,
    onClearWord: () -> Unit,
    onClickSearch: () -> Unit,
    onNavBack: () -> Unit,
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
                onClick = onNavBack
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
            onNavBack = {}
        )
    }
}