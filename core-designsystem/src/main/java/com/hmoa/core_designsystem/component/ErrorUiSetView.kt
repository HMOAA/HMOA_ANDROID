package com.hmoa.core_designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.hmoa.core_common.ErrorUiState


@Composable
fun ErrorUiSetView(isOpen: Boolean, onConfirmClick: () -> Unit, errorUiState: ErrorUiState, onCloseClick: () -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    when (errorUiState) {
        is ErrorUiState.ErrorData -> {
            if (errorUiState.expiredTokenError) {
                Box(modifier = Modifier.semantics { testTag = "expiredTokenError" }) {
                    AppDesignDialog(
                        isOpen = isOpen,
                        modifier = Modifier.wrapContentHeight()
                            .width(screenWidth - 88.dp),
                        title = "리프레시 토큰이 만료되었습니다",
                        content = "다시 로그인해주세요",
                        buttonTitle = "로그인 하러가기",
                        onOkClick = {
                            onConfirmClick()
                        },
                        onCloseClick = {
                            onCloseClick()
                        }
                    )
                }
            } else if (errorUiState.wrongTypeTokenError) {
                Box(modifier = Modifier.semantics { testTag = "wrongTypeTokenError" }) {
                    AppDesignDialog(
                        isOpen = isOpen,
                        modifier = Modifier.wrapContentHeight()
                            .width(screenWidth - 88.dp),
                        title = "유효하지 않은 토큰입니다",
                        content = "유효하지 않은 토큰입니다",
                        buttonTitle = "로그인 하러가기",
                        onOkClick = {
                            onConfirmClick()
                        },
                        onCloseClick = {
                            onCloseClick()
                        }
                    )
                }
            } else if (errorUiState.unknownError) {
                Box(modifier = Modifier.semantics { testTag = "unknownError" }) {
                    AppDesignDialog(
                        isOpen = isOpen,
                        modifier = Modifier.wrapContentHeight()
                            .width(screenWidth - 88.dp),
                        title = "로그인 후 이용가능한 서비스입니다",
                        content = "입력하신 내용을 다시 확인해주세요",
                        buttonTitle = "로그인 하러가기",
                        onOkClick = {
                            onConfirmClick()
                        },
                        onCloseClick = {
                            onCloseClick()
                        }
                    )
                }
            } else if (errorUiState.generalError.first) {
                Box(modifier = Modifier.semantics { testTag = "generalError" }) {
                    AppDefaultDialog(
                        isOpen = isOpen,
                        title = "이런 오류가 발생했어요 :(",
                        content = (errorUiState as ErrorUiState.ErrorData).generalError.second ?: "",
                        onDismiss = {
                            onCloseClick()
                        },
                        modifier = Modifier.wrapContentHeight()
                            .width(screenWidth - 88.dp)
                    )
                }
            }
        }

        ErrorUiState.Loading -> {}
    }
}
