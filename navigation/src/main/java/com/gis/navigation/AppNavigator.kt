package com.gis.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

class AppNavigator {

  private lateinit var navController: NavController

  fun setNavController(navController: NavController) {
    this.navController = navController
  }

  fun navigateToLogin(actionId: Int, navOptions: NavOptions? = null) {
    navController.navigate(actionId, null, navOptions)
  }

  fun navigateToCats(actionId: Int, args: Bundle? = null, navOptions: NavOptions? = null) {
    navController.navigate(actionId, args, navOptions)
  }
}