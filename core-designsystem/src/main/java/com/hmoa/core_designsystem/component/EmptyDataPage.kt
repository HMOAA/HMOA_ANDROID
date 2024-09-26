package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomFont

@Composable
fun EmptyDataPage(
    mainText: String,
    buttonText: String,
    onClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier.size(110.dp),
            painter = painterResource(R.drawable.ic_app_default_1),
            contentDescription = "App Logo"
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = mainText,
            fontSize = 22.sp,
            fontFamily = CustomFont.bold,
        )
        Spacer(Modifier.weight(1f))
        Button(
            textSize = 15,
            radious = 5,
            isEnabled = true,
            btnText = buttonText,
            onClick = onClick
        )
    }
}