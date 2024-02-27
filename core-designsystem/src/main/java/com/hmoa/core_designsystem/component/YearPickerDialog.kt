package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
import kotlin.math.abs

@Composable
fun YearPickerDialog(
    yearList : List<Int>, //출생연도 리스트
    value : Int, //초기 값
    width : Dp, //넓이
    height : Dp, //높이
    onDismiss : () -> Unit, //dismiss event
    onDoneClick : (Int) -> Unit, //확인 클릭 event
){
    var selectedValue by remember{mutableIntStateOf(value)}

    //offset을 통해 위치 조절
    val density = LocalDensity.current
    val offset = with(density) {70.dp.toPx().toInt()}

    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = yearList.indexOf(value),
        initialFirstVisibleItemScrollOffset = -offset
    )

    var preventEvent by remember{mutableStateOf(false)}

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(370.dp)){
            Column(
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .background(color = Color.White)
            ){
                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .height(36.dp)
                        .fillMaxWidth()
                        .padding(start = 38.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ){
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
                    ){
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
                ){

                    val selectedIndex = yearList.indexOf(selectedValue)

                    itemsIndexed(yearList) {index, year ->

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
                ){
                    Button(
                        modifier = Modifier
                            .width(200.dp)
                            .fillMaxHeight(),
                        onClick = {
                            onDoneClick(selectedValue)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        )
                    ) {
                        Text(
                            text = "확인",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
        LaunchedEffect(scrollState.firstVisibleItemIndex){
            if (!preventEvent){
                if (scrollState.isScrollInProgress){
                    val centerIndex = scrollState.layoutInfo.visibleItemsInfo
                        .minByOrNull { (scrollState.layoutInfo.viewportEndOffset - scrollState.layoutInfo.viewportStartOffset) }?.index ?: 0

                    selectedValue = yearList[centerIndex+2]
                }
            }
            preventEvent = false
        }
    }
}

@Preview
@Composable
fun TestPickerDialog(){

    val width = LocalConfiguration.current.screenWidthDp.dp

    var showDialog by remember{mutableStateOf(true)}

    var value by remember{mutableStateOf(2000)}
    val yearList = (1950..2024).toList()

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(width)
            .background(color = Color.LightGray)
    ){
        if(showDialog){
            YearPickerDialog(
                width = width,
                height = 370.dp,
                onDismiss = {
                    showDialog = !showDialog
                },
                onDoneClick = {
                    value = it
                },
                value = value,
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