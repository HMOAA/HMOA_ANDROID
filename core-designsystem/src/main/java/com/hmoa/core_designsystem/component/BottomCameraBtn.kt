package com.hmoa.core_designsystem.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.R

@Composable
fun BottomCameraBtn(
    isColorInverted: Boolean = true,
    onUpdatePictures : (List<Uri>) -> Unit,
){
    //갤러리에서 사진 가져오기
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {uris ->
            onUpdatePictures(uris)
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
    ){
        IconButton(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            onClick = {
                multiplePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        ){
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.ic_camera),
                tint = if(isColorInverted) Color.Black else Color.White,
                contentDescription = "Add Picture"
            )
        }
    }
}