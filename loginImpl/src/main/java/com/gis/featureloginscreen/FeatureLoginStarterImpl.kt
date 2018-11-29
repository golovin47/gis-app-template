package com.gis.featureloginscreen

import androidx.navigation.NavController
import com.gis.featureloginapi.FeatureLoginStarter

class FeatureLoginStarterImpl(private val navController: NavController) : FeatureLoginStarter {

  override fun start() {
    navController.setGraph(R.navigation.login_nav_graph)
  }
}