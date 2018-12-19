package com.gis.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class AppNavigator {

  private lateinit var navController: NavController

  fun setNavController(navController: NavController) {
    this.navController = navController
  }

  fun goToLoginFromStartScreen() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.anim_fade_in)
      .setExitAnim(R.anim.anim_fade_out)
      .setPopUpTo(R.id.startScreenFragment, true)
      .build()
    navController.navigate(R.id.from_start_to_login, null, navOptions)
  }

  fun goToMainFromStartScreen() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.anim_fade_in)
      .setExitAnim(R.anim.anim_fade_out)
      .setPopUpTo(R.id.startScreenFragment, true)
      .build()
    navController.navigate(R.id.from_start_to_cats, null, navOptions)
  }

  fun goToMainFromLogin() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.anim_fade_in)
      .setExitAnim(R.anim.anim_fade_out)
      .setPopUpTo(R.id.loginFragment, true)
      .build()
    navController.navigate(R.id.from_login_to_cats, null, navOptions)
  }
}