package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun AppDesignDialog(
    isOpen: Boolean,
    modifier: Modifier,
    title: String,
    buttonTitle: String,
    content: String,
    onOkClick: () -> Unit,
    onCloseClick: () -> Unit,
) {
    if (isOpen) {
        Dialog(
            onDismissRequest = onCloseClick
        ) {
            Column(
                modifier = modifier
                    .background(color = Color.White, shape = RoundedCornerShape(5.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier.fillMaxWidth().padding(top = 12.dp).padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(R.drawable.btn_close),
                        modifier = Modifier.clickable { onCloseClick() },
                        contentDescription = "close button",
                    )
                }
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = pretendard
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = content,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = pretendard
                )

                Spacer(Modifier.height(22.dp))

                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = CustomColor.gray2)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            onOkClick()
                        }.background(color = CustomColor.gray3),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = buttonTitle,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontFamily = pretendard
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun TestNavigateDialog() {

    var isOpen by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        androidx.compose.material3.Button(
            onClick = { isOpen = true }
        ) {
            Text(
                text = "버튼"
            )
        }

        AppDesignDialog(
            isOpen = isOpen,
            modifier = Modifier.wrapContentHeight()
                .width(screenWidth - 88.dp),
            title = "Dialog Test",
            content = "테스트 성공",
            onOkClick = { isOpen = false },
            buttonTitle = "취소",
            onCloseClick = { isOpen = false }
        )
    }
}