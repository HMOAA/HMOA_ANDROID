package com.example.feature_community

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category

@Composable
fun CommunityDescriptionRoute(
    onNavBack : () -> Unit,
){



    CommunityDescriptionPage(
        category =
        onNavBack = onNavBack
    )
}

@Composable
fun CommunityDescriptionPage(
    category : Category,
    onNavBack : () -> Unit,
){

    val scrollState = rememberScrollState()

    val categoryTextStyle = TextStyle(
        fontSize = 14.sp,
        color = CustomColor.gray2
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        TopBar(
            title = "Community",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack
        )

        Column(
            modifier = Modifier.fillMaxSize()
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
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = CustomColor.gray1,
                        shape = RoundedCornerShape(size = 10.dp)
                    )
            ){
                
            }
        }
    }
}

@Preview
@Composable
fun TestCommunityDescriptionPage(){
    CommunityDescriptionPage()
}