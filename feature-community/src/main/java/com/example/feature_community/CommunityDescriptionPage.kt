package com.example.feature_community

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Comment
import com.hmoa.core_designsystem.component.PostContent
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto

@Composable
fun CommunityDescriptionRoute(
    onNavBack : () -> Unit,
){

    /** bottom options 상태 관리 state **/

    /** 데이터 받아와서 CommunityData로 상태 관리 **/

//    CommunityDescriptionPage(
//        onNavBack = onNavBack
//    )
}

@Composable
fun CommunityDescriptionPage(
    //bottom sheet 띄우는 state 갱신 event
    changeBottomOptionState : (Boolean) -> Unit,
    category : Category,
    profile : String,
    nickname : String,
    dateDiff : String,
    title : String,
    content : String,
    heartCount : String,
    isLiked : Boolean,
    commentList : List<CommunityCommentDefaultResponseDto> = listOf(),
    onNavBack : () -> Unit,
){

    val scrollState = rememberScrollState()

    val categoryTextStyle = TextStyle(
        fontSize = 14.sp,
        color = CustomColor.gray2
    )
    val infoTextStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Black
    )
    val commentSizeTextStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.Black
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "Community",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(state = scrollState, orientation = Orientation.Vertical)
        ){
            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = category.name,
                style = categoryTextStyle
            )
            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = CustomColor.gray1,
                        shape = RoundedCornerShape(size = 10.dp)
                    )
            ){
                PostContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = CustomColor.gray3,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    onChangeBottomSheetState = {
                        changeBottomOptionState(it)
                    },
                    profile = profile,
                    nickname = nickname,
                    dateDiff = dateDiff,
                    title = title,
                    content = content,
                    heartCount = heartCount,
                    isLiked = isLiked
                )
            }

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "답변",
                    style = infoTextStyle
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = "+${commentList.size}",
                    style = commentSizeTextStyle
                )
            }

            Spacer(Modifier.height(21.dp))

            commentList.forEachIndexed{ index, comment ->
                Comment(
                    profile = comment.profileImg,
                    nickname = comment.nickname,
                    dateDiff = comment.createAt,
                    comment = comment.content,
                    isFirst = false,
                    viewNumber = if (comment.heartCount > 999) "999+" else comment.heartCount.toString(),
                    onNavCommunity = {}
                )

                if (index != commentList.size - 1) {
                    Spacer(Modifier.height(15.dp))
                }
            }
        }
    }

    /** 여기에 댓글 작성 EditText 생성 */
}

@Preview
@Composable
fun TestCommunityDescriptionPage(){
    var isOpenBottomOptions by remember{mutableStateOf(false)}
    CommunityDescriptionPage(
        changeBottomOptionState = {
            isOpenBottomOptions = it
        },
        category = Category.추천,
        profile = "",
        nickname = "하리보",
        dateDiff = "3일 전",
        title = "테스트 제목입니다.",
        content = "테스트 내용입니다. 테스트 내용입니다. 테스트 내용입니다. 테스트 내용입니다. 테스트 내용입니다. 테스트 내용입니다. 테스트 내용입니다.",
        heartCount = "259",
        isLiked = false,
        onNavBack = {}
    )
}