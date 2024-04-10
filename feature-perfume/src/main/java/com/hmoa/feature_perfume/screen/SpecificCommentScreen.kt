package com.hmoa.feature_perfume.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.feature_perfume.viewmodel.SpecificCommentViewmodel

@Composable
fun SpecificCommentRoute(onBackClick: () -> Unit, commentId: Int?) {
    if(commentId != null){
        SpecificCommentScreen(onBackClick = { onBackClick() }, commentId = commentId)
    }
}

@Composable
fun SpecificCommentScreen(
    onBackClick: () -> Unit,
    commentId: Int,
    viewModel: SpecificCommentViewmodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.initializePerfumeComment(commentId = commentId)
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is SpecificCommentViewmodel.SpecificCommentUiState.Loading -> {}
            is SpecificCommentViewmodel.SpecificCommentUiState.CommentData -> {
                SpecificCommentContent(
                    onBackClick = { onBackClick() },
                    data = (uiState as SpecificCommentViewmodel.SpecificCommentUiState.CommentData).comment,
                    isCommentLiked = (uiState as SpecificCommentViewmodel.SpecificCommentUiState.CommentData).isLikeComment
                )
            }

            is SpecificCommentViewmodel.SpecificCommentUiState.Error -> {}
        }
    }
}

@Composable
fun SpecificCommentContent(
    onBackClick: () -> Unit,
    data: PerfumeCommentResponseDto?,
    isCommentLiked:Boolean
) {
    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar(
            title = "댓글",
            iconSize = 25.dp,
            navIcon = painterResource(R.drawable.ic_back),
            onNavClick = { onBackClick() },
        )
        ProfileAndHeartView(
            count = data?.heartCount ?: 0,
            isCommentLiked = isCommentLiked,
            userImgUrl = data?.profileImg ?: "",
            userName = data?.nickname ?: "",
            content = data?.content ?: "",
            createdDate = data?.createdAt ?: "",
            onReportClick = {}
        )
    }
}

@Composable
fun ProfileAndHeartView(
    count: Int,
    isCommentLiked: Boolean,
    userImgUrl: String,
    userName: String,
    content: String,
    createdDate: String,
    onReportClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 9.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                CircleImageView(userImgUrl, 28, 28)
                Text(
                    text = userName,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = createdDate,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = CustomColor.gray3),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            TypeBadge(
                roundedCorner = 20.dp,
                type = "${count}",
                fontColor = Color.Black,
                unSelectedIcon = painterResource(R.drawable.ic_heart),
                selectedIcon = painterResource(R.drawable.ic_heart_filled),
                iconColor = Color.Black,
                fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
                selected = isCommentLiked,
                unSelectedColor = CustomColor.gray1,
                selectedColor = Color.Black
            )
        }
        Text(
            text = content,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
            modifier = Modifier.fillMaxWidth().padding(top = 9.dp)
        )
    }
}

@Preview
@Composable
fun SpecificCommentPreview() {
    ProfileAndHeartView(
        count = 10,
        isCommentLiked = false,
        userName = "이용인",
        userImgUrl = "",
        content = "기존에 사용하던 향이라 재구매했어요. 계절에 상관없이 사용할 수 있어서 좋아요",
        createdDate = "10일 전",
        onReportClick = {}
    )
}
