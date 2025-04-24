package com.example.mediuserapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediuserapp.ui.screens.AddProductScreenUi
import com.example.mediuserapp.ui.screens.LoginUi
import com.example.mediuserapp.ui.screens.OrderScreenUi
import com.example.mediuserapp.ui.screens.SignUpUi
import com.example.mediuserapp.ui.screens.SpecificProductScreenUi
import com.example.mediuserapp.ui.screens.UserAvailableProductUi
import com.example.mediuserapp.ui.screens.homeScreenUi
import com.example.mediuserapp.ui.screens.userProfileUi
import com.example.mediuserapp.ui.screens.waitingScreen
import com.example.mediuserapp.viewModel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun navigate(viewModel: MyViewModel) {
    val navController = rememberNavController()
    var selected by remember { mutableIntStateOf(0) }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val bottomItems = listOf<BottomNavItem>(
        BottomNavItem("DashBoard", Icons.Default.Home),
        BottomNavItem("Add Orders", Icons.Default.Add),
        BottomNavItem("Orders", Icons.Default.ShoppingCart),
        BottomNavItem("Account", Icons.Default.Person)
    )
    val hideBottomBarRoutes = listOf(
        Routes.SignUp::class.qualifiedName,
        Routes.Login::class.qualifiedName,
        Routes.WaitingScreen::class.qualifiedName
    )
    val userId = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val startScreen = remember(userId.value) {
        if (userId.value.isEmpty()) {
            Routes.SignUp
        } else {
            Routes.WaitingScreen
        }
    }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(1) {
        coroutineScope.launch(Dispatchers.IO) {
            isLoading.value = true
            userId.value = viewModel.getUserIdDirectly().toString()
            isLoading.value = false
        }
    }
    if(isLoading.value){
        CircularProgressIndicator()
    }
    else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (hideBottomBarRoutes.none { currentRoute?.startsWith(it.orEmpty()) == true }) {
                    NavigationBar {

                        bottomItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selected == index,
                                onClick = {
                                    selected = index
                                    when (selected) {
                                        0 -> navController.navigate(Routes.userAvailableProductRoute)
                                        1 -> navController.navigate(Routes.addProductOrderRoute)
                                        2 -> navController.navigate(Routes.ordersRoute)
                                        3 -> navController.navigate(Routes.userProfileRoute)
                                    }
                                },
                                alwaysShowLabel = true,
                                label = {
                                    Text(text = item.name)
                                },
                                icon = { Icon(imageVector = item.icon, contentDescription = null) }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(navController = navController, startDestination = startScreen) {

                    composable<Routes.SignUp> {
                        SignUpUi(viewModel, navController)
                    }

                    composable<Routes.Login> {
                        LoginUi(viewModel, navController)
                    }

                    composable<Routes.WaitingScreen> {
                        val data = it.toRoute<Routes.WaitingScreen>()
                        waitingScreen(
                            viewModel = viewModel,
                            userId = data.userId,
                            navController = navController
                        )
                    }
                    composable<Routes.HomeScreen> {
                        val data = it.toRoute<Routes.HomeScreen>()
                        homeScreenUi(
                            userId = data.userId,
                        )
                    }


                    composable<Routes.specificProductScreenRoute> {
                        val args = it.toRoute<Routes.specificProductScreenRoute>()
                        SpecificProductScreenUi(args.productId, viewModel)
                    }

                    composable<Routes.addProductOrderRoute> {
                        AddProductScreenUi(viewModel, navController)
                    }
                    composable<Routes.ordersRoute> {
                        OrderScreenUi(viewModel, navController)
                    }

                    composable<Routes.userAvailableProductRoute> {
                        UserAvailableProductUi(viewModel, navController)
                    }

                    composable<Routes.userProfileRoute> {
                        userProfileUi(viewModel,navController)
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