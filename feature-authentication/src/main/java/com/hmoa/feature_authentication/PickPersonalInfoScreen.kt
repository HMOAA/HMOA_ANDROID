package com.hmoa.feature_authentication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.Spinner
import com.hmoa.component.TopBar
import com.hmoa.component.YearPickerDialog
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.RadioButtonList
import com.hmoa.core_designsystem.theme.CustomColor
import kotlinx.coroutines.launch

@Composable
internal fun PickPersonalInfoRoute(
    onHomeClick: () -> Unit,
    onPickNicknameClick: () -> Unit,
) {
    PickPersonalInfoScreen(onHomeClick, onPickNicknameClick)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PickPersonalInfoScreen(onHomeClick: () -> Unit, onPickNicknameClick: () -> Unit) {
    var birthYear by remember { mutableStateOf<String?>(null) }
    var sex by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val yearList = (1950..2024).toList()
    var value by remember { mutableStateOf(2000) }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxHeight().background(color = Color.Gray),
                verticalArrangement = Arrangement.Bottom
            ) {
                YearPickerDialog(
                    yearList = yearList,
                    initialValue = value,
                    height = 380.dp,
                    onDismiss = { scope.launch { modalSheetState.hide() } },
                    onDoneClick = {
                        birthYear = it.toString()
                        scope.launch { modalSheetState.hide() }
                        Log.w("onDoneClick", "it:${it}, birthYear:${birthYear}")
                    })
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {
            Column {
                TopBar(
                    navIcon = painterResource(R.drawable.ic_back),
                    onNavClick = { onPickNicknameClick() },
                    title = "2/2"
                )
                Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                    Text(
                        "나에게 꼭 맞는\n향수 추천을 위한\n3초",
                        modifier = Modifier.padding(top = 60.dp),
                        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Medium)
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Text(
                        "출생연도와 성별을 설정하면\n나와 비슷한 사람들이 찾아보는 향수를 \n추천받을 수 있어요",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Thin)
                    )
                    Text(
                        "출생연도",
                        modifier = Modifier.padding(top = 30.dp),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = CustomColor.gray4)
                    )
                    Column {
                        Spinner(
                            width = 152.dp,
                            height = 46.dp,
                            value = birthYear,
                            onClick = { scope.launch { modalSheetState.show() } },
                            placeholder = "선택"
                        )
                    }
                }
                Column(modifier = Modifier.padding(top = 25.dp).padding(start = 5.dp)) { RadioButtonList(listOf("여성", "남성"), onButtonClick = { sex = it })
                }
            }
            Button(isAvailableNextButton(birthYear), "시작하기", { onHomeClick() }, Modifier.fillMaxWidth().height(80.dp))
        }
    }
}

fun isAvailableNextButton(birthYear:String?):Boolean{
    if(birthYear != null){
        return true
    }
    return false
}


@Preview
@Composable
fun PickPersonalInfoScreenPreview() {
    PickPersonalInfoScreen({}, {})
}

