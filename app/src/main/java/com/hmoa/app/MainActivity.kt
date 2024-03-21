package com.hmoa.app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.hmoa.app.navigation.SetUpNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    private lateinit var initialRoute: String
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("A123","MainActivity onCreate()")
        super.onCreate(savedInstanceState)
        Log.d("A123","onCreate done")
        runBlocking { initialRoute = viewModel.routeInitialScreen() }
        Log.d("A123", "runBlocking is done")

        setContent {
            val navHostController = rememberNavController()
            SetUpNavGraph(navHostController, initialRoute)
        }
    }
}