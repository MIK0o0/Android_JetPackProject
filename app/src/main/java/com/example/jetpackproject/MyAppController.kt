package com.example.jetpackproject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.jetpackproject.ui.screens.PhotoSlider
import com.example.jetpackproject.ui.Screen
import com.example.jetpackproject.ui.screens.AddScreen
import com.example.jetpackproject.ui.screens.DetailsScreen
import com.example.jetpackproject.ui.screens.HomeScreen
import com.example.jetpackproject.ui.screens.ListScreen
import com.example.jetpackproject.ui.screens.PhotoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
//
//
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { MyDrawerContent(navController = navController, drawerState = drawerState, scope) },
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { MyAppBar(drawerState = drawerState, scope) },
                bottomBar = { MyBottomBar(navController) },
                content = { innerPadding ->
                    MyDrawerContent(navController = navController, drawerState = drawerState, scope = scope)

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen()
                        }
                        composable(Screen.Search.route) {
                            PhotoScreen(navController)
                        }
                        composable(Screen.Profile.route) {
                            ListScreen(navController)
                        }
                        composable("add") {
                            AddScreen(navController)
                        }
                        composable("slider/{clickedIndex}") { backStackEntry ->
                            val clickedIndex = backStackEntry.arguments?.getString("clickedIndex")?.toIntOrNull() ?: 0
                            PhotoSlider(clickedIndex)
                        }
                        composable("details/{dataItemId}"){backStackEntry ->
                            val dataItemId = backStackEntry.arguments?.getString("dataItemId")?.toIntOrNull() ?: 0
                            DetailsScreen(navController, dataItemId)
                        }
                    }

                }
            )
        }
    )

//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        topBar = { MyAppBar(drawerState = drawerState, scope) },
//        bottomBar = { MyBottomBar(navController) },
//        content = { innerPadding ->
//            MyDrawerContent(navController = navController, drawerState = drawerState, scope = scope)
//
//            NavHost(
//                navController = navController,
//                startDestination = Screen.Home.route,
//                modifier = Modifier.padding(innerPadding)
//            ) {
//                composable(Screen.Home.route) {
//                    HomeScreen()
//                }
//                composable(Screen.Search.route) {
//                    PhotosScreen()
//                }
//                composable(Screen.Profile.route) {
//                    ListScreen(navController)
//                }
//                composable("add") {
//                    AddScreen(navController)
//                }
//            }
//
//        }
//    )
}
