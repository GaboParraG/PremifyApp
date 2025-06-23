package com.premifysas.premifyapp.navigation

sealed class AppScreens (val route: String) {

    object  SplasScreen: AppScreens("SplashScreen")
    object  PpalScreen: AppScreens("PpalScreen")
    object  Login: AppScreens("Login")
    object  ForgotPassword: AppScreens("ResetPassword")
    object  SignUp: AppScreens("SignUp")
    object  NewRaffle: AppScreens("NewRaffle")
    object  RaffleDetail: AppScreens("RaffleDetail")
    object  ActiveRaffles: AppScreens("ActiveRaffles")
    object  Raffles: AppScreens("Raffles")
    object  ConfirmRaffle: AppScreens("ConfirmRaffle")
    object  Profile: AppScreens("Profile")

}