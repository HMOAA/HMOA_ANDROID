package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import kotlinx.coroutines.launch

@Composable
fun YearPickerDialog(
    yearList: List<Int>, //출생연도 리스트
    initialValue: Int, //초기 값
    height: Dp, //높이
    onDismiss: () -> Unit, //dismiss event
    onDoneClick: (Int) -> Unit, //확인 클릭 event
) {
    var selectedValue by remember { mutableIntStateOf(initialValue) }

    //offset을 통해 위치 조절
    val density = LocalDensity.current
    val offset = with(density) { 70.dp.toPx().toInt() }

    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = yearList.indexOf(initialValue),
        initialFirstVisibleItemScrollOffset = -offset
    )

    var preventEvent by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth()
                    .padding(start = 38.dp, end = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "출생연도",
                        fontSize = 16.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.Top
                ) {
                    // dialog dismiss
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = onDismiss
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.btn_close),
                            contentDescription = "Close Button"
                        )
                    }
                }
            }

            Spacer(Modifier.height(36.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(168.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                    alignment = Alignment.CenterVertically
                ),
                state = scrollState
            ) {

                val selectedIndex = yearList.indexOf(selectedValue)

                itemsIndexed(yearList) { index, year ->

                    //글씨 크기
                    val fontSize = when (index) {
                        selectedIndex -> 22.sp
                        selectedIndex - 1 -> 21.sp
                        selectedIndex + 1 -> 21.sp
                        selectedIndex - 2 -> 18.sp
                        selectedIndex + 2 -> 18.sp
                        else -> 12.sp
                    }
                    //font color
                    val color = when (index) {
                        selectedIndex -> Color.Black
                        selectedIndex - 1 -> CustomColor.gray3
                        selectedIndex + 1 -> CustomColor.gray3
                        selectedIndex - 2 -> CustomColor.gray2
                        selectedIndex + 2 -> CustomColor.gray2
                        else -> CustomColor.gray1
                    }

                    Text(
                        modifier = Modifier
                            .wrapContentHeight()
                            .clickable {
                                coroutineScope.launch {
                                    preventEvent = true
                                    selectedValue = year
                                    scrollState.animateScrollToItem(
                                        yearList.indexOf(
                                            selectedValue
                                        ), scrollOffset = -offset
                                    )
                                }
                            },
                        text = "${year}",
                        fontSize = fontSize,
                        color = color
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                com.hmoa.core_designsystem.component.Button(true, textColor = Color.White, textSize = 16, btnText = "확인", onClick = {onDoneClick(selectedValue)
                    onDismiss()}, buttonModifier = Modifier
                    .width(200.dp)
                    .fillMaxHeight(),)
            }

            Spacer(Modifier.height(24.dp))
        }
    }
    LaunchedEffect(scrollState.firstVisibleItemIndex) {
        if (!preventEvent) {
            if (scrollState.isScrollInProgress) {
                val centerIndex = scrollState.layoutInfo.visibleItemsInfo
                    .minByOrNull { (scrollState.layoutInfo.viewportEndOffset - scrollState.layoutInfo.viewportStartOffset) }?.index
                    ?: 0

                selectedValue = yearList[centerIndex + 2]
            }
        }
        preventEvent = false
    }

//    Dialog(
//        onDismissRequest = onDismiss,
//        properties = DialogProperties(
//            dismissOnBackPress = true,
//            dismissOnClickOutside = true,
//            usePlatformDefaultWidth = false
//        )
//    ) {
//
//    }
}

@Preview
@Composable
fun TestPickerDialog() {


    var showDialog by remember { mutableStateOf(true) }

    var value by remember { mutableStateOf(2000) }
    val yearList = (1950..2024).toList()

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.LightGray)
    ) {
        if (showDialog) {
            YearPickerDialog(
                height = 370.dp,
                onDismiss = {
                    showDialog = !showDialog
                },
                onDoneClick = {
                    value = it
                },
                initialValue = value,
                yearList = yearList
            )
        }


        Button(
            modifier = Modifier
                .height(40.dp)
                .width(100.dp),
            onClick = {
                showDialog = true
            }
        ) {
            Text(
                text = "show dialog"
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "${value}"
        )
    }

}