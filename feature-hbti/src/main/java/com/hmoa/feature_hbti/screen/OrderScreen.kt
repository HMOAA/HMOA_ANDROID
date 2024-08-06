package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.concatWithComma
import com.hmoa.core_common.formatWon
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.CustomOutlinedTextField
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.Note
import com.hmoa.core_model.response.NoteProduct
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.feature_hbti.viewmodel.OrderUiState
import com.hmoa.feature_hbti.viewmodel.OrderViewModel

@Composable
fun OrderRoute(
    productIds: List<Int>,
    onNavBack: () -> Unit,
    viewModel: OrderViewModel = hiltViewModel()
){
    viewModel.setIds(productIds)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    OrderScreen (
        uiState = uiState.value,
        errState = errState.value,
        onNavBack = onNavBack
    )
}

@Composable
fun OrderScreen(
    uiState: OrderUiState,
    errState: ErrorUiState,
    onNavBack: () -> Unit,
){
    when(uiState){
        OrderUiState.Loading -> AppLoadingScreen()
        is OrderUiState.Success -> {
            OrderScreenMainContent(
                totalPrice = uiState.noteResult.totalPrice,
                noteProducts = uiState.noteResult.noteProducts,
                onNavBack = onNavBack
            )
        }
        OrderUiState.Error -> {
            ErrorUiSetView(
                onConfirmClick = onNavBack,
                errorUiState = errState,
                onCloseClick = onNavBack
            )
        }
    }
}
@Composable
private fun OrderScreenMainContent(
    totalPrice: Int,
    noteProducts: List<NoteProduct>,
    onNavBack: () -> Unit
){
    val scrollState = rememberScrollState()
    var name by remember{mutableStateOf("")}
    var phone1 by remember{mutableStateOf("")}
    var phone2 by remember{mutableStateOf("")}
    var phone3 by remember{mutableStateOf("")}
    val options = listOf("토스페이", "카카오페이", "페이코", "일반 결제")
    var selectedOption by remember{mutableStateOf(options[0])}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(bottom = 52.dp)
    ){
        TopBar(
            title = "주문서 작성",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack
        )
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(state = scrollState)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "주문자 정보",
                    fontSize = 16.sp,
                    fontFamily = CustomFont.bold
                )
                Text(
                    modifier = Modifier.clickable{
                        /** 작성한 정보 저장하기 */
                    },
                    text = "작성한 정보 저장하기",
                    fontSize = 10.sp,
                    fontFamily = CustomFont.medium,
                    textDecoration = TextDecoration.Underline
                )
            }
            InputName(name = name, onNameChanged = {name = it})
            InputPhone(
                phone1 = phone1,
                onPhone1Changed = {phone1 = it},
                phone2 = phone2,
                onPhone2Changed = {phone2 = it},
                phone3 = phone3,
                onPhone3Changed = {phone3 = it}
            )
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            InputAddress()
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            ProductInfo(noteProducts)
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            Receipt(totalPrice)
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            SelectPaymentType(
                options = options,
                selectedOption = selectedOption,
                onValueChanged = {selectedOption = it}
            )
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            CheckPrivacyConsent()
            Button(
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                isEnabled = true, /** 위의 사항 중 빈 것이 하나라도 있다면 false 다 차 있으면 true */
                btnText = "결제하기",
                onClick = { /** 결제 && navigation */ }
            )
        }
    }
}
@Composable
private fun InputName(
    name: String,
    onNameChanged: (newName: String) -> Unit,
){
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
            onValueChanged = onNameChanged,
            color = CustomColor.gray2,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(horizontal = 12.dp),
            placeHolder = "이름",
        )
    }
}

@Composable
private fun InputPhone(
    phone1: String,
    onPhone1Changed: (newPhone1: String) -> Unit,
    phone2: String,
    onPhone2Changed: (newPhone2: String) -> Unit,
    phone3: String,
    onPhone3Changed: (newPhone3: String) -> Unit,
){
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
                onValueChanged = onPhone1Changed,
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(horizontal = 12.dp),
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
                onValueChanged = onPhone2Changed,
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(horizontal = 12.dp),
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
                onValueChanged = onPhone3Changed,
                color = CustomColor.gray2,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(horizontal = 12.dp),
                placeHolder = "5678"
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun InputAddress(){
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
private fun ProductInfo(notes: List<NoteProduct>){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ){
        Text(
            text = "상품 정보",
            fontSize = 18.sp,
            fontFamily = CustomFont.bold
        )
        notes.forEach{ note ->
            NoteItem(note)
            if (notes.lastIndex != notes.indexOf(note)){
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
        }
    }
}

@Composable
private fun NoteItem(note: NoteProduct){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ){
        CircleImageView(
            imgUrl = note.productPhotoUrl,
            width = 60,
            height = 60
        )
        Spacer(Modifier.width(20.dp))
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = note.productName,
                fontSize = 14.sp,
                fontFamily = CustomFont.semiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = concatWithComma(note.notes.map{it.noteName}),
                fontSize = 10.sp,
                fontFamily = CustomFont.regular
            )
            Spacer(Modifier.height(18.dp))
            Text(
                text = "수량 ${note.notesCount}개",
                fontSize = 10.sp,
                fontFamily = CustomFont.regular,
                color = CustomColor.gray3
            )
        }
        Column(
            modifier = Modifier.wrapContentWidth(),
            horizontalAlignment = Alignment.End
        ){
            IconButton(
                modifier = Modifier.size(10.dp),
                onClick = { /** 해당 품목 삭제 */ }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.btn_close),
                    tint = CustomColor.gray3,
                    contentDescription = "Delete Product"
                )
            }
            Spacer(Modifier.height(22.dp))
            Text(
                text = "1,200원/개",
                fontSize = 10.sp,
                fontFamily = CustomFont.regular,
                color = CustomColor.gray3
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${formatWon(note.notesCount * 1200)}원",
                fontSize = 14.sp,
                fontFamily = CustomFont.semiBold
            )
        }
    }
}

