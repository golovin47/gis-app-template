package com.gis.navigation

import androidx.navigation.NavController

class AppNavigator {

  private lateinit var navController: NavController

  fun setNavController(navController: NavController) {
    this.navController = navController
  }

  fun navigateToStart() {
    navController.setGraph(R.navigation.start_screen_nav_graph)
  }

  fun navigateToLogin() {
  }

  fun navigateToCats() {
    navController.setGraph(R.navigation.cats_nav_graph)
  }
}