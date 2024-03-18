package com.hmoa.core_designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.theme.CustomColor


@Composable
fun customSliderColors(): SliderColors = SliderDefaults.colors(
    activeTrackColor = CustomColor.gray1,
    inactiveTrackColor = Color.Black,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(value: Float = 0f, onValueChangedFinished: (value: Float) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    var sliderPosition = remember { mutableFloatStateOf(value) }
    Slider(
        value = sliderPosition.value,
        onValueChange = {
            sliderPosition.value = it
        },
        modifier = Modifier.fillMaxWidth().height(52.dp),
        valueRange = 0f..50f,
        steps = 10,
        onValueChangeFinished = { onValueChangedFinished },
        enabled = true,
        interactionSource = interactionSource,
        thumb = {
            Row{
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(color = CustomColor.gray2, shape = RoundedCornerShape(size = 5.dp))
                        .indication(
                            interactionSource = interactionSource,
                            indication = rememberRipple(
                                bounded = true,
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Sliding Icon",
                        tint = Color.White,
                    )
                }
            }
        },
        track = {
            SliderDefaults.Track(
                sliderPositions = SliderPositions(),
                colors = customSliderColors(),
                modifier = Modifier.scale(scaleX = 2f, scaleY = 52f).fillMaxWidth().border(BorderStroke(1.dp, color = Color.Transparent),shape = RoundedCornerShape(5.dp)),
                enabled = true
            )
        }

    )
}



@Composable
@Preview
fun CustomSliderPreview() {
    CustomSlider(10f, {})
}