package com.premifysas.premifyapp.navigation

sealed class AppScreens (val route: String) {

    object  SplasScreen: AppScreens("SplashScreen")
    object Login: AppScreens("Login")

}