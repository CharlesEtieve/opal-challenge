package com.opal.app.presentation.home

import androidx.navigation.NavController
import com.opal.app.presentation.BaseViewModel
import com.opal.app.presentation.reward.ReferralRoute

class HomeViewModel : BaseViewModel() {
    fun onSetup(navController: NavController) {
        navController.navigate(ReferralRoute)
    }
}
