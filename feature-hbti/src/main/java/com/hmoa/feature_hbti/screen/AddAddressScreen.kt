package com.hmoa.feature_hbti.screen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.CustomOutlinedTextField
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.data.DefaultAddressDto
import com.hmoa.feature_hbti.BuildConfig
import com.hmoa.feature_hbti.viewmodel.AddAddressViewModel
import kotlinx.serialization.json.Json

@Composable
fun AddAddressRoute(
    addressJson: String?,
    productIds: String?,
    navOrder: (String) -> Unit,
    viewModel: AddAddressViewModel = hiltViewModel()
) {
    val errorState = viewModel.errorState.collectAsStateWithLifecycle()
    val isPostAddressCompleted by viewModel.isPostAddressCompleted.collectAsStateWithLifecycle()
    AddAddressScreen(
        addressJson = addressJson,
        errorState = errorState.value,
        onPostAddressClick = { name, addressName, phone, homePhone, postalCode, address, detailAddress, request ->
            viewModel.postAddress(name, addressName, phone, homePhone, postalCode, address, detailAddress, request)
        },
        navOrder = {navOrder(productIds ?: "")},
    )
    LaunchedEffect(isPostAddressCompleted) {
        if (isPostAddressCompleted) navOrder(productIds ?: "")
    }
}

@Composable
fun AddAddressScreen(
    addressJson: String?,
    errorState: ErrorUiState,
    onPostAddressClick: (name: String, addressName: String, phone: String, homePhone: String, postalCode: String, address: String, detailAddress: String, request: String) -> Unit,
    navOrder: () -> Unit,
) {
    var isOpen by remember { mutableStateOf(true) }

    if (errorState is ErrorUiState.ErrorData && (errorState.expiredTokenError || errorState.wrongTypeTokenError || errorState.unknownError || errorState.generalError.first)) {
        ErrorUiSetView(
            isOpen = isOpen,
            onConfirmClick = navOrder,
            errorUiState = errorState,
            onCloseClick = navOrder
        )
    } else {
        AddAddressMainContent(
            addressJson = addressJson,
            onPostAddressClick = onPostAddressClick,
            navOrder = navOrder
        )
    }
}

@Composable
private fun AddAddressMainContent(
    addressJson: String?,
    onPostAddressClick: (name: String, addressName: String, phone: String, homePhone: String, postalCode: String, address: String, detailAddress: String, request: String) -> Unit,
    navOrder: () -> Unit
) {
    var name by remember{ mutableStateOf("") }
    var addressName by remember{mutableStateOf("")}
    var phone1 by remember{mutableStateOf("")}
    var phone2 by remember{mutableStateOf("")}
    var phone3 by remember{mutableStateOf("")}
    var homePhone1 by remember{mutableStateOf("")}
    var homePhone2 by remember{mutableStateOf("")}
    var homePhone3 by remember{mutableStateOf("")}
    var postalCode by remember{mutableStateOf("")}
    var address by remember{mutableStateOf("")}
    var detailAddress by remember{mutableStateOf("")}
    var request by remember{mutableStateOf("")}
    if (!addressJson.isNullOrEmpty()){
        val initAddress = Json.decodeFromString<DefaultAddressDto>(addressJson)
        name = initAddress.name
        addressName = initAddress.addressName
        phone1 = initAddress.phoneNumber.substring(0,3)
        phone2 = initAddress.phoneNumber.substring(4,8)
        phone3 = initAddress.phoneNumber.substring(9,12)
        homePhone1 = if(initAddress.landlineNumber[1] == '1') initAddress.landlineNumber.substring(0,3) else initAddress.landlineNumber.substring(0,2)
        homePhone2 = if(initAddress.landlineNumber[1] == '1') initAddress.landlineNumber.substring(4,8) else initAddress.landlineNumber.substring(3,7)
        homePhone3 = if(initAddress.landlineNumber[1] == '1') initAddress.landlineNumber.substring(9,13) else initAddress.landlineNumber.substring(8,12)
        postalCode = initAddress.zipCode
        address = initAddress.streetAddress
        detailAddress = initAddress.detailAddress
        request = initAddress.request
    }
    val scrollState = rememberScrollState()
    var showWebView by remember{mutableStateOf(false)}
    var isEnabled = remember{ derivedStateOf{
        name.isNotEmpty() && addressName.isNotEmpty()
                && phone1.isNotEmpty() && phone2.isNotEmpty() && phone3.isNotEmpty()
                && homePhone1.isNotEmpty() && homePhone2.isNotEmpty() && homePhone3.isNotEmpty()
                && postalCode.isNotEmpty() && address.isNotEmpty()
                && detailAddress.isNotEmpty() && request.isNotEmpty()
    }}

    BackHandler(
        enabled = true,
        onBack = {
            if(showWebView) showWebView = false
            else navOrder()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        if (showWebView){
            AddressWebView(
                onUpdateAddress = { newZoneCode, newAddress ->
                    postalCode = newZoneCode
                    address = newAddress
                },
                onDismiss = {showWebView = false}
            )
        }
        TopBar(
            title = "주소 추가",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = navOrder
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "주문자 정보",
                fontSize = 18.sp,
                fontFamily = CustomFont.bold
            )
            Spacer(Modifier.height(20.dp))
            InputName(
                name = name,
                onChangeName = { name = it }
            )
            InputAddressName(
                addressName = addressName,
                onAddressChange = { addressName = it }
            )
            InputPhone(
                phone1 = phone1,
                onPhone1Change = { phone1 = it },
                phone2 = phone2,
                onPhone2Change = { phone2 = it },
                phone3 = phone3,
                onPhone3Change = { phone3 = it },
            )
            InputHomePhone(
                homePhone1 = homePhone1,
                onHomePhone1Change = { homePhone1 = it },
                homePhone2 = homePhone2,
                onHomePhone2Change = { homePhone2 = it },
                homePhone3 = homePhone3,
                onHomePhone3Change = { homePhone3 = it },
            )
            InputAddress(
                postalCode = postalCode,
                address = address,
                detailAddress = detailAddress,
                onShowWebViewClick = {showWebView = true},
                onUpdateDetailAddress = {detailAddress = it}
            )
            InputRequest(
                request = request,
                onRequestChange = { request = it }
            )
            com.hmoa.core_designsystem.component.Button(
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                isEnabled = isEnabled.value,
                btnText = "작성 완료",
                onClick = {
                    onPostAddressClick(
                        name,
                        addressName,
                        "${phone1}-${phone2}-${phone3}",
                        "${homePhone1}-${homePhone2}-${homePhone3}",
                        postalCode,
                        address,
                        detailAddress,
                        request
                    )
                },
                radious = 5
            )
        }
    }
}

