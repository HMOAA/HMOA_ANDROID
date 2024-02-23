package com.hmoa.app.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen(route = "Login")
}