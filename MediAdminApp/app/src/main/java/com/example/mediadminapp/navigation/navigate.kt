package com.example.mediadminapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediadminapp.ui.screens.AllProductScreenUi
import com.example.mediadminapp.ui.screens.AllUsersUi
import com.example.mediadminapp.ui.screens.SpecificProductScreenUi
import com.example.mediadminapp.ui.screens.UserDetailScreen
import com.example.mediadminapp.ui.screens.addProductScreenUi
import com.example.mediadminapp.viewModel.myViewModel

@Composable
fun navigate(viewModel: myViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.addProductScreenRoute){

        composable<Routes.homeScreenRoute>{
            AllUsersUi(viewModel,navController)
        }

        composable<Routes.userDetailScreenRoute> {
            val args = it.toRoute<Routes.userDetailScreenRoute>()
            UserDetailScreen(args.userId,viewModel)
        }

        composable<Routes.addProductScreenRoute>{
            addProductScreenUi(viewModel,navController)
        }

        composable<Routes.allProductScreenRoute>{
            AllProductScreenUi(viewModel,navController)
        }

        composable<Routes.specificProductScreenRoute> {
            val args = it.toRoute<Routes.specificProductScreenRoute>()
            SpecificProductScreenUi(args.productId,viewModel)
        }

    }
}