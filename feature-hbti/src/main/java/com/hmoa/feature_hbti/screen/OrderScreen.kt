package com.hmoa.feature_hbti.screen

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.concatWithComma
import com.hmoa.core_common.formatWon
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.CustomOutlinedTextField
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.TagBadge
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_domain.entity.data.WebviewType
import com.hmoa.core_model.data.DefaultAddressDto
import com.hmoa.core_model.data.DefaultOrderInfoDto
import com.hmoa.core_model.data.NoteProductIds
import com.hmoa.core_model.response.FinalOrderResponseDto
import com.hmoa.core_model.response.Note
import com.hmoa.core_model.response.NoteProduct
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.feature_hbti.BuildConfig
import com.hmoa.feature_hbti.viewmodel.OrderUiState
import com.hmoa.feature_hbti.viewmodel.OrderViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun OrderRoute(
    productIds: List<Int>,
    navBack: () -> Unit,
    navAddAddress: (String, String) -> Unit,
    navLogin: () -> Unit,
    navOrderResult: () -> Unit,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val isSaveBuyerInfo = viewModel.isSavedBuyerInfo.collectAsStateWithLifecycle()
    val isDone = viewModel.isDone.collectAsStateWithLifecycle()
    OrderScreen(
        uiState = uiState.value,
        errState = errState.value,
        isSaveBuyerInfo = isSaveBuyerInfo.value,
        onPaymentClick = { phone ->
            viewModel.doPayment(context, phone)
        },
        deleteNote = { viewModel.deleteNote(it) },
        saveBuyerInfo = { name, phoneNumber ->
            viewModel.saveBuyerInfo(name, phoneNumber)
        },
        onNavBack = navBack,
        navAddAddress = {
            val productIdsToJson = Json.encodeToString(NoteProductIds(productIds))
            navAddAddress(it, productIdsToJson)
        },
        navLogin = navLogin
    )
    LaunchedEffect(Unit) { viewModel.setIds(productIds) }
    LaunchedEffect(isDone.value) {
        if (isDone.value) {
            navOrderResult()
        }
    }
}

@Composable
fun OrderScreen(
    uiState: OrderUiState,
    errState: ErrorUiState,
    isSaveBuyerInfo: Boolean,
    onPaymentClick: (String) -> Unit,
    deleteNote: (id: Int) -> Unit,
    saveBuyerInfo: (name: String, phoneNumber: String) -> Unit,
    onNavBack: () -> Unit,
    navLogin: () -> Unit,
    navAddAddress: (String) -> Unit
) {
    when (uiState) {
        OrderUiState.Loading -> AppLoadingScreen()
        is OrderUiState.Success -> {
            OrderScreenMainContent(
                isSaveBuyerInfo = isSaveBuyerInfo,
                orderInfo = uiState.orderInfo,
                addressInfo = uiState.addressInfo,
                buyerInfo = uiState.buyerInfo,
                deleteNote = deleteNote,
                saveBuyerInfo = saveBuyerInfo,
                onPaymentClick = onPaymentClick,
                onNavBack = onNavBack,
                navAddAddress = navAddAddress
            )
        }

        OrderUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navLogin,
                errorUiState = errState,
                onCloseClick = {
                    onNavBack()
                }
            )
        }
    }
}

