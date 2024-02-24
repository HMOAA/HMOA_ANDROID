package com.hmoa.feature_authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.KakaoSdk

class AuthCodeHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, "{NATIVE_APP_KEY}")
    }
}