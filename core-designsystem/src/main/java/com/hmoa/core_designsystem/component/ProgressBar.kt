package com.hmoa.core_designsystem.component


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.hmoa.core_designsystem.theme.CustomColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProgressBar(percentage: Float) {
    LinearProgressIndicator(
        progress = { percentage },
        modifier = Modifier.fillMaxWidth(),
        color = CustomColor.black,
        trackColor = CustomColor.gray1,
        strokeCap = StrokeCap.Round,
    )
}

/** Iterate the progress value */
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(5)
    }
}

@Preview
@Composable
fun ProgressBarPreview() {
    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().background(color = Color.White)
    ) {
        androidx.compose.material3.Button(onClick = {
            loading = true
            scope.launch {
                loadProgress { progress ->
                    if (currentProgress <= 0.4f) {
                        currentProgress = progress
                    } else {
                        loading = false
                    }
                }
                loading = false // Reset loading when the coroutine finishes
            }
        }, enabled = !loading) {
            Text("Start loading")
        }
        ProgressBar(percentage = currentProgress)
    }
}