package com.example.mediuserapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediuserapp.ui.screens.LoginUi
import com.example.mediuserapp.ui.screens.SignUpUi
import com.example.mediuserapp.ui.screens.homeScreenUi
import com.example.mediuserapp.ui.screens.waitingScreen
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun navigate(viewModel: MyViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SignUp){

        composable<Routes.SignUp>{
            SignUpUi(viewModel,navController)
        }

        composable<Routes.Login>{
            LoginUi(viewModel,navController)
        }
        
        composable<Routes.WaitingScreen>{
            val data = it.toRoute<Routes.WaitingScreen>()
            waitingScreen(
                viewModel = viewModel,
                userId = data.userId,
                navController = navController
            )
        }
        composable<Routes.HomeScreen>{
            val data = it.toRoute<Routes.HomeScreen>()
            homeScreenUi(
                userId = data.userId,
            )
        }
        
    }
}