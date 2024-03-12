package com.example.feature_userinfo

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_userinfo.viewModel.UserInfoUiState
import com.example.feature_userinfo.viewModel.UserViewModel
import com.hmoa.component.TopBar
import com.hmoa.feature_userinfo.R

@Composable
fun EditProfileRoute(
    onNavBack: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()
    val isDuplicated = viewModel.isDuplicated.collectAsStateWithLifecycle()
    val isEnabled = viewModel.isEnabled.collectAsStateWithLifecycle()

    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){
        viewModel.updateProfile(it!!)
    }

    EditProfilePage(
        uiState = uiState.value,
        isDuplicated = isDuplicated.value,
        isEnabled = isEnabled.value,
        onUpdateNickname = {
            viewModel.updateNickname(it)
        },
        onSaveNickname = {
            viewModel.saveNickname()
        },
        onSaveProfile = {
            viewModel.saveProfile()
        },
        getImage = getImage,
        onNavBack = onNavBack
    )
}

@Composable
fun EditProfilePage(
    uiState : UserInfoUiState,
    isDuplicated : Boolean,
    isEnabled : Boolean,
    onUpdateNickname : (String) -> Unit,
    onSaveNickname : () -> Unit,
    onSaveProfile : () -> Unit,
    getImage : ManagedActivityResultLauncher<String, Uri?>,
    onNavBack: () -> Unit,
) {

    when (uiState) {
        is UserInfoUiState.Loading -> {

        }
        is UserInfoUiState.UserInfo -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                TopBar(
                    navIcon = painterResource(R.drawable.back_btn),
                    onNavClick = onNavBack,
                    title = "프로필 수정",
                )

                Spacer(Modifier.height(38.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.size(72.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        /** profile 이미지 정보 match */
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color(0xFFBBBBBB), shape = CircleShape),
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile"
                        )

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(color = Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                modifier = Modifier.size(16.dp),
                                onClick = {
                                    /** Camera App 연동해서 처리 */
                                    getImage.launch("image/*")
                                }
                            ) {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(R.drawable.profile_edit_btn),
                                    contentDescription = "Profile Edit Button",
                                    tint = Color(0xFFBBBBBB)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(72.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "닉네임",
                        color = Color(0xFF414141)
                    )

                    Spacer(Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            value = uiState.nickname,
                            onValueChange = {
                                onUpdateNickname(it)
                            },
                            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                            decorationBox = {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .height(45.dp)
                                            .padding(start = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = uiState.nickname,
                                        )
                                    }

                                    Divider()
                                }
                            }
                        )

                        Spacer(Modifier.width(8.dp))

                        Button(
                            shape = RoundedCornerShape(size = 5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDuplicated) Color.Black else Color(0xFFBBBBBB)
                            ),
                            onClick = {
                                /** 중복확인 API */
                            }
                        ) {
                            Text(
                                text = "중복확인",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = if (isDuplicated) "중복하는 아이디가 있습니다" else "사용할 수 있는 닉네임입니다",
                        color = if (isDuplicated) Color(0xFFEE5D5D) else Color(0xFF3596EF),
                        fontSize = 12.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                com.hmoa.core_designsystem.component.Button(
                    buttonModifier = Modifier
                        .height(82.dp)
                        .fillMaxWidth()
                        .background(color = if (isEnabled) Color.Black else Color(0xFFBBBBBB)),
                    isEnabled = isEnabled,
                    btnText = "변경",
                    onClick = {
                        onSaveNickname()
                        onSaveProfile()
                        onNavBack()
                    }
                )
            }
        }
    }
}