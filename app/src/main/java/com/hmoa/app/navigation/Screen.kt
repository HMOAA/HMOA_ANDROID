package com.hmoa.app.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen(route = "Login")
    object SignupScreen : Screen(route = "Signup")
}