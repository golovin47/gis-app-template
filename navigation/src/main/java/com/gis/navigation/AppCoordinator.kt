package com.gis.navigation

import androidx.navigation.NavOptions

class AppCoordinator(private val navigator: AppNavigator) {

  fun goToLoginFromStartScreen() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.anim_fade_in)
      .setExitAnim(R.anim.anim_fade_out)
      .setPopUpTo(R.id.startScreenFragment, true)
      .build()
    navigator.navigateToLogin(actionId = R.id.from_start_to_login, navOptions = navOptions)
  }

  fun goToMainFromStartScreen() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.anim_fade_in)
      .setExitAnim(R.anim.anim_fade_out)
      .setPopUpTo(R.id.startScreenFragment, true)
      .build()
    navigator.navigateToCats(actionId = R.id.from_start_to_cats, navOptions = navOptions)
  }
}