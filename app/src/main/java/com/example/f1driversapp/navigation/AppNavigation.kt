package com.example.f1driversapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.f1driversapp.models.NavItem
import com.example.f1driversapp.screens.AddScreen
import com.example.f1driversapp.screens.HomeScreen
import com.example.f1driversapp.viewModel.DriverViewModel

object AppDestinations {
    const val HOME_ROUTE = "home"
    const val ADD_DRIVER_ROUTE = "add_driver"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.HOME_ROUTE
) {

    val driverViewModel: DriverViewModel = viewModel()

    var selectedIndex by remember { mutableIntStateOf(0) }
    val navItems = NavItemList.NavItemList

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navItemList = navItems,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    selectedIndex = index
                    when (index) {
                        0 -> navController.navigate(AppDestinations.HOME_ROUTE) {

                            popUpTo(AppDestinations.HOME_ROUTE) { inclusive = true }
                        }
                        1 -> navController.navigate(AppDestinations.ADD_DRIVER_ROUTE) {

                            popUpTo(AppDestinations.HOME_ROUTE)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(AppDestinations.HOME_ROUTE) {

                    selectedIndex = 0

                    driverViewModel.fetchDrivers()
                    HomeScreen()
                }

                composable(AppDestinations.ADD_DRIVER_ROUTE) {

                    selectedIndex = 1
                    AddScreen(
                        navigateBack = {

                            navController.navigate(AppDestinations.HOME_ROUTE) {
                                popUpTo(AppDestinations.HOME_ROUTE) { inclusive = true }
                            }

                            selectedIndex = 0
                        }
                    )
                }
            }
        }
    }
}