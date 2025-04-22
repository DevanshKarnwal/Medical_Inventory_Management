package com.example.mediuserapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediuserapp.ui.screens.AllProductScreenUi
import com.example.mediuserapp.ui.screens.LoginUi
import com.example.mediuserapp.ui.screens.SignUpUi
import com.example.mediuserapp.ui.screens.SpecificProductScreenUi
import com.example.mediuserapp.ui.screens.homeScreenUi
import com.example.mediuserapp.ui.screens.waitingScreen
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun navigate(viewModel: MyViewModel){
    val navController = rememberNavController()
    var selected by remember { mutableIntStateOf(0) }
    val bottomItems = listOf<BottomNavItem>(
        BottomNavItem("DashBoard", Icons.Default.Home),
        BottomNavItem("Add Products", Icons.Default.Add),
        BottomNavItem("Orders", Icons.Default.ShoppingCart),
        BottomNavItem("Available Products", Icons.Default.Menu)
    )
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
        composable<Routes.allProductScreenRoute>{
            AllProductScreenUi(viewModel,navController)
        }

        composable<Routes.specificProductScreenRoute> {
            val args = it.toRoute<Routes.specificProductScreenRoute>()
            SpecificProductScreenUi(args.productId,viewModel)
        }
    }
}


data class BottomNavItem(
    val name: String,
    val icon: ImageVector
)