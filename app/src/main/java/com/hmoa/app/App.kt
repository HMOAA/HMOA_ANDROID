package com.hmoa.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}