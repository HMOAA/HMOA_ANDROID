package com.hmoa.core_designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.R

@Composable
fun BottomCameraBtn(
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
    ){
        IconButton(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            onClick = onClick
        ){
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = "Add Picture"
            )
        }
    }
}