package com.hyangmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hyangmoa.core_designsystem.theme.CustomColor

@Composable
fun AppDefaultDialog(
    isOpen : Boolean,
    modifier : Modifier,
    title : String,
    content : String,
    onDismiss : () -> Unit,
){
    if (isOpen){
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = modifier
                    .background(color = Color.White, shape = RoundedCornerShape(17.dp))
                    .padding(top = 22.dp).padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = content,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(22.dp))

                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = CustomColor.gray2)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            onDismiss()
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "취소",
                        fontSize = 16.sp,
                        color = CustomColor.blue2,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TestDialog(){

    var isOpen by remember{mutableStateOf(false)}

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        
        androidx.compose.material3.Button(
            onClick = {isOpen = true}
        ){
            Text(
                text = "버튼"
            )
        }
        
        AppDefaultDialog(
            isOpen = isOpen,
            modifier = Modifier.wrapContentHeight()
                .width(screenWidth-88.dp),
            title = "Dialog Test",
            content = "테스트 성공",
            onDismiss = { isOpen = false },
        )
    }
}