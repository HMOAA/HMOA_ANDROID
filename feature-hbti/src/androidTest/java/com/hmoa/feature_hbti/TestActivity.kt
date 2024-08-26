package com.hmoa.feature_hbti

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.hmoa.feature_hbti.screen.HbtiSurveyResultScreen

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            HbtiSurveyResultScreen(
                onErrorHandleLoginAgain = {},
                onBackClick = {},
                onHbtiProcessClick = {}

            )
        }
    }
}