@Composable
private fun Receipt(totalPrice: Int){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "결제금액",
                fontSize = 18.sp,
                fontFamily = CustomFont.bold
            )
            Text(
                text = "${formatWon(totalPrice + 3000)}원",
                fontSize = 20.sp,
                fontFamily = CustomFont.bold,
                color = CustomColor.red
            )
        }
        HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "총 상품금액",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Text(
                text = "${formatWon(totalPrice)}원",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "배송비",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Text(
                text = "3,000원",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
        }
        HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "총 결제금액",
                fontSize = 12.sp,
                fontFamily = CustomFont.semiBold
            )
            Text(
                text = "${formatWon(totalPrice + 3000)}원",
                fontSize = 12.sp,
                fontFamily = CustomFont.semiBold
            )
        }
    }
}

@Composable
private fun SelectPaymentType(
    options: List<String>,
    selectedOption: String,
    onValueChanged: (value: String) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 20.dp)
    ){
        Text(
            text = "결제수단",
            fontSize = 18.sp,
            fontFamily = CustomFont.bold
        )
        Spacer(Modifier.height(20.dp))
        VerticalOptions(
            options = options,
            selectedOption = selectedOption,
            onValueChanged = onValueChanged
        )
    }
}

@Composable
private fun VerticalOptions(
    options: List<String>,
    selectedOption: String,
    onValueChanged: (value: String) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ){
        options.forEach{
            val isSelected = it == selectedOption
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { onValueChanged(it) }
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(if(isSelected) com.hmoa.core_designsystem.R.drawable.ic_check else com.hmoa.core_designsystem.R.drawable.ic_check_empty),
                        contentDescription = "Check Button"
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = it,
                    fontSize = 12.sp,
                    fontFamily = CustomFont.semiBold
                )
            }
            if (options.indexOf(it) != options.lastIndex){Spacer(Modifier.height(16.dp))}
        }
    }
}

@Composable
private fun CheckPrivacyConsent(){
    var isChecked by remember{mutableStateOf(false)}
    var isDetailChecked1 by remember{mutableStateOf(false)}
    var isDetailChecked2 by remember{mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { isChecked = !isChecked}
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(if(isChecked) com.hmoa.core_designsystem.R.drawable.ic_check else com.hmoa.core_designsystem.R.drawable.ic_check_empty),
                    contentDescription = "Privacy Consent"
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = "주문 내용을 확인했으며, 아래의 정보 제공 및 결제에 모두 동의합니다.",
                fontSize = 14.sp,
                fontFamily = CustomFont.bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ){
            IconButton(
                onClick = { isDetailChecked1 = !isDetailChecked1 }
            ) {
                Icon(
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_check_not_have_background),
                    tint = if(isDetailChecked1) Color.Black else CustomColor.gray3,
                    contentDescription = "Check"
                )
            }
            Spacer(Modifier.width(16.dp))
            Text(
                text = "환불 방법",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "보기",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3,
                textDecoration = TextDecoration.Underline
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 27.dp)
        ){
            IconButton(
                onClick = { isDetailChecked2 = !isDetailChecked2 }
            ) {
                Icon(
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_check_not_have_background),
                    tint = if(isDetailChecked2) Color.Black else CustomColor.gray3,
                    contentDescription = "Check"
                )
            }
            Spacer(Modifier.width(16.dp))
            Text(
                text = "개인정보 제공 동의",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "보기",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
@Preview
private fun UITest(){
    val testData = PostNoteSelectedResponseDto(
        noteProducts = listOf(
            NoteProduct(
                notes = listOf(
                    Note(
                        noteName = "통카빈",
                        noteContent = "열대 나무 씨앗을 말린 향신료"
                    ),
                    Note(
                        noteName = "넛맥",
                        noteContent = "넛맥이란 식물을 말린 향신료"
                    ),
                    Note(
                        noteName = "페퍼",
                        noteContent = "후추"
                    )
                ),
                notesCount = 3,
                price = 4800,
                productId = 0,
                productName = "프루트",
                productPhotoUrl = ""
            ),
            NoteProduct(
                notes = listOf(
                    Note(
                        noteName = "통카빈",
                        noteContent = "열대 나무 씨앗을 말린 향신료"
                    ),
                    Note(
                        noteName = "넛맥",
                        noteContent = "넛맥이란 식물을 말린 향신료"
                    ),
                    Note(
                        noteName = "페퍼",
                        noteContent = "후추"
                    )
                ),
                notesCount = 6,
                price = 4800,
                productId = 0,
                productName = "플로럴",
                productPhotoUrl = ""
            ),
            NoteProduct(
                notes = listOf(
                    Note(
                        noteName = "통카빈",
                        noteContent = "열대 나무 씨앗을 말린 향신료"
                    ),
                    Note(
                        noteName = "넛맥",
                        noteContent = "넛맥이란 식물을 말린 향신료"
                    ),
                    Note(
                        noteName = "페퍼",
                        noteContent = "후추"
                    )
                ),
                notesCount = 6,
                price = 6000,
                productId = 0,
                productName = "시트러스",
                productPhotoUrl = ""
            )
        ),
        totalPrice = 18000
    )
    OrderScreen(
        uiState = OrderUiState.Success(testData),
        errState = ErrorUiState.Loading,
        onNavBack = {}
    )
}