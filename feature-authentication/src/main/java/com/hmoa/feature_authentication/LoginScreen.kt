package com.hmoa.feature_authentication

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.hmoa.core_designsystem.component.OAuthLoginButton
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_authentication.viewmodel.LoginViewModel


fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun requestGoogleLogin(context: Context): GoogleSignInClient {
    val clientId = context.getString(com.hmoa.feature_authentication.R.string.google_cloud_outh_client_id)
    Log.d(
        "feature-authentication",
        "requestGoogleLogin, clientId: ${clientId}"
    )
    val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestServerAuthCode(clientId) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
        .build()
    Log.d("feature-authentication", "requestGoogleLogin, googleSignInOption: ${googleSignInOption}")
    return GoogleSignIn.getClient(context.findActivity(), googleSignInOption)
}

@Composable
internal fun LoginRoute(
    onSignup: () -> Unit,
    onHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isAbleToGoHome by viewModel.isAbleToGoHome.collectAsStateWithLifecycle()
    val isNeedToSignup by viewModel.isNeedToSignUp.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context.findActivity()
    val googleSignInClient: GoogleSignInClient by lazy { requestGoogleLogin(context) }
    val googleAuthLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d(
                    "feature-authentication",
                    "result.data : ${result.data?.data}"
                )
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                Log.d(
                    "feature-authentication",
                    "task.givenName: ${task.result.givenName} ,task.serverAuthCode: ${task.result.serverAuthCode}"
                )
                try {
                    val account = task.getResult(ApiException::class.java)

                    // 이름, 이메일 등이 필요하다면 아래와 같이 account를 통해 각 메소드를 불러올 수 있다.
                    val userName = account.givenName
                    val serverAuth = account.serverAuthCode
                    Log.d(
                        "feature-authentication",
                        "googleAuthLauncher success --- userName: ${userName}, serverAuth: ${serverAuth}"
                    )
                    onSignup()

                } catch (e: Exception) {
                    Log.e("feature-authentication", "googleAuthLauncher error: ${e.stackTraceToString()}")
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.e(
                    "feature-authentication",
                    "result.data : ${result.data?.data ?: "nothing"}, resultCode:${result.resultCode}"
                )
            }
        }
    fun handleGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        Log.d("feature-authentication", "signInIntent: ${signInIntent}")
        googleAuthLauncher.launch(signInIntent)
    }
    LaunchedEffect(isAbleToGoHome) {
        if (isAbleToGoHome) {
            onHome()
        }
        if (isNeedToSignup) {
            onSignup()
        }
    }
    LoginScreen(
        onClickKakaoLogin = { viewModel.handleKakaoLogin() },
        onClickGoogleLogin = { handleGoogleLogin() },
        onHome = { onHome() })
}

@Composable
fun LoginScreen(
    onClickKakaoLogin: () -> Unit,
    onClickGoogleLogin: () -> Unit,
    onHome: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.fillMaxWidth().padding(top = 160.dp),
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_hmoa_logo),
                contentDescription = "OAuth Type Icon",
            )
            Spacer(modifier = Modifier.padding(top = 100.dp))
            OAuthLoginButton(
                backgroundColor = Color.White,
                iconId = com.hmoa.core_designsystem.R.mipmap.ic_google_foreground,
                iconSize = 40,
                buttonText = " Google로 로그인",
                textColor = Color.Black,
                textSize = 16,
                onPress = {
                    onClickGoogleLogin()
                },
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            OAuthLoginButton(
                backgroundColor = CustomColor.kakao,
                iconId = com.hmoa.core_designsystem.R.drawable.ic_kakao,
                iconSize = 13,
                buttonModifier = Modifier.padding(start = 15.dp),
                buttonText = "Kakaotalk으로 로그인",
                textColor = Color.Black,
                textSize = 16,
                onPress = {
                    onClickKakaoLogin()
                },
            )
        }
        ClickableText(
            text = AnnotatedString("로그인없이 사용하기"),
            onClick = { onHome() },
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 12.sp, color = CustomColor.gray4)
        )

    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
}