package com.example.mediadminapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mediadminapp.ui.screens.orderScreenUi

@Composable
fun navigate(viewModel: myViewModel) {
    val navController = rememberNavController()
    var selected by remember { mutableIntStateOf(0) }
    val bottomItems = listOf<BottomNavItem>(
        BottomNavItem("DashBoard", Icons.Default.Home),
        BottomNavItem("Add Products", Icons.Default.Add),
        BottomNavItem("Orders", Icons.Default.ShoppingCart),
        BottomNavItem("Available Products", Icons.Default.Menu)
    )
    val currentDestination = navController.currentBackStackEntry?.destination?.route
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    bottomItems.forEachIndexed { index, bottomNavItem ->
                        NavigationBarItem(
                            alwaysShowLabel = true,
                            selected = selected == index,
                            onClick = {
                                selected = index
                                when (selected) {
                                    0 -> navController.navigate(Routes.homeScreenRoute)
                                    1 -> navController.navigate(Routes.addProductScreenRoute)
                                    2 -> {navController.navigate(Routes.orderScreenRoute)}
                                    3 -> navController.navigate(Routes.allProductScreenRoute)

                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = bottomNavItem.icon,
                                    contentDescription = bottomNavItem.name
                                )
                            },
                            label = {
                                Text(text = bottomNavItem.name)
                            },
                        )
                    }
                }
            }
        ) {
            innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                NavHost(navController = navController, startDestination = Routes.homeScreenRoute) {

                    composable<Routes.homeScreenRoute> {
                        AllUsersUi(viewModel, navController)
                    }

                    composable<Routes.userDetailScreenRoute> {
                        val args = it.toRoute<Routes.userDetailScreenRoute>()
                        UserDetailScreen(args.userId, viewModel)
                    }

                    composable<Routes.addProductScreenRoute> {
                        addProductScreenUi(viewModel, navController)
                    }

                    composable<Routes.allProductScreenRoute> {
                        AllProductScreenUi(viewModel, navController)
                    }

                    composable<Routes.specificProductScreenRoute> {
                        val args = it.toRoute<Routes.specificProductScreenRoute>()
                        SpecificProductScreenUi(args.productId, viewModel)
                    }

                    composable<Routes.orderScreenRoute> {
                        orderScreenUi(viewModel)
                    }

                }

            }
        }

    }


}


data class BottomNavItem(
    val name: String,
    val icon: ImageVector
)