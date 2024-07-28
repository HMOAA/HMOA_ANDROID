package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.CustomOutlinedTextField
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont

@Composable
fun AddAddressRoute(
    onNavBack: () -> Unit
){
    AddAddressScreen(
        onNavBack = onNavBack
    )
}

@Composable
fun AddAddressScreen(
    onNavBack: () -> Unit,
){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "주소 추가",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
        ){
            Text(
                text = "주문자 정보",
                fontSize = 18.sp,
                fontFamily = CustomFont.bold
            )
            Spacer(Modifier.height(20.dp))
            InputName()
            InputShippingAddress()
            InputPhone()
            InputHomePhone()
            InputAddress()
            InputRequest()
            Spacer(Modifier.height(24.dp))
            com.hmoa.core_designsystem.component.Button(
                buttonModifier = Modifier.fillMaxWidth().height(52.dp),
                isEnabled = true, /** 정보가 모두 작성되었을 때 true가 되도록 수정 */
                btnText = "작성 완료",
                onClick = { /** 작성 정보 저장 및 이전 화면으로 navigation */ },
                radious = 5
            )
        }
    }
}

@Composable
private fun InputName(){
    var name by remember{ mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ){
        Text(
            text = "이름",
            fontSize = 12.sp,
            fontFamily = CustomFont.medium
        )
        Spacer(Modifier.height(8.dp))
        CustomOutlinedTextField(
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth(),
            value = name,
            onValueChanged = {newName -> name = newName},
            color = CustomColor.gray2,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(start = 12.dp),
            placeHolder = "수령인",
        )
    }
}

@Composable
private fun InputPhone(){
    var phone1 by remember{mutableStateOf("")}
    var phone2 by remember{mutableStateOf("")}
    var phone3 by remember{mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ){
        Text(
            text = "휴대전화",
            fontSize = 12.sp,
            fontFamily = CustomFont.medium
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone1,
                onValueChanged = {newPhone1 -> phone1 = newPhone1},
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "010"
            )
            Row(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 5.dp)
            ){
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone2,
                onValueChanged = {newPhone2 -> phone2 = newPhone2},
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "1234"
            )
            Row(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 5.dp)
            ){
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone3,
                onValueChanged = {newPhone3 -> phone3 = newPhone3},
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "5678"
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun InputShippingAddress(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "배송지",
            fontSize = 18.sp,
            fontFamily = CustomFont.bold
        )
        Text(
            modifier = Modifier.clickable{
                /** 배송지 입력 이벤트 */
            },
            text = "배송지를 입력해주세요",
            fontSize = 10.sp,
            fontFamily = CustomFont.medium,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
private fun InputHomePhone(){
    var phone1 by remember{mutableStateOf("")}
    var phone2 by remember{mutableStateOf("")}
    var phone3 by remember{mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ){
        Text(
            text = "전화번호",
            fontSize = 12.sp,
            fontFamily = CustomFont.medium
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone1,
                onValueChanged = {newPhone1 -> phone1 = newPhone1},
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "010"
            )
            Row(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 5.dp)
            ){
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone2,
                onValueChanged = {newPhone2 -> phone2 = newPhone2},
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "1234"
            )
            Row(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 5.dp)
            ){
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone3,
                onValueChanged = {newPhone3 -> phone3 = newPhone3},
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "5678"
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun InputAddress(){
    var postalCode by remember{mutableStateOf<Int?>(null)}
    var address by remember{mutableStateOf("")}
    var detailAddress by remember{mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "주소",
            fontSize = 12.sp,
            fontFamily = CustomFont.medium
        )
        Spacer(Modifier.height(8.dp))
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .border(
                    width = 1.dp,
                    color = CustomColor.gray1,
                    shape = RoundedCornerShape(size = 5.dp)
                )
                .padding(horizontal = 12.dp),
            value = postalCode.toString(),
            onValueChange = {
                postalCode = it.toInt()
            },
            textStyle = TextStyle(
                fontSize = 12.sp,
                fontFamily = CustomFont.medium
            ),
            singleLine = true,
            maxLines = 1,
            decorationBox = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if (postalCode == null){
                        Text(
                            text = "우편번호",
                            fontSize = 12.sp,
                            fontFamily = CustomFont.medium,
                            color = CustomColor.gray2
                        )
                    } else {
                        it()
                    }
                    Button(
                        modifier = Modifier
                            .width(46.dp)
                            .height(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CustomColor.gray1),
                        shape = RoundedCornerShape(size = 5.dp),
                        onClick = {/** 주소 찾기 버튼 */},
                        contentPadding = PaddingValues(0.dp)
                    ){
                        Text(
                            text = "주소 찾기",
                            fontSize = 10.sp,
                            fontFamily = CustomFont.medium,
                            color = Color.Black
                        )
                    }
                }
            },
        )
        Spacer(Modifier.height(8.dp))
        CustomOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            value = address,
            onValueChanged = {newAddr -> address = newAddr},
            color = CustomColor.gray2,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(start = 12.dp),
            placeHolder = "주소"
        )
        Spacer(Modifier.height(8.dp))
        CustomOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            value = detailAddress,
            onValueChanged = {newDetailAddress -> detailAddress = newDetailAddress},
            color = CustomColor.gray2,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(start = 12.dp),
            placeHolder = "상세주소"
        )
    }
}

@Composable
private fun InputRequest(){
    var request by remember{mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "배송 요청사항(선택)",
            fontSize = 12.sp,
            fontFamily = CustomFont.medium
        )
        Spacer(Modifier.height(8.dp))
        CustomOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            value = request,
            onValueChanged = {newRequest -> request = newRequest},
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(horizontal = 12.dp),
            placeHolder = "배송 시 요청사항을 적어주세요"
        )
    }
}

@Preview
@Composable
private fun UiTest(){
    AddAddressScreen(
        onNavBack = {}
    )
}