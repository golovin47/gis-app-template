package com.gis.navigationimpl

import com.gis.featureloginapi.FeatureLoginStarter
import com.gis.featurestartscreenapi.FeatureStartScreenStarter
import com.gis.featureusers.FeatureCatsStarter
import com.gis.naviagationapi.Navigator

class NavigatorImpl(
  private val startScreenStarter: FeatureStartScreenStarter,
  private val loginStarter: FeatureLoginStarter,
  private val catsStarter: FeatureCatsStarter
) : Navigator {

  override fun navigateToStartScreen() {
    startScreenStarter.start()
  }

  override fun navigateToLoginScreen() {
    loginStarter.start()
  }

  override fun navigateToUsersScreen() {
    catsStarter.start()
  }
}