@Composable
private fun OrderScreenMainContent(
    isSaveBuyerInfo: Boolean,
    orderInfo: FinalOrderResponseDto,
    addressInfo: DefaultAddressDto?,
    buyerInfo: DefaultOrderInfoDto?,
    deleteNote: (id: Int) -> Unit,
    saveBuyerInfo: (name: String, phoneNumber: String) -> Unit,
    onPaymentClick: (String) -> Unit,
    onNavBack: () -> Unit,
    navAddAddress: (String) -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf("") }
    var phone1 by remember { mutableStateOf("") }
    var phone2 by remember { mutableStateOf("") }
    var phone3 by remember { mutableStateOf("") }
    var isRefundChecked by remember { mutableStateOf(false) }
    var isPrivacyConsentGranted by remember { mutableStateOf(false) }
    var isAllChecked by remember { mutableStateOf(false) }
    val isEnabled = remember { derivedStateOf { addressInfo != null && buyerInfo != null && isAllChecked } }
    var flag by remember { mutableStateOf(false) }
    var showWebView by remember { mutableStateOf(false) }
    var webViewType by remember { mutableStateOf<WebviewType?>(null) }
    BackHandler(
        enabled = true,
        onBack = {
            if (showWebView) showWebView = false
            else onNavBack()
        }
    )
    if (showWebView) {
        NotificationWebView(webViewType)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(bottom = 52.dp)
        ) {
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
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "주문자 정보",
                        fontSize = 16.sp,
                        fontFamily = CustomFont.bold
                    )
                    Text(
                        modifier = if (!isSaveBuyerInfo) Modifier.clickable {
                            if (name.isNotEmpty() && phone1.isNotEmpty() && phone2.isNotEmpty() && phone3.isNotEmpty()) {
                                val phone = "${phone1}-${phone2}-${phone3}"
                                saveBuyerInfo(name, phone)
                            } else {
                                Toast.makeText(context, "정보를 모두 작성한 후 저장해주세요", Toast.LENGTH_SHORT).show()
                            }
                        } else Modifier,
                        text = if (!isSaveBuyerInfo) "작성한 정보 저장하기" else "",
                        fontSize = 10.sp,
                        fontFamily = CustomFont.medium,
                        textDecoration = TextDecoration.Underline
                    )
                }
                if (isSaveBuyerInfo) {
                    UserInfoDesc(
                        buyerInfo = buyerInfo!!,
                        addressInfo = addressInfo,
                        navAddAddress = navAddAddress
                    )
                } else {
                    InputUserInfo(
                        name = name,
                        onNameChanged = { name = it },
                        phone1 = phone1,
                        onPhone1Changed = { phone1 = it },
                        phone2 = phone2,
                        onPhone2Changed = { phone2 = it },
                        phone3 = phone3,
                        onPhone3Changed = { phone3 = it },
                        navAddAddress = {
                            if (isSaveBuyerInfo) {
                                navAddAddress(it)
                            } else {
                                Toast.makeText(context, "배송자 정보를 먼저 입력해주세요", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                ProductInfo(
                    notes = orderInfo.productInfo.noteProducts,
                    deleteNote = { deleteNote(it) }
                )
                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                Receipt(
                    shippingPayment = orderInfo.shippingAmount,
                    totalPayment = orderInfo.totalAmount,
                    finalPayment = orderInfo.paymentAmount
                )
                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                CheckPrivacyConsent(
                    isAllChecked = isAllChecked,
                    onUpdateAllChecked = {
                        flag = true
                        isAllChecked = it
                    },
                    isRefundChecked = isRefundChecked,
                    onUpdateRefundChecked = { isRefundChecked = it },
                    isPrivacyConsentGranted = isPrivacyConsentGranted,
                    onUpdatePrivacyConsentGranted = { isPrivacyConsentGranted = it },
                    showPrivacyConsent = {
                        webViewType = WebviewType.PRIVACY_CONSENT
                        showWebView = true
                    },
                    showShippingRefund = {
                        webViewType = WebviewType.SHIPPING_REFUND
                        showWebView = true
                    }
                )
                Button(
                    buttonModifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    isEnabled = isEnabled.value,
                    btnText = "결제하기",
                    onClick = { onPaymentClick("${phone1}-${phone2}-${phone3}") }
                )
            }
        }
    }
    LaunchedEffect(buyerInfo) {
        if (buyerInfo != null) {
            name = buyerInfo.name
            phone1 = buyerInfo.phoneNumber.substring(0, 3)
            phone2 = buyerInfo.phoneNumber.substring(4, 8)
            phone3 = buyerInfo.phoneNumber.substring(9)
        }
    }
    LaunchedEffect(isAllChecked) {
        if (flag) {
            isRefundChecked = isAllChecked
            isPrivacyConsentGranted = isAllChecked
        }
    }
    LaunchedEffect(isRefundChecked, isPrivacyConsentGranted) {
        isAllChecked = isRefundChecked && isPrivacyConsentGranted
        flag = false
    }
}

@Composable
private fun InputUserInfo(
    name: String,
    onNameChanged: (newName: String) -> Unit,
    phone1: String,
    onPhone1Changed: (newPhone1: String) -> Unit,
    phone2: String,
    onPhone2Changed: (newPhone2: String) -> Unit,
    phone3: String,
    onPhone3Changed: (newPhone3: String) -> Unit,
    navAddAddress: (String) -> Unit,
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
            onValueChanged = onNameChanged,
            color = Color.Black,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            borderWidth = 1.dp,
            borderColor = CustomColor.gray1,
            borderShape = RoundedCornerShape(size = 5.dp),
            padding = PaddingValues(horizontal = 12.dp),
            placeHolder = "이름",
        )
        Spacer(Modifier.height(16.dp))
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
                onValueChanged = {
                    if (it.length <= 3) {
                        onPhone1Changed(it)
                    }
                },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(horizontal = 12.dp),
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
                onValueChanged = {
                    if (it.length <= 4) {
                        onPhone2Changed(it)
                    }
                },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(horizontal = 12.dp),
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
                onValueChanged = {
                    if (it.length <= 4) {
                        onPhone3Changed(it)
                    }
                },
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                borderWidth = 1.dp,
                borderColor = CustomColor.gray1,
                borderShape = RoundedCornerShape(size = 5.dp),
                padding = PaddingValues(horizontal = 12.dp),
                placeHolder = "5678",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )
        }
        Spacer(Modifier.height(24.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.Black)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "배송지",
                fontSize = 18.sp,
                fontFamily = CustomFont.bold
            )
            Text(
                modifier = Modifier.clickable { navAddAddress("NULL") },
                text = "배송지를 입력해주세요",
                fontSize = 10.sp,
                fontFamily = CustomFont.medium,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
private fun UserInfoDesc(
    buyerInfo: DefaultOrderInfoDto,
    addressInfo: DefaultAddressDto?,
    navAddAddress: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (addressInfo == null) buyerInfo.name else addressInfo.name,
                fontSize = 14.sp,
                fontFamily = CustomFont.semiBold,
                style = TextStyle(
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = true)
                )
            )
            if (addressInfo != null) {
                Spacer(Modifier.width(8.dp))
                TagBadge(
                    tag = addressInfo.addressName
                )
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier.clickable {
                        val addressInfoToJson = Json.encodeToString(addressInfo)
                        navAddAddress(addressInfoToJson)
                    },
                    text = "변경하기",
                    fontSize = 10.sp,
                    fontFamily = CustomFont.medium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = if (addressInfo == null) buyerInfo.phoneNumber else addressInfo.phoneNumber,
            fontSize = 12.sp,
            fontFamily = CustomFont.medium,
            color = CustomColor.gray3
        )
        if (addressInfo != null) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "${addressInfo.streetAddress} ${addressInfo.detailAddress}",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium
            )
            Spacer(Modifier.height(24.dp))
        } else {
            Spacer(Modifier.height(24.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "배송지",
                    fontSize = 18.sp,
                    fontFamily = CustomFont.bold
                )
                Text(
                    modifier = Modifier.clickable { navAddAddress("NULL") },
                    text = "배송지를 입력해주세요",
                    fontSize = 10.sp,
                    fontFamily = CustomFont.medium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
private fun ProductInfo(
    notes: List<NoteProduct>,
    deleteNote: (id: Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "상품 정보",
                fontSize = 18.sp,
                fontFamily = CustomFont.bold
            )
            Text(
                text = "11시 이전 결제 건까지 당일 발송",
                fontSize = 11.sp,
                lineHeight = 20.sp,
                letterSpacing = (-0.4).sp,
                fontFamily = CustomFont.regular,
                color = CustomColor.gray4
            )
        }
        notes.forEach { note ->
            NoteItem(note = note, deleteNote = deleteNote)
            if (notes.lastIndex != notes.indexOf(note)) {
                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray1)
            }
        }
    }
}