@Composable
private fun InputName(
    name: String,
    onChangeName: (newName: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
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
            onValueChanged = onChangeName,
            color = Color.Black,
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
private fun InputPhone(
    phone1: String,
    onPhone1Change: (newPhone1: String) -> Unit,
    phone2: String,
    onPhone2Change: (newPhone2: String) -> Unit,
    phone3: String,
    onPhone3Change: (newPhone3: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "휴대전화",
            fontSize = 12.sp,
            fontFamily = CustomFont.medium
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone1,
                onValueChanged = { if(it.length <= 3) {onPhone1Change(it)} },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "010",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )
            Row(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 5.dp)
            ) {
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone2,
                onValueChanged = { if(it.length <= 4) {onPhone2Change(it)} },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "1234",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )
            Row(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 5.dp)
            ) {
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = phone3,
                onValueChanged = { if(it.length <= 4) {onPhone3Change(it)} },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "5678",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun InputAddressName(
    addressName: String,
    onAddressChange: (newAddressName: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
    ) {
        Text(
            text = "배송지",
            fontSize = 12.sp,
            fontFamily = CustomFont.medium
        )
        Spacer(Modifier.height(10.dp))
        CustomOutlinedTextField(
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth(),
            value = addressName,
            onValueChanged = onAddressChange,
            color = Color.Black,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(start = 12.dp),
            placeHolder = "배송지명(선택)",
        )
    }
}

@Composable
private fun InputHomePhone(
    homePhone1: String,
    onHomePhone1Change: (newHomePhone1: String) -> Unit,
    homePhone2: String,
    onHomePhone2Change: (newHomePhone2: String) -> Unit,
    homePhone3: String,
    onHomePhone3Change: (newHomePhone3: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "전화번호",
            fontSize = 12.sp,
            fontFamily = CustomFont.medium
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = homePhone1,
                onValueChanged = { if(it.length <= 3) {onHomePhone1Change(it)} },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "010",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )
            Row(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 5.dp)
            ) {
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = homePhone2,
                onValueChanged = { if(it.length <= 4) {onHomePhone2Change(it)} },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "1234",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )
            Row(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 5.dp)
            ) {
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                value = homePhone3,
                onValueChanged = { if(it.length <= 4) {onHomePhone3Change(it)} },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(start = 12.dp),
                placeHolder = "5678",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun InputAddress(
    postalCode: String,
    address: String,
    detailAddress: String,
    onUpdateDetailAddress: (detailAddress: String) -> Unit,
    onShowWebViewClick: () -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
            value = postalCode,
            onValueChange = {},
            enabled = false,
            textStyle = TextStyle(
                color = Color.Black,
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
                ) {
                    if (postalCode.isEmpty()) {
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
                        onClick = onShowWebViewClick,
                        contentPadding = PaddingValues(0.dp)
                    ) {
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
            onValueChanged = { },
            enabled = true,
            color = Color.Black,
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
            onValueChanged = onUpdateDetailAddress,
            color = Color.Black,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(start = 12.dp),
            placeHolder = "상세주소"
        )
        Spacer(Modifier.height(16.dp))
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun AddressWebView(
    onUpdateAddress: (zoneCode: String, address: String) -> Unit,
    onDismiss: () -> Unit,
){
    val context = LocalContext.current
    class MyJavaScriptInterface{
        @JavascriptInterface
        @Suppress("unused")
        fun processData(zoneCode: String, address: String){
            onUpdateAddress(zoneCode, address)
            onDismiss()
        }
    }

    AndroidView(
        factory = {
            WebView(context).apply{
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                addJavascriptInterface(MyJavaScriptInterface(), "Android")
                webViewClient = object: WebViewClient(){
                    override fun onPageFinished(view: WebView?, url: String?) {
                        loadUrl("javascript:sample2_execDaumPostcode();")
                    }
                }

                loadUrl(BuildConfig.ADDRESS_SEARCH_URL)
            }
        }
    )
}

@Composable
private fun InputRequest(
    request: String,
    onRequestChange: (newRequest: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
            onValueChanged = onRequestChange,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(horizontal = 12.dp),
            placeHolder = "배송 시 요청사항을 적어주세요"
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Preview
@Composable
private fun UiTest() {
    AddAddressScreen(
        errorState = ErrorUiState.Loading,
        navOrder = {},
        onPostAddressClick = { a, b, c, d, e, f, g, h ->

        },
        addressJson = null
    )
}