package com.hmoa.core_designsystem.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.theme.CustomColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** Iterate the progress value */
suspend fun loadProgress(stepSize: Float, updateProgress: (Float) -> Unit) {

    for (i in 1..100) {
        updateProgress(stepSize)
        delay(5)
    }
}

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

@Preview
@Composable
fun ProgressBarPreview() {
    var currentProgress by remember { mutableStateOf(0f) }
    var targetProgress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope() // Create a coroutine scope
    val additionalProgress = 0.1f

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().background(color = Color.White)
    ) {
        androidx.compose.material3.Button(onClick = {
            targetProgress += additionalProgress
            scope.launch {
                loadProgress(additionalProgress) { progress ->
                    if (currentProgress <= targetProgress) {
                        currentProgress += progress
                    }
                }
            }
        }) {
            Text("add")
        }

        ProgressBar(percentage = currentProgress)

        androidx.compose.material3.Button(onClick = {
            targetProgress -= additionalProgress
            scope.launch {
                loadProgress(additionalProgress) { progress ->
                    if (currentProgress >= targetProgress) {
                        currentProgress -= progress
                    }
                }
            }
        }) {
            Text("substract")
        }
    }
}