@Composable
private fun NoteItem(note: NoteProduct, deleteNote: (id: Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        CircleImageView(
            imgUrl = note.productPhotoUrl,
            width = 60,
            height = 60
        )
        Spacer(Modifier.width(20.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = note.productName,
                fontSize = 14.sp,
                fontFamily = CustomFont.semiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = concatWithComma(note.notes.map { it.noteName }),
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
        ) {
            IconButton(
                modifier = Modifier.size(10.dp),
                onClick = { deleteNote(note.productId) }
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
private fun Receipt(
    shippingPayment: Int,
    totalPayment: Int,
    finalPayment: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "결제금액",
                fontSize = 18.sp,
                fontFamily = CustomFont.bold
            )
            Text(
                text = "${formatWon(totalPayment)}원",
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
        ) {
            Text(
                text = "총 상품금액",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Text(
                text = "${formatWon(finalPayment)}원",
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
        ) {
            Text(
                text = "배송비",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Text(
                text = "${formatWon(shippingPayment)}원",
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
        ) {
            Text(
                text = "총 결제금액",
                fontSize = 12.sp,
                fontFamily = CustomFont.semiBold
            )
            Text(
                text = "${formatWon(totalPayment)}원",
                fontSize = 12.sp,
                fontFamily = CustomFont.semiBold
            )
        }
    }
}

@Composable
private fun CheckPrivacyConsent(
    isAllChecked: Boolean,
    onUpdateAllChecked: (Boolean) -> Unit,
    isRefundChecked: Boolean,
    onUpdateRefundChecked: (Boolean) -> Unit,
    isPrivacyConsentGranted: Boolean,
    onUpdatePrivacyConsentGranted: (Boolean) -> Unit,
    showPrivacyConsent: () -> Unit,
    showShippingRefund: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { onUpdateAllChecked(!isAllChecked) }
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(if (isAllChecked) com.hmoa.core_designsystem.R.drawable.ic_check else com.hmoa.core_designsystem.R.drawable.ic_check_empty),
                    contentDescription = "Privacy Consent"
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = "주문 내용을 확인했으며, 아래의 정보 제공 및 결제에 모두 동의합니다.",
                fontSize = 14.sp,
                fontFamily = CustomFont.bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { onUpdateRefundChecked(!isRefundChecked) }
            ) {
                Icon(
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_check_not_have_background),
                    tint = if (isRefundChecked) Color.Black else CustomColor.gray3,
                    contentDescription = "Check"
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = "배송/취소/반품/교환정책",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.clickable { showShippingRefund() },
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
                .padding(top = 16.dp, bottom = 27.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { onUpdatePrivacyConsentGranted(!isPrivacyConsentGranted) }
            ) {
                Icon(
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_check_not_have_background),
                    tint = if (isPrivacyConsentGranted) Color.Black else CustomColor.gray3,
                    contentDescription = "Check"
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = "개인정보 제공 동의",
                fontSize = 12.sp,
                fontFamily = CustomFont.medium,
                color = CustomColor.gray3
            )
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.clickable { showPrivacyConsent() },
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
private fun NotificationWebView(webviewType: WebviewType?) {
    val context = LocalContext.current
    val url = when (webviewType) {
        WebviewType.PRIVACY_CONSENT -> BuildConfig.PRIVACY_CONSENT_URL
        WebviewType.SHIPPING_REFUND -> BuildConfig.SHIPPING_REFUND_URL
        else -> ""
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.domStorageEnabled = true
                webViewClient = object : WebViewClient() {

                }

                loadUrl(url)
            }
        }
    )
}

@Composable
@Preview
private fun UITest() {
    val name = "서호준"
    val phoneNumber = "010-6472-37863"
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
        uiState = OrderUiState.Success(
            buyerInfo = DefaultOrderInfoDto(name, phoneNumber),
            addressInfo = null,
            orderInfo = FinalOrderResponseDto(15000, testData, 3000, 15000)
        ),
        errState = ErrorUiState.Loading,
        isSaveBuyerInfo = false,
        saveBuyerInfo = { a, b ->

        },
        onPaymentClick = { a ->

        },
        onNavBack = {},
        deleteNote = { },
        navAddAddress = {},
        navLogin = {}
    )
}