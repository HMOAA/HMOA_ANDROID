package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CommentInputBar(
    modifier : Modifier = Modifier,
    profile : String?,
    onCommentApply : (String) -> Unit,
){
    Row(
        modifier = modifier
            .padding(start = 8.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        //text field 변수
        var comment by remember{mutableStateOf("")}

        val commentTextStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontFamily = pretendard
        )
        val placeHolderTextStyle = TextStyle(
            fontSize = 14.sp,
            color = CustomColor.gray3,
            fontWeight = FontWeight.Normal,
            fontFamily = pretendard
        )

        /** loading 화면을 따로 받을 수 있다면 추가 */
        GlideImage(
            imageModel = profile,
            modifier = Modifier.size(28.dp)
                .clip(CircleShape),
            contentDescription = "Profile",
            loading = {
                /** loading 화면을 따로 받을 수 있다면 추가 */
            },
            failure = {
                Box(
                    modifier = Modifier.size(28.dp)
                        .clip(CircleShape)
                        .background(color = Color.White, shape = CircleShape)
                ){
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Not Loading Profile",
                        tint = CustomColor.gray2
                    )
                }
            }
        )
        Spacer(Modifier.width(2.dp))

        Text(
            text = ":",
            fontWeight = FontWeight.Normal,
            fontFamily = pretendard
        )

        BasicTextField(
            modifier = Modifier.weight(1f),
            value = comment,
            onValueChange = {
                comment = it
            },
            textStyle = commentTextStyle
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                if (comment.isEmpty()) {
                    Text(
                        text = "댓글을 입력하세요",
                        style = placeHolderTextStyle
                    )
                } else {
                    it()
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        IconButton(
            onClick = {
                onCommentApply(comment)
                comment = ""
            }
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_comment_input),
                contentDescription = "Apply Comment Button"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestCommentInputBar(){

    var test by remember{mutableStateOf("")}

    CommentInputBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp)
            .background(color = CustomColor.gray6, shape = RoundedCornerShape(5.dp)),
        profile = "",
        onCommentApply = {
            test = it
        }
    )
}