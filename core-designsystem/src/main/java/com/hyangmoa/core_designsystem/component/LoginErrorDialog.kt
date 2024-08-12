package com.hyangmoa.core_designsystem.component

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun LoginErrorDialog(enableDialog: Boolean, onConfirmClick: () -> Unit, onCloseClick: () -> Unit) {
    var isOpen by remember { mutableStateOf(true) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    if (enableDialog) {
        AppDesignDialog(
            isOpen = isOpen,
            modifier = Modifier.wrapContentHeight()
                .width(screenWidth - 88.dp),
            title = "로그인 후 이용가능한 서비스입니다",
            content = "입력하신 내용을 다시 확인해주세요",
            buttonTitle = "로그인 하러가기",
            onOkClick = {
                isOpen = false
                onConfirmClick()
            },
            onCloseClick = {
                isOpen = false
                onCloseClick()
            }
        )
    }
}