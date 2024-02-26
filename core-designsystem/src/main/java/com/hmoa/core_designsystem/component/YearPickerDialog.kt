package com.hmoa.component

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun YearPickerDialog(
    width : Dp,
    height : Dp,
    onDismiss : () -> Unit,
    onDoneClick : () -> Unit,
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(modifier = Modifier.fillMaxWidth().height(370.dp)){
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

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
            }

        }
    }
}

@Preview
@Composable
fun TestPickerDialog(){

    val width = LocalConfiguration.current.screenWidthDp.dp

    var showDialog by remember{mutableStateOf(false)}

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
            modifier = Modifier.height(40.dp).width(100.dp),
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