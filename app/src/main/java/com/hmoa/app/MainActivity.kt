package com.hmoa.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.hmoa.app.navigation.SetUpNavGraph
import com.hmoa.feature_authentication.navigation.LOGIN_ROUTE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            SetUpNavGraph(navHostController)
        }
    }
}