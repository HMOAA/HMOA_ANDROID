package com.hmoa.feature_authentication.view

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.hmoa.core_designsystem.component.OAuthLoginButton
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_authentication.BuildConfig
import com.hmoa.feature_authentication.LoginEffect
import com.hmoa.feature_authentication.LoginEvent
import com.hmoa.feature_authentication.viewmodel.NewLoginViewModel

fun requestGoogleLogin(context: Context): GoogleSignInClient {
    val googleSignInOption =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode(BuildConfig.GOOGLE_CLOUD_OAUTH_CLIENT_ID)
            .build()
    return GoogleSignIn.getClient(context, googleSignInOption)
}

@Composable
fun LoginRoute(
    onSignup: (loginProvider: String) -> Unit,
    onHome: () -> Unit,
    nViewModel: NewLoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val googleSignInClient: GoogleSignInClient by lazy { requestGoogleLogin(context) }
    val googleAuthLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val serverAuth = account.serverAuthCode
                    nViewModel.handleEvent(LoginEvent.RequestGoogleToken(serverAuth))
                } catch (e: Exception) {
                    Log.e("feature-authentication", "googleAuthLauncher error: ${e.stackTraceToString()}")
                }
            }
        }

    fun handleGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    LaunchedEffect(Unit) {
        nViewModel.effects.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToHome -> onHome()
                is LoginEffect.NavigateToSignup -> onSignup(effect.loginProvider.name)
                LoginEffect.StartGoogleLogin -> handleGoogleLogin()
            }
        }
    }

    LoginScreen(
        onClickKakaoLogin = { nViewModel.handleEvent(LoginEvent.ClickKakaoLogin) },
        onClickGoogleLogin = { nViewModel.handleEvent(LoginEvent.ClickGoogleLogin) },
        onHome = { nViewModel.handleEvent(LoginEvent.ClickHome) })
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
                iconId = com.hmoa.core_designsystem.R.drawable.ic_google,
                iconSize = 16,
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
                iconSize = 16,
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
