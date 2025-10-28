package com.opal.app.presentation.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.opal.app.presentation.reward.ReferralRoute
import com.opal.app.presentation.reward.ReferralScreen

@Composable
fun HomeNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        composable<HomeRoute> {
            HomeScreen(navController)
        }
        composable<ReferralRoute> {
            ReferralScreen()
        }
    }
}
