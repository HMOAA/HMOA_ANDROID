package com.hmoa.component

import android.util.TimeFormatException
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration
import kotlin.math.abs

@Composable
fun YearPickerDialog(
    width : Dp,
    height : Dp,
    onDismiss : () -> Unit,
    onDoneClick : () -> Unit,
){
    val testData = (1950..2024).toList()

    var data by remember{mutableIntStateOf(2000)}

    val scrollState = rememberLazyListState()

    val density = LocalDensity.current

    val coroutineScope = rememberCoroutineScope()

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
                        space = 5.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    state = scrollState
                ){
                    itemsIndexed(testData){ index, year ->

                        when (index) {
                            testData.indexOf(data)-2 -> {
                                Text(
                                    modifier = Modifier.clickable{
                                        data = year
                                    },
                                    text = "${year}",
                                    fontSize = 14.sp
                                )
                            }
                            testData.indexOf(data)-1 -> {
                                Text(
                                    modifier = Modifier.clickable{
                                        data = year
                                    },
                                    text = "${year}",
                                    fontSize = 16.sp
                                )
                            }
                            testData.indexOf(data) -> {
                                Text(
                                    text = "${data}",
                                    fontSize = 18.sp
                                )
                            }
                            testData.indexOf(data)+1 -> {
                                Text(
                                    modifier = Modifier.clickable{
                                        data = year
                                    },
                                    text = "${year}",
                                    fontSize = 16.sp
                                )
                            }
                            testData.indexOf(data)+2 -> {
                                Text(
                                    modifier = Modifier.clickable{
                                        data = year
                                    },
                                    text = "${year}",
                                    fontSize = 14.sp
                                )
                            }
                            else -> {
                                Text(
                                    text = "",
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

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
                        onClick = onDoneClick,
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

                //test
                Text(
                    text = "${data}"
                )
            }

        }
        LaunchedEffect(data, scrollState){

//            val centerIndex = scrollState.layoutInfo.visibleItemsInfo
//                .minByOrNull { abs(it.offset - scrollState.layoutInfo.viewportEndOffset) }?.index ?: 0
            val selectedIndex = testData.indexOf(data)
            val currentIndex = scrollState.layoutInfo.viewportStartOffset

            if (!preventEvent){
                // data 값이 변경되었을 때 스크롤 조정 및 data 업데이트
                if (currentIndex != selectedIndex) {
                    val centerScrollOffset = with(density) { 60.dp.toPx() }.toInt()

                    if (scrollState.isScrollInProgress){
                        return@LaunchedEffect
                    }

                    scrollState.animateScrollToItem(selectedIndex) //, scrollOffset = -centerScrollOffset
                }
            }
            preventEvent = false
            data = testData[selectedIndex]
        }
    }
}

@Preview
@Composable
fun TestPickerDialog(){

    val width = LocalConfiguration.current.screenWidthDp.dp

    var showDialog by remember{mutableStateOf(true)}

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

                }
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
    }